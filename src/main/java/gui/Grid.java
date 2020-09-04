package gui;

import data.WaveFile;
import data.structure.Strip;

import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;

import java.util.ArrayList;

import static javafx.scene.paint.Color.*;

public class Grid extends VBox{

	Canvas
		canvas;

	public GraphicsContext
		gc;

	Color
		color = Color.LIGHTBLUE;

	int
		elementIndex = 18;

//	-------------------------------------------------------------------------------------------

	public Grid(BorderPane root) {

		setBackground(new Background(new BackgroundFill(GRAY, CornerRadii.EMPTY, Insets.EMPTY)));

		populateBorders(root);
		populateGrid(root);
		updateToWindow(root);

		canvas = new Canvas(getWidth(), getHeight());

		StackPane
			stack = new StackPane(this);

		root.setCenter(stack);



		gc = canvas.getGraphicsContext2D();

//		stack.getChildren().add(canvas);

	}

//	-------------------------------------------------------------------------------------------

	private void populateBorders(BorderPane root){

		StackPane[] borders = new StackPane[2];

		for (int i = 0; i < 2; i++){

			borders[i] = new StackPane();
			borders[i].setBackground(getBackground());
			borders[i].setPrefWidth(10);
		}

		root.setLeft(borders[0]);
		root.setRight(borders[1]);
	}

	private void populateGrid(BorderPane root){

		int
			initHeightParam = (int) root.getHeight() - 50,
			initWidthParam = (int) root.getWidth() - 20;

		ArrayList<Shape>
			elements = new ArrayList<>();

		for (int i = 0; i < elementIndex ; i++) {

			if (i % 2 == 0) {

				double
					h = calculateHeight(initHeightParam, i, elementIndex);

				Rectangle
					r = new Rectangle(0, 0, initWidthParam, h);

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

		getChildren().addAll(elements);
	}

	private void updateToWindow(BorderPane root){

		root.widthProperty().addListener((observable, oldValue, newValue) -> {

			double
				parameter = ((double)newValue) - 20;

			setPrefWidth(parameter);

			if (((Rectangle) getChildren().get(0)).getWidth() != parameter)

				for (Node element : getChildren()) {

					if (element instanceof Rectangle) ((Rectangle) element).setWidth(parameter);

					else ((Line) element).setEndX(parameter);
				}
		});

		root.heightProperty().addListener((observable, oldValue, newValue) -> {

			int
				parameter = (int) (double)newValue - 50 ,
				numberOfElements = getChildren().size();

			if (((Rectangle) getChildren().get(0)).getWidth() != parameter)

				for (int i = 0; i < numberOfElements; i+= 2){

					double
						h = calculateHeight(parameter, i, numberOfElements);

					((Rectangle) getChildren().get(i)).setHeight(h);
				}
		});
	}

	private double calculateHeight(int parameter, int i, int numberOfElements){

		int
			shifter = (i == 0 || i == numberOfElements - 1)
						  ? 5
						  : 1 + (Math.min(i, numberOfElements - i) >>> 1);

		return  (parameter >>> shifter) - 1;
	}
}