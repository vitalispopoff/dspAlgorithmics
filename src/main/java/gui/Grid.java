package gui;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class Grid extends VBox{

	Color
		color = Color.LIGHTBLUE;

	int
		elementIndex = 18;

	public Grid(BorderPane root) {

		setBackground(new Background(new BackgroundFill(GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		StackPane
			left = new StackPane(),
			rite = new StackPane(),
			bottom = new StackPane();

		left.setBackground(getBackground());
		rite.setBackground(getBackground());
		bottom.setBackground(getBackground());
		left.setPrefWidth(10);
		rite.setPrefWidth(10);
		bottom.setMinHeight(25);

		root.setLeft(left);
		root.setRight(rite);
		root.setBottom(bottom);

		int
			initHeightParam = (int) root.getHeight() - 50,
			initWidthParam = (int) root.getWidth() - 20;

	//	grid elements //---------------------------------------------------------------------------------------

		ArrayList<Shape>
			elements = new ArrayList<>();

		for (int i = 1; i < elementIndex ; i++) {

			if (i % 2 == 0) {

				int
					shift = 1 + (Math.min(i, elementIndex - i) >>> 1);

				Rectangle
					r = new Rectangle(0, 0, initWidthParam, (initHeightParam >> shift) - 1);

				r.setOpacity(0);
				elements.add(r);
			}

			else {

				Line
					l = new Line(0, 0, initWidthParam, 0);

				l.setStrokeWidth(1);
				l.setStroke(color);
				elements.add(l);
			}
		}

		Rectangle
			topBlock = new Rectangle(0, 0, initWidthParam, initHeightParam >>> 5),
			bottomBlock = new Rectangle(0, 0, initWidthParam, initHeightParam >>> 5);

		topBlock.setOpacity(0);
		bottomBlock.setOpacity(0);

		elements.add(0, topBlock);
		elements.add(bottomBlock);

		getChildren().addAll(elements);

        root.widthProperty().addListener((observable, oldValue, newValue) -> {

			double
				parameter = ((double)newValue) - 20;

			setPrefWidth(parameter);

			if (((Rectangle) getChildren().get(0)).getWidth() != parameter)

				for (Node element : getChildren()) {

					if (element instanceof Rectangle)

						((Rectangle) element).setWidth(parameter);

					else
						((Line) element).setEndX(parameter);
				}
		});

        root.heightProperty().addListener((observable, oldValue, newValue) -> {

			int
				barHeight = (int) ((MainMenu)root.getTop()).getHeight(),
				parameter = (int) (double)newValue - 50 ,
				numberOfElements = getChildren().size();

			if (((Rectangle) getChildren().get(0)).getWidth() != parameter)

				for (int i = 0; i < numberOfElements; i+= 2){

					int
						shifter = (i == 0 || i == numberOfElements - 1)
							? 5
							: 1 + (Math.min(i, numberOfElements - i) >>> 1);

					((Rectangle) getChildren().get(i)).setHeight((parameter >>> shifter) - 1);
				}
		});

//		getChildren().addAll(elements);
		root.setCenter(this);
	}
}