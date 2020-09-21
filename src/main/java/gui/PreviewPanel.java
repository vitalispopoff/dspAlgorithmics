package gui;

import data.*;

import data.structure.Strip;
import data.structure.WaveHeader;
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

		FileCache.currentIndexProperty().addListener((observable, oldValue, newValue) -> {

			if ((int) newValue >= 0) {

				waveFile = FileCache.getCurrentFile();

				clean();
				drawBorders();
				drawHorizontals();
				drawVerticals();
				drawWaveform();

				root.previewRefreshTrigger.scrollPanelStateProperty()
					.addListener((observable1, oldValue1, newValue1) -> {

					clean();
					drawBorders();
					drawHorizontals();
					drawVerticals();
					drawWaveform();
				});
			}

			else {

				root.previewRefreshTrigger.scrollPanelStateProperty()
					.removeListener(((observable1, oldValue1, newValue1) -> {}));

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
			verticalScale = 1.,
			horizontalScale = 1.,
			
			height = getHeight() - margin,
			width = getWidth(),

			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)) - 1 ;


		context.setStroke(DODGERBLUE);
		context.setTextAlign(TextAlignment.RIGHT);
		context.setFont(font);

		context.strokeLine(margin * 0.75,  (int) height >>> 1, width, (int) height >>> 1);

		for (int i = 1; i <= numberOfLines; i++) {

			context.setStroke(DODGERBLUE);

			double
				y1 = (height / 2) - scaledHeight / (1 << i),
				y2 = (height / 2) + scaledHeight / (1 << i);

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

	private void drawVerticals() {

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
			verticalGridResolution = (int) ((samplingRate - 1) * root.getHorizontalScrollPanel().getScaleValue()) + 1,
			movement = computeMovement(),
			verticalGridMovement = movement % verticalGridResolution;

		context.setStroke(LIGHTGREY);


		for (int i = 0 ; i < width + movement; i += verticalGridResolution) {

			int
				x = (int) (margin + width / 2 - computeMovement() + i);

			if (root.getHorizontalScrollPanel().getScrollValue() < 1.)

				context.strokeLine(x, 0, x, height);
		}
	}

	void drawWaveform() {

		context.setLineWidth(1);

		double
			verticalScale = 1.,
			horizontalScale = 1.,

			height = getHeight() - margin,
			width = getWidth() - margin,

			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;

		Strip
			strip = FileCache.getCurrentFile().getSignal().getStrip(0);

		WaveHeader
			currentHeader = FileCache.getCurrentFile().getHeader();

		int
			waveLength = strip.size(),
			bitsPerSample = currentHeader.getField(BITS_PER_SAMPLE) - 1,
			samplingRate = currentHeader.getField(SAMPLE_PER_SEC),
			verticalGridResolution = samplingRate / 1000,
			movement = computeMovement() - (int) width / 2,
			verticalGridMovement = movement % verticalGridResolution;

		for (int i = 1; i < Math.min(width, strip.size()); i++) {

			if (i + movement < strip.size() && i + movement > 0) {

				context.setStroke(RED);
				double
					prevSample = (double) strip.get(i + movement - 1),
					sample = (double) strip.get(i + movement),

					verticalCenter = height / 2,

					prevSampleAmplitude = prevSample / (double) (1 << bitsPerSample),
					prevDcOffset = verticalCenter * (1 - prevSampleAmplitude),

					sampleAmplitude = sample / (double) (1 << bitsPerSample),
					dcOffset = verticalCenter * (1 - sampleAmplitude);

				context.strokeLine(i + margin, prevDcOffset, i + 1 + margin, dcOffset);
			}

			else{

				context.setStroke(DARKRED);
				context.strokeLine(i + margin, ((int) height >> 1), i + 1 + margin, ((int) height >> 1));
			}
		}
	}

	private int computeMovement(){

		Strip
			strip = FileCache.getCurrentFile().getSignal().getStrip(0);

		double
			height = getHeight() - margin,
			width = getWidth() - margin;

		return (int) (root.getHorizontalScrollPanel().getScrollValue() * (strip.size() + width / 2));
	}
}