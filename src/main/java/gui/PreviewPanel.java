package gui;

import data.FileCache;
import data.WaveFile;
import javafx.beans.Observable;
import javafx.beans.binding.NumberBinding;
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

	public PreviewPanel(Root root){

		super();

		context = getGraphicsContext2D();

		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());

/*		root.previewRefreshTrigger.getScrollPanelsState().addListener((observable, oldValue, newValue) -> {

			clean();
			drawBorders();
		});*/






		FileCache.currentIndexDueProperty().addListener((observable, oldValue, newValue) -> {

			NumberBinding
				o = root.dynamicAreaHeightProperty().add(root.dynamicAreaWidthProperty());

			if ((int)newValue >= 0) {

				waveFile = FileCache.loadCurrent();

				drawBorders();

					o.addListener((observable1, oldValue1, newValue1) -> {

						clean();
						drawBorders();
				});
			}

			else {

				o.removeListener(((observable1, oldValue1, newValue1) -> {}));

				clean();
			}

		});

		widthProperty().bind(root.dynamicAreaWidthProperty());
		heightProperty().bind(root.dynamicAreaHeightProperty());
	}



	void clean() {

			context.clearRect(0, 0, getWidth(), getHeight());
		}


	private void drawBorders(){

		context.setLineWidth(1);
		context.setStroke(BLUE);

		context.strokeRect(margin, 1, getWidth() - margin - 1., getHeight() - margin);

	}




}