package gui;

import javafx.geometry.Pos;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.Stage;

public class WavePane extends StackPane {

	public WavePane(Stage stage) {

		setAlignment(Pos.TOP_LEFT);
		setTranslateY(-12.5);

		double
			initialWidth = stage.getScene().widthProperty().getValue(),
			initialHeight = stage.getScene().heightProperty().getValue();

		setMinHeight(initialHeight);

		Line
			center =  new Line(0, 1, 1000, 1);

		center.setStroke(Color.BLUE);
		getChildren().add(center);
		setAlignment(getChildren().get(0), Pos.CENTER_LEFT);


		stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {

			setMinWidth((double) newValue);

				center.setEndX(((double)newValue));
		});

		stage.getScene().heightProperty()
			.addListener((observable, oldValue, newValue) -> setMinHeight((double) newValue));
	}
}