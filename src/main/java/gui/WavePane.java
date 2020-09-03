package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class WavePane extends StackPane {

	public WavePane(Stage stage) {

		double
			initialWidth = stage.getScene().widthProperty().getValue(),
			initialHeight = stage.getScene().heightProperty().getValue();

		setMinHeight(initialHeight);

		setTranslateY(-12);

		Line
			line = new Line(0, 0, 1000, 0);

		line.setStartY(initialHeight / 2.);
		line.setEndY(initialHeight / 2.);

		line.setStroke(Color.BLUE);



		stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {

			setMinWidth((double) newValue);

			line.setEndX(((double)newValue));
//			System.out.println(newValue + " " + getWidth());
		});

		stage.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {

			setMinHeight((double) newValue);
//			line.setTranslateY((((double) newValue) - ((double)oldValue)) / 2.);
			if(line.getStartY() != (double)newValue) {

				line.setStartY(((double) newValue) / 2.);
				line.setEndY(((double) newValue) / 2.);
			}
//			System.out.println(newValue + " " + getHeight());
		});

		getChildren().add(line);








	/*		for (int i = 2; i < yScale / 2; i <<= 1) {

			Line
				line1 = new Line(0, 0, yScale, 0),
				line2 = new Line(0, 0, yScale, 0);

			line1.setStroke(Color.LIGHTBLUE);
			line2.setStroke(Color.LIGHTBLUE);

			line1.setTranslateY(i);
			line2.setTranslateY(-i);

			getChildren().addAll(line1, line2);
		}*/    // ? additional lines - disposable ?


	}
}