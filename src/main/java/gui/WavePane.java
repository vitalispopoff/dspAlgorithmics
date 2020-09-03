package gui;

import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;

public class WavePane extends StackPane {

	public WavePane(double sceneWidth, double sceneHeight){

		setPrefSize(sceneWidth, sceneHeight);
		setMinSize(520, 260);
//		setMaxSize(sceneWidth, sceneHeight);

		setBorder(new Border(new BorderStroke(Color.BLUE, BorderStrokeStyle.SOLID, CornerRadii.EMPTY, BorderWidths.FULL)));

		Line
			center = new Line(0, 0, sceneWidth, 0);

		center.setStroke(Color.BLUE);

		getChildren().add(center);

		for (int i = 2; i < sceneHeight / 2; i <<= 1) {

			Line
				line1 = new Line(0, 0, sceneWidth, 0),
				line2 = new Line(0, 0, sceneWidth, 0);

			line1.setStroke(Color.LIGHTBLUE);
			line2.setStroke(Color.LIGHTBLUE);

			line1.setTranslateY(i);
			line2.setTranslateY(-i);

			getChildren().addAll(line1, line2);
		}
	}
}