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
//				drawBorders();
//				drawHorizontals();
				drawWaveForm();
//				drawVerticals();


				root.previewRefreshTrigger.scrollPanelStateProperty()
					.addListener((observable1) -> {

						clean();
//						drawBorders();
//						drawHorizontals();
						drawWaveForm();
//						drawVerticals();
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
			height = getHeight() - margin,
			width = getWidth(),

			verticalScale = root.getVerticalScrollPanel().getScaleValue(),
			verticalScroll = root.getVerticalScrollPanel().getScrollValue(),

			gridYScale = height * verticalScale / 2.,
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
				txt = (-(i - 1) * 6);

			if (y1 > 0) {

				if (i > 1 && i < numberOfLines) context.strokeText(txt + " ", margin * 0.85, y1 + 4);

				context.strokeLine(margin, y1, width, y1);
			}

			if (y2 < height && y1 != y2) {

				if (i > 1 && i < numberOfLines) context.strokeText(txt + " ", margin * 0.85, y2 + 4);

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

		context.clearRect(margin, height + 2, width + margin, margin);

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.6);


		for (int i = 0; x1 > margin || x2 < width + margin; i++) {

			double
				gridIncrement = i * gridScale * horizontalScale / scaledIntegerWidth;

			x1 = (width / 2) - (gridIncrement) /*- horizontalOffset*/;
			x2 = (width / 2) + (gridIncrement) /*- horizontalOffset*/;

			if (x1 > margin && txt - gridIncrement >= 0) {

				context.setStroke(GREY);
				context.strokeLine(x1, 0, x1, height);

/*				context.setStroke(DODGERBLUE);
				context.strokeText((int) (txt - gridIncrement) + " ", x1, height + margin * 0.85);*/    // txt

			}

			if (x2 < width + margin && x2 != width / 2 && x1 != x2 && txt + gridIncrement < fileLength) {

				context.setStroke(GREY);
				context.strokeLine(x2, 0, x2, height);

/*				context.setStroke(DODGERBLUE);
				context.strokeText((int) (txt + gridIncrement) + " ", x2, height + margin * 0.85);*/    // txt

			}
		}
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

			scaledHeight = height * Math.pow(2.,verticalScale),
			vOffset = (0.5 * scaledHeight - 0.25 * height) * verticalScroll,


			previewArea = (fileLength + 1. - horizontalScale),
			stepInArea = Math.max(1., previewArea / width),
			stepInPreview = Math.max(1., width / previewArea),

			vScale = ((double) (1 << (int) bitsPerSample)) / height / verticalScale,
			middle = width / 2;

		System.out.println("\t Scroll = " + horizontalScroll + ", scale = ");

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(1.);

		for (int i = 0; i < (int) middle; i++) {

//			if (i < middle) {

				double
					index1Start = /*middle*/ (previewArea / 2.) + (stepInArea * i) + horizontalScroll,
					index1End = index1Start + stepInArea,
					index2Start = /*middle*/ (previewArea / 2.) - (stepInArea * (i + 1)) + horizontalScroll,
					index2End = index2Start + stepInArea,


					x1Start = stepInPreview * (middle + i) + margin,
					x1End = stepInPreview * (middle + (i + 1)) + margin,
					x2End = stepInPreview * (middle -  i) + margin,
					x2Start = stepInPreview * (middle - (i + 1)) + margin,


					y1Start = index1Start < fileLength && index1Start >=0
								? (height / 2) - ((strip.get((int) index1Start) / vScale) + vOffset)
								: height / 2.,
					y1End = index1End < fileLength && index1End >= 0
								? (height / 2) - ((strip.get((int) index1End) / vScale) + vOffset)
								: height / 2.,
					y2Start = index2Start < fileLength && index2Start >=0
						  		? (height / 2) - ((strip.get((int) index2Start) / vScale) + vOffset)
						  		: height / 2.,
					y2End = index2Start < fileLength && index2End >= 0
								? (height / 2) - ((strip.get((int) index2End) / vScale) + vOffset)
								: height / 2.
								;

				context.strokeLine(x1Start, y1Start, x1End, y1End);
				context.strokeLine(x2Start, y2Start, x2End, y2End);


/*
			//					y2Start = index2Start < previewArea && index2Start >= 0
//								  ? ((height / 2) - (strip.get((int) index2Start) / vScale) + vOffset) - (height / 2)
//								  : 0.,
//
//
//
//					y2End = index2End < previewArea && index2End >= 0
//								? ((height / 2) - (strip.get((int) index2End) / vScale) + vOffset) - (height / 2)
//								: 0.;

				double
					index = (stepInArea * (middle + i) + horizontalScroll),
					index1 = index + stepInArea;

				double
					y1 = 0.,
					y2 = 0.;

				if (index1 < fileLength) {

					y1 = ((strip.get((int) index) / vScale) + vOffset);
					y2 = ((strip.get((int) index1) / vScale) + vOffset);
				}

				context.strokeLine(stepInPreview * (i + middle) + margin, (height / 2) - y1, stepInPreview * (i + middle + 1) + margin, (height / 2) - y2);*/
			}
		}
	}
//}

/*	void iterateDrawing(int channel) {

		context.setLineWidth(1);

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
   // drawing waveform
	}*/