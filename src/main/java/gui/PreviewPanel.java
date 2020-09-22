package gui;

import data.*;

import javafx.scene.canvas.*;
import javafx.scene.text.*;

import static data.structure.FileContentStructure.*;
import static javafx.scene.paint.Color.*;

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

				root.previewRefreshTrigger.scrollPanelStateProperty()
					.addListener((observable1) -> {

						clean();
						drawBorders();
						drawHorizontals();
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

		context.strokeRect(margin, 1, getWidth() - margin - 1., getHeight() - margin);
	}

	private void drawHorizontals() {


		double
			bitsPerSample = FileCache.getFile().getHeader().getField(BITS_PER_SAMPLE),
			verticalScale = 1.,
			horizontalScale = 1.,

			height = getHeight() - margin,
			width = getWidth(),

			scaledHeight = height * Math.pow(2., root.getVerticalScrollPanel().getScaleValue() - 1),
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4,

			verticalOffset = root.getVerticalScrollPanel().getScrollValue();

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)) - 1;

		context.setStroke(DODGERBLUE);
		context.setTextAlign(TextAlignment.RIGHT);
		context.setFont(font);


		// ? central line -------------------------------------

		double y0 =  (height / 2.) + verticalOffset;

		context.strokeLine(margin * 0.75, y0, width, (int) y0);


		// ? --------------------------------------------------


		for (int i = 1; i <= numberOfLines; i++) {

			context.setStroke(DODGERBLUE);

			double
				y1 = (height / 2) - scaledHeight / (1 << i) + verticalOffset,
				y2 = (height / 2) + scaledHeight / (1 << i) + verticalOffset;

			int
				txt = (-(i - 1) * 6);

			if (y1 > 0) {

				if (i > 1 && i < numberOfLines) {

					context.setStroke(DODGERBLUE);
					context.strokeText(txt + " ", margin * 0.85, y1 + 4);

					context.setStroke(DODGERBLUE);
					context.strokeLine(margin * 0.85, y1, width, y1);
				}

				else {
					context.strokeLine(margin, y1, width, y1);
				}
			}

			if (y2 < height && y1 != y2) {

				if (i > 1 && i < numberOfLines) {

					context.setStroke(DODGERBLUE);
					context.strokeText(txt + " ", margin * 0.85, y2 + 4);

					context.setStroke(DODGERBLUE);
					context.strokeLine(margin * 0.85, y2, width, y2);
				}

				else {
					context.setStroke(DODGERBLUE);
					context.strokeLine(margin, y2, width, y2);
				}
			}
		}
	}

/*	private void drawVerticals() {

		Strip
			strip = waveFile.getSignal().getStrip(0);

		WaveHeader
			currentHeader = waveFile.getHeader();

		double
			height = getHeight() - margin,
			width = getWidth() - margin;

		int
			waveLength = strip.size(),
			bitsPerSample = currentHeader.getField(BITS_PER_SAMPLE) - 1,
			samplingRate = currentHeader.getField(SAMPLE_PER_SEC),
//			horizontalGridResolution = (int) ((samplingRate - 1) * root.getHorizontalScrollPanel().getScaleValue()) + 1,
			horizontalGridResolution = samplingRate / 1000,
			movement = computeMovement(),
			verticalGridMovement = movement % horizontalGridResolution;

		context.setStroke(LIGHTGREY);


		for (int i = 0; i < width + movement; i += horizontalGridResolution) {

			int
				x = (int) (margin + width / 2 - computeMovement() + i);

			if (root.getHorizontalScrollPanel().getScrollValue() < 1.) {
				context.strokeLine(x, 0, x, height);
			}
		}
	}*/

	void iterateDrawing(int channel) {

		context.setLineWidth(1);


















			/*

					context.setLineWidth(1);

		Color[]
			light = new Color[]{RED, GREEN},
			dark = new Color[]{DARKRED, DARKGREEN};

		Strip
			strip = FileCache.getFile().getSignal().getStrip(channel);

		double
			previewWidth = getWidth() - margin,
			previewHeight = getHeight() - margin,

			scale = root.getHorizontalScrollPanel().getScaleValue() / previewWidth,
			scroll = root.getHorizontalScrollPanel().getScrollValue() /  (Math.max(scale, 1.)),

//			bitsPerSample = FileCache.getCurrentFileBitsPerSample();
			bitsPerSample = root.verticalScrollPanel().getScaleValue();


		for (int i = (int) scroll; i < scroll + previewWidth; i++) {

						int
				firstIndex = (int) (i * (Math.max(scale, 1.))),
				secondIndex = (int) ((i + 1) * (Math.max(scale, 1.)));

			double
				firstSample = firstIndex < strip.size() ? strip.get(firstIndex) : 0,
				secondSample = secondIndex < strip.size() ? strip.get(secondIndex) : 0,


				firstX = ((i - scroll) / Math.min(1., scale)) + margin,
				secondX = ((i + 1 - scroll) / Math.min(1., scale)) + margin,


				firstY = firstSample / (double) (Math.pow(2., bitsPerSample),	// !
				secondY = secondSample / (double) (1 << (int) bitsPerSample),

				verticalCenter = previewHeight / 2,

				firstYOffset = verticalCenter * (1. - firstY),
				secondYOffset = verticalCenter * (1. - secondY);

			if (firstIndex >= 0 && secondIndex < strip.size()) {

				context.setStroke(light[channel]);
				context.strokeLine(firstX, firstYOffset, secondX, secondYOffset);
			}

//			else {
//
//				context.setStroke(dark[channel]);
//				context.strokeLine(firstX, firstYOffset, secondX, secondYOffset);
//			}
		}
		*/    // drawing waveform
	}
}