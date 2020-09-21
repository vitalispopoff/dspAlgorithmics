package gui;

import data.FileCache;
import data.WaveFile;
import javafx.beans.binding.DoubleBinding;
import javafx.scene.canvas.*;
import javafx.scene.text.Font;

import static javafx.scene.paint.Color.*;


public class PreviewPanel extends Canvas {

	private static final double
		margin = 20.;

	private final GraphicsContext
		context;

	private final Font
		font = new Font("Arial", 10);

	private WaveFile
		waveFile;

	public PreviewPanel(Root root) {

		super();

		context = getGraphicsContext2D();

		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());


		// ! ------------------------------------------------------



		// ? ------------------------------------------------------

		FileCache.cacheIsEmptyStaticProperty().addListener((observable, oldValue, newValue) -> {


			if ( ! newValue) {

				waveFile = FileCache.loadCurrent();

				clean();
				drawBorders();
				drawHorizontals();

				root.previewRefreshTrigger.scrollPanelStateProperty().addListener((observable1, oldValue1, newValue1) -> {

					clean();
					drawBorders();
					drawHorizontals();
				});

			}

			else {

				root.previewRefreshTrigger.scrollPanelStateProperty().removeListener(((observable1, oldValue1, newValue1) -> {}));

				clean();

				System.out.println("cache empty");
			}

		});

		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());
	}


	void clean() {

		context.clearRect(0, 0, getWidth(), getHeight());
	}


	private void drawBorders() {

		context.setLineWidth(1);
		context.setStroke(BLUE);

		context.strokeRect(margin, 1, getWidth() - margin - 1., getHeight() - margin);

//		System.out.println("drawn");

	}

	private void drawHorizontals() {

		double
			verticalScale = 1.,
			horizontalScale = 1.,

			height = getHeight(),
			width = getWidth(),

			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;

		context.setLineWidth(1);
		context.setStroke(BLUE);
		context.strokeLine(0, height / 2, width, height / 2);

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)),
			leftMargin = 20;

		context.setFont(font);

		for (int i = 1; i <= numberOfLines; i++) {

			context.setStroke(DODGERBLUE);

			double
				y1 = (height / 2) - scaledHeight / (1 << i),
				y2 = (height / 2) + scaledHeight / (1 << i);

			int
				txt = (-(i - 1) * 6);

			if (y1 >= 0) {

				if (i > 1 && i < numberOfLines) {

					context.setStroke(BLUE);
					context.strokeText(Integer.toString(txt), 1, y1 + 4);

					context.setStroke(DODGERBLUE);
					context.strokeLine(leftMargin * 0.85, y1, width, y1);
				}

				else {
					context.strokeLine(leftMargin, y1, width, y1);
				}
			}

			if (y2 <= height && y1 != y2) {

				if (i > 1 && i < numberOfLines) {

					context.setStroke(BLUE);
					context.strokeText(Integer.toString(txt), 1, y2 + 4);

					context.setStroke(DODGERBLUE);
					context.strokeLine(leftMargin * 0.85, y2, width, y2);
				}

				else {
					context.setStroke(DODGERBLUE);
					context.strokeLine(leftMargin, y2, width, y2);
				}
			}
		}
	}


}