package gui.previewPanel;

import data.FileCache;
import javafx.scene.canvas.*;
import javafx.scene.paint.Color;
import javafx.scene.text.*;

import data.structure.signal.Previewing;
import data.structure.signal.SamplePyramid;
import gui.Root;

import static javafx.scene.paint.Color.*;
import static javafx.scene.text.TextAlignment.*;

import static data.FileCache.*;

public class PreviewPanel extends Canvas {


	private final Root
		root;

	private static final double
		margin = 20.;

	private final GraphicsContext
		context;

	private final Font
		font = new Font("Arial", 10);

	private boolean
		theFlag = true;

	double
		horizontalScale;



	public PreviewPanel(Root root) {

		this.root = root;
		setHorizontalScale();
		context = getGraphicsContext2D();
		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());

		fileCacheProperty().sizeProperty().addListener((observable) -> {

			if (getFileCache().size()>0) {

				/*Previewing.loadCurrentChan();*/ // * moved to WaveFile

				drawEverything();

				root.previewRefreshTrigger.scrollPanelStateProperty().addListener((observable1) -> {

					setHorizontalScale();
					drawEverything();
				});
			}
			else {
				root.previewRefreshTrigger.scrollPanelStateProperty().removeListener(((observable1) -> {}));
				clean();
//				cleanCurrentFileSignal();
			}
		});
	}



	void clean() {

		context.clearRect(0, 0, getWidth(), getHeight());
		drawFrame();
	}

	private void drawEverything() {

		if (theFlag) {

			theFlag = false;

			clean();
			drawAmplitudeGrid();
			drawFrame();
			drawWaveForm();
			drawTimeGrid();

			theFlag = true;
		}
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

	private void drawTimeGrid() {

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.6);

		double
			samplesPerSecond = getCurrentFileSamplesPerSecond(),
			bitsPerSample = getCurrentFileBitsPerSample(),
			fileLength = getCurrentFileSignalLength(),

			horizontalScroll = root.getHorizontalScrollPanel().getScrollValue(),
			hScale = root.getHorizontalScrollPanel().getScaleValue(),

			width = getWidth() - margin,
			height = getHeight() - margin,

			gridScale = (samplesPerSecond / 1000.), // ? 1 ms

			viewStep = horizontalScale,

			gridIncrement = gridScale * Math.pow(2., hScale - (int) hScale),

			x2 = (width / 2.) - gridIncrement,
			x,
			txt;

		do {
			x2 += gridIncrement;
			x = x2 - (horizontalScroll * horizontalScale);

			if (x > margin && x < fileLength) {

				txt = (x2 - (width / 2.)) / viewStep / gridScale;
				context.setStroke(GREY);
				context.strokeLine(x, 0, x, height);
				context.setStroke(DODGERBLUE);
				context.strokeText((int) txt + " ", x, height + margin * 0.85);
			}

		} while (x < width + margin && x < fileLength);
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



	private void setHorizontalScale() {

		horizontalScale = Math.pow(2., root.getHorizontalScrollPanel().getScaleValue());

		System.out.println(
			"guiPreviewPanel.setHorizontalScale() : getScaleValue() = "
				+ (int) Math.abs(root.getHorizontalScrollPanel().getScaleValue())
				+ "; hScale = "
				+ horizontalScale
		);
	}

	private boolean indexIsInRange(double index) {

		return index >= 0 && index < getCurrentFileSignalLength();
	} // indexInRange()

	private void drawWaveForm() {

		context.setTextAlign(CENTER);
		context.setFont(font);
		context.setLineWidth(0.75);
		context.setStroke(RED);

		int
			samplePyramidLevel = root.getHorizontalScrollPanel().getScaleValue() < 0.
							   	? (int) -root.getHorizontalScrollPanel().getScaleValue()
							   : 0;



		SamplePyramid
			samples = FileCache.getFile().getChannelAnchor().getSampleLevel(samplePyramidLevel);

		System.out.println(">>>\tPreviewPanel.drawWaveForm : samplePyramidLevel = "
					+ samplePyramidLevel
					+ "\n\tsamples.size = "
					+ samples.size()
		);

		double
			bitsPerSample = getCurrentFileBitsPerSample(),
			height = getHeight() - margin,
			verticalScroll = root.getVerticalScrollPanel().getScrollValue(),
			verticalScale = root.getVerticalScrollPanel().getScaleValue(),

			zoom = height * Math.pow(2., verticalScale),                // ! copy from drawHorizontals
			vOffset = (0.5 * zoom - 0.25 * height) * verticalScroll,    // !

			vScale = (Math.pow(2., bitsPerSample) / height) / Math.pow(2., verticalScale - 1.),
			y0 = (height / 2.) - vOffset,

			fileLength = getCurrentFileSignalLength(),
			width = getWidth() - margin,
			horizontalScroll = root.getHorizontalScrollPanel().getScrollValue(),

			x0 = width / 2.,

			hScaleParam = (horizontalScale - ((int) horizontalScale)) * Math.pow(2, samplePyramidLevel);

		{
			double
				index1Start = horizontalScroll / Math.pow(2., samplePyramidLevel),
				x1Start = x0,
				y1Start,

				index1End = horizontalScroll / Math.pow(2., samplePyramidLevel) + 1,
				x1End = x0 + 1 * horizontalScale,
				y1End = y0;

			do {

				index1Start--;
				index1End--;

				if (samplePyramidLevel == 0) {

					x1Start -= horizontalScale;
					x1End -= horizontalScale;
				}
				else {

					x1Start -= hScaleParam;
					x1End -= hScaleParam;
				}

				if (indexIsInRange(index1End) && index1End < samples.size())
					y1End = y0 - samples.getSampling((int) index1End).getValue() / vScale;

				if (indexIsInRange(index1Start) && index1Start < samples.size()) {

					y1Start = y0 - samples.getSampling((int) index1Start).getValue() / vScale;
					context.setStroke(RED);
					context.strokeLine(x1Start, y1Start, x1End, y1End);

					if (horizontalScale > 1.) {

						context.setStroke(new Color(0, 0, 0, Double.min((horizontalScale - 1.) / 2., 1.)));
						context.strokeOval(x1Start - 0.5, y1Start - 0.5, 1, 1);

					} // drawing sample points at zoom in

				}

			} while (index1End >= 0 && x1End > margin && index1End < samples.size());

		} // * print left side

		{
			double
				index2Start = horizontalScroll / Math.pow(2., samplePyramidLevel) - 1,
				x2Start = x0 - 1 - horizontalScale,
				y2Start = y0,

				index2End = horizontalScroll / Math.pow(2., samplePyramidLevel),
				x2End = x0,
				y2End;

			do {
				index2Start++;
				index2End++;

				if (samplePyramidLevel == 0) {
					x2Start += horizontalScale;
					x2End += horizontalScale;
				}
				else {
					x2Start += hScaleParam;
					x2End += hScaleParam;
				}

				if (indexIsInRange(index2Start) && index2Start < samples.size())
					y2Start = y0 - samples.getSampling((int) index2Start).getValue() / vScale;


				if (indexIsInRange(index2End) && index2End < samples.size()) {

					y2End = y0 - samples.getSampling((int) index2End).getValue() / vScale;
					context.setStroke(RED);
					context.strokeLine(x2Start, y2Start, x2End, y2End);

					if (horizontalScale > 1.) {

						context.setStroke(new Color(0, 0, 0, Double.min((horizontalScale - 1.) / 2., 1.)));
						context.strokeOval(x2Start - 0.5, y2Start - 0.5, 1, 1);

					}    // drawing sample points at zoom in
				}

			} while (index2End < fileLength && index2End < samples.size() && x2End < width + margin);

		} // * print right side
	}
}