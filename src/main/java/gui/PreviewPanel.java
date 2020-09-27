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

		setHorizontalScale();

		context = getGraphicsContext2D();
		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());

		FileCache.fileCacheProperty().sizeProperty().addListener((observable) -> {

			if (FileCache.getFileCacheIsNotEmpty()) {

				waveFile = FileCache.getFile();
				drawEverything();
				root.previewRefreshTrigger.scrollPanelStateProperty().addListener((observable1) -> {

					setHorizontalScale();
					drawEverything();
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

	private void drawEverything() {

		clean();
		drawAmplitudeGrid();
		drawWaveForm();
		drawFrame();
		drawTimeGrid();
	}

	double
		horizontalScale;

	private void setHorizontalScale(){
		horizontalScale =  Math.pow(Math.E, root.getHorizontalScrollPanel().getScaleValue()) / Math.log(2.);
	}

	private void drawAmplitudeGrid() {

		double
			height = getHeight() - margin,
			width = getWidth(),

			verticalScale = root.getVerticalScrollPanel().getScaleValue(),
			verticalScroll = root.getVerticalScrollPanel().getScrollValue(),

			gridYScale = height * Math.pow(2., verticalScale - 2.),

			accountForMinResolution = gridYScale / 8,

			zoom = height * Math.pow(2., verticalScale),                // ! copy from drawWaveform
			yOffset = (0.5 * zoom - 0.25 * height) * verticalScroll,    // !

			y0 = (height / 2.) - yOffset;

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)) - 1;

		context.setStroke(DODGERBLUE);
		context.setLineWidth(1.);

		context.strokeLine(margin * 0.75, y0, width, y0);    // central line

		context.setLineWidth(0.6);
		context.setTextAlign(RIGHT);
		context.setFont(font);

		for (int i = 1; i <= numberOfLines; i++) {

			double
				vScale = gridYScale / (1 << i),
				y1 = y0 - vScale,
				y2 = y0 + vScale;

			int
				txt = (-i * 6);

			if (y1 > 0) {

				if (i < numberOfLines) context.strokeText(txt + " ", margin * 0.85, y1 + 4);

				context.strokeLine(margin, y1, width, y1);
			}

			if (y2 < height && y1 != y2) {

				if (i < numberOfLines) context.strokeText(txt + " ", margin * 0.85, y2 + 4);

				context.strokeLine(margin, y2, width, y2);
			}
		}
	}

	private void drawWaveForm() {

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.75);
		context.setStroke(RED);

		Strip
			strip = FileCache.getFile().getSignal().getStrip(0);

		double
			bitsPerSample = FileCache.getCurrentFileBitsPerSample(),
			height = getHeight() - margin,
			verticalScroll = root.getVerticalScrollPanel().getScrollValue(),
			verticalScale = root.getVerticalScrollPanel().getScaleValue(),

			zoom = height * Math.pow(2., verticalScale),                // ! copy from drawHorizontals
			vOffset = (0.5 * zoom - 0.25 * height) * verticalScroll,    // !

			vScale = (Math.pow(2., bitsPerSample) / height) / Math.pow(2., verticalScale - 1.),
			y0 = (height / 2.) - vOffset,


			fileLength = FileCache.getCurrentFileSignalLength(),
			width = getWidth() - margin,
			horizontalScroll = root.getHorizontalScrollPanel().getScrollValue(),

			viewStep = horizontalScale / fileLength,
			x0 = width / 2.;

		{
			double
				index1Start = horizontalScroll,
				x1Start = x0,
				y1Start,

				index1End = horizontalScroll + 1,
				x1End = x0 + viewStep,
				y1End = y0;

			do {
				index1Start--;
				index1End--;

				x1Start -= viewStep;
				x1End -= viewStep;

				if (indexIsInRange(index1End)) y1End = y0 - strip.get((int) index1End) / vScale;
				if (indexIsInRange(index1Start)) {

					y1Start = y0 - strip.get((int) index1Start) / vScale;
					context.strokeLine(x1Start, y1Start, x1End, y1End);
				}

			} while (index1End >= 0 && x1End > margin);
		}

		{
			double
				index2Start = horizontalScroll - 1,
				x2Start = x0 - viewStep,
				y2Start = y0,

				index2End = horizontalScroll,
				x2End = x0,
				y2End;

			do {
				index2Start++;
				index2End++;

				x2Start += viewStep;
				x2End += viewStep;

				if (indexIsInRange(index2Start)) y2Start = y0 - strip.get((int) index2Start) / vScale;
				if (indexIsInRange(index2End)) {

					y2End = y0 - strip.get((int) index2End) / vScale;
					context.strokeLine(x2Start, y2Start, x2End, y2End);
				}

			} while (index2Start < fileLength && x2Start < width + margin);
		}
	}

	private void drawTimeGrid() {

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.6);

		int
			scaledIntegerWidth = 1 << (int) (Math.log(horizontalScale) / Math.log(2.)),
			txt = 0/*(int) (horizontalScroll)*/;

		double
			samplesPerSecond = FileCache.getCurrentFileSamplesPerSecond(),
			bitsPerSample = FileCache.getCurrentFileBitsPerSample(),
			fileLength = FileCache.getCurrentFileSignalLength(),

			horizontalScroll = root.getHorizontalScrollPanel().getScrollValue(),

			width = getWidth() - margin,
			height = getHeight() - margin,

			gridScale = (samplesPerSecond / 1000.), // ? 1 ms

			viewStep = horizontalScale / fileLength,

			x0 = width / 2.,
			x1 = x0,
			x2 = x0;







		for (int i = 0; x1 > margin || x2 < width + margin; i++) {

			double
				gridIncrement = horizontalScale * ((i * 32./*gridScale*/) / scaledIntegerWidth);

			x1 = x0 - (gridIncrement) /*- horizontalScroll*/;
			x2 = x0 + (gridIncrement) /*- horizontalScroll*/;

			if (x1 > margin) {

				context.setStroke(GREY);
//				context.strokeLine(x1, 0, x1, height);

//				context.setStroke(DODGERBLUE);
//				context.strokeText((int) (txt - gridIncrement) + " ", x1, height + margin * 0.85);

			}

			if (x2 < width + margin /*&& x1 != x2*/) {

				context.setStroke(GREY);
				context.strokeLine(x2, 0, x2, height);

//				context.setStroke(DODGERBLUE);
//				context.strokeText((int) (txt + gridIncrement) + " ", x2, height + margin * 0.85);
				// txt

			}
		}
	}

	private boolean indexIsInRange(double index) {

		return index >= 0 && index < FileCache.getCurrentFileSignalLength();
	}


	private void drawFrame() {

		context.clearRect(margin, getHeight() + 2 - margin, getWidth(), getHeight());            // clear footer

		double
			y0 = (getHeight() - margin) / 2.,
			x0 = (getWidth() - margin) / 2.;

		context.setLineWidth(1.);
		context.setStroke(DODGERBLUE);

		context.strokeLine(margin * 0.75, y0, margin, y0);                                        // vertical indicator
		context.strokeLine(x0, 2 + y0 * 2., x0, getHeight() + 2 - (margin * 0.75));                // horizontal indicator
		context.strokeRect(margin + 1, 1, getWidth() - 2 - margin, getHeight() + 1 - margin);    // frame
	}
}