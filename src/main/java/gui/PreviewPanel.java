package gui;

import data.*;

import data.structure.Strip;
import javafx.scene.canvas.*;
import javafx.scene.text.*;

import static javafx.scene.paint.Color.*;
import static javafx.scene.text.TextAlignment.*;

public class PreviewPanel extends Canvas {

	private final Root
		root;

	private static final double
		margin = 20.;

	private final GraphicsContext
		context;

	private final Font
		font = new Font("Arial", 10);

	private WaveFile
		waveFile;

	public PreviewPanel(Root root) {

		this.root = root;

		context = getGraphicsContext2D();

		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());


		FileCache.fileCacheProperty().sizeProperty().addListener((observable) -> {

			if (FileCache.getFileCacheIsNotEmpty()) {

				waveFile = FileCache.getFile();

				clean();
				drawBorders();
				drawHorizontals();
				drawWaveForm();
				drawVerticals();


				root.previewRefreshTrigger.scrollPanelStateProperty()
					.addListener((observable1) -> {

						clean();
						drawBorders();
						drawHorizontals();
						drawWaveForm();
						drawVerticals();
					});
			}

			else {

				root.previewRefreshTrigger.scrollPanelStateProperty().removeListener(((observable1) -> {}));
				clean();
				waveFile = null;
			}
		});

	}


	void clean() {

		context.clearRect(0, 0, getWidth(), getHeight());
	}

	private void drawBorders() {

		context.setLineWidth(1);
		context.setStroke(DODGERBLUE);
	}

	private void drawHorizontals() {

		double
			height = getHeight() - margin,
			width = getWidth(),

			verticalScale = root.getVerticalScrollPanel().getScaleValue(),
			verticalScroll = root.getVerticalScrollPanel().getScrollValue(),

			gridYScale = height * Math.pow(2., verticalScale - 2.)/* / 2.*/,
			zoom = height * Math.pow(2., verticalScale),

			accountForMinResolution = gridYScale / 8,

			yOffset = (0.5 * zoom - 0.25 * height) * verticalScroll,
			y0 = (height / 2.) - yOffset;

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)) - 1;

		context.setStroke(DODGERBLUE);
		context.setLineWidth(1.);

		context.strokeLine(margin * 0.75, height / 2, margin, height / 2); // central indicator
		context.strokeLine(margin * 0.75, y0, width, y0);    // central line

		context.setLineWidth(0.6);
		context.setTextAlign(RIGHT);
		context.setFont(font);


		for (int i = 1; i <= numberOfLines; i++) {

			double
				y1 = (height / 2) - gridYScale / (1 << i) - yOffset,
				y2 = (height / 2) + gridYScale / (1 << i) - yOffset;

			int
				txt = ((/*1 */-i) * 6);

			if (y1 > 0) {

				if (/*i > 1 &&*/ i < numberOfLines) context.strokeText(txt + " ", margin * 0.85, y1 + 4);

				context.strokeLine(margin, y1, width, y1);
			}

			if (y2 < height && y1 != y2) {

				if (/*i > 1 &&*/ i < numberOfLines) context.strokeText(txt + " ", margin * 0.85, y2 + 4);

				context.strokeLine(margin, y2, width, y2);
			}
		}
	}

	private void drawVerticals() {

		Strip
			strip = FileCache.getFile().getSignal().getStrip(0);

		int
			samplesPerSecond = FileCache.getCurrentFileSamplesPerSecond(),
			bitsPerSample = FileCache.getCurrentFileBitsPerSample(),
			fileLength = FileCache.getCurrentFileSignalLength();

		double
			horizontalScale = root.getHorizontalScrollPanel().getScaleValue(),

			width = getWidth() - margin,
			height = getHeight() - margin,

			gridScale = 64,
			hOffset = root.getHorizontalScrollPanel().getScrollValue(),

			x1 = width / 2,
			x2 = width / 2;

		int
			scaledIntegerWidth = 1 << (int) (Math.log(horizontalScale) / Math.log(2.)),
			txt = 0 /*(int) horizontalScroll*/;


		context.strokeRect(margin + 1, 1, getWidth() - 1 - margin, getHeight() + 1 - margin);
		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.6);


		for (int i = 0; x1 > margin || x2 < width + margin; i++) {

			double
				gridIncrement = horizontalScale * ((i * gridScale) / scaledIntegerWidth);

			x1 = (width / 2) - (gridIncrement) /*- hOffset*/;
			x2 = (width / 2) + (gridIncrement) /*- hOffset*/;

			if (x1 > margin) {

				context.setStroke(GREY);
				context.strokeLine(x1, 0, x1, height);

/*				context.setStroke(DODGERBLUE);
				context.strokeText((int) (txt - gridIncrement) + " ", x1, height + margin * 0.85);*/    // txt

			}

			if (x2 < width + margin && x1 != x2) {

				context.setStroke(GREY);
				context.strokeLine(x2, 0, x2, height);

/*				context.setStroke(DODGERBLUE);
				context.strokeText((int) (txt + gridIncrement) + " ", x2, height + margin * 0.85);*/    // txt

			}
		}
	}


	private double getHorizontalRescaleFactor() {

		double
			width = getWidth() - margin,
			height = getHeight() - margin,

			samplesPerSecond = FileCache.getCurrentFileSamplesPerSecond(),

			bitsPerSample = FileCache.getCurrentFileBitsPerSample(),
			fileLength = FileCache.getCurrentFileSignalLength(),

			horizontalScroll = root.getHorizontalScrollPanel().getScrollValue(),
			horizontalScale = root.getHorizontalScrollPanel().getScaleValue();

		return width / horizontalScale;
	}


	private void drawWaveForm() {

		Strip
			strip = FileCache.getFile().getSignal().getStrip(0);

		double
			samplesPerSecond = FileCache.getCurrentFileSamplesPerSecond(),
			bitsPerSample = FileCache.getCurrentFileBitsPerSample(),
			fileLength = FileCache.getCurrentFileSignalLength(),

			width = getWidth() - margin,
			height = getHeight() - margin,

			verticalScroll = root.getVerticalScrollPanel().getScrollValue(),
			verticalScale = root.getVerticalScrollPanel().getScaleValue(),
			horizontalScroll = root.getHorizontalScrollPanel().getScrollValue(),
			horizontalScale = root.getHorizontalScrollPanel().getScaleValue(),

			scaledHeight = height * Math.pow(2., verticalScale),
			vOffset = (0.5 * scaledHeight - 0.25 * height) * verticalScroll,

			vScale = (Math.pow(2., bitsPerSample) / height) / Math.pow(2., verticalScale - 1.),
			middle = width / 2.;

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.5);

		double
			y0 = (height / 2.) - vOffset,
			indexStep = Double.max(1., getHorizontalRescaleFactor()),
			viewStep = 1. / Double.min(1., getHorizontalRescaleFactor());

		double
			index1Start = horizontalScroll,
			x1Start = middle,
			y1Start = y0,

			index1End = horizontalScroll + indexStep,
			x1End = middle + viewStep,
			y1End = y0;

		do {
			index1Start += indexStep;
			index1End += indexStep;

			x1Start += viewStep;
			x1End += viewStep;

			if (index1Start < fileLength && index1Start >= 0) y1Start = y0 - strip.get((int) index1Start) / vScale;

			if (index1End < fileLength && index1End >=0) y1End = y0 - strip.get((int) index1End) / vScale;

			context.strokeLine(x1Start, y1Start, x1End, y1End);

		}

		while (index1Start < fileLength && x1Start < width + margin);



		double
			index2Start = horizontalScroll - indexStep,
			x2Start = middle - viewStep,
			y2Start = y0,

			index2End = horizontalScroll,
			x2End = middle,
			y2End = y0;

		do {
			index2Start -= indexStep;
			index2End -= indexStep;

			x2Start -= viewStep;
			x2End -= viewStep;

			if (index2Start < fileLength && index2Start >= 0) y2Start = y0 - strip.get((int) index2Start) / vScale;

			if (index2End < fileLength && index2End >= 0) y2End = y0 - strip.get((int) index2End) / vScale;

			context.strokeLine(x2Start, y2Start, x2End, y2End);

		}

		while (index2End >=0 && x2End > margin);
	}
}