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

		populateBorders(root);
		populateGrid(root);

		observeWindow(root);

		/*		int
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

		getChildren().addAll(elements);*/ // ! moved to a private method - disposable

		/*        root.widthProperty().addListener((observable, oldValue, newValue) -> {

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
		});*/	// ! moved to a private method - disposable

		root.setCenter(this);
	}



	private void populateBorders(BorderPane root){

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
	}

	private void populateGrid(BorderPane root){

		int
			initHeightParam = (int) root.getHeight() - 50,
			initWidthParam = (int) root.getWidth() - 20;

		ArrayList<Shape>
			elements = new ArrayList<>();

		for (int i = 0; i < elementIndex ; i++) {

			if (i % 2 == 0) {

/*				int
					shifter = (i == 0 || i == elementIndex - 1)
								  ? 5
								  : 1 + (Math.min(i, elementIndex - i) >>> 1);*/	// * disposable

/*				int
					shifter = getShifter(i, elementIndex);*/	// * disposable

				double
					h = calculateHeight(initHeightParam, i, elementIndex);

				Rectangle
					r = new Rectangle(0, 0, initWidthParam, /*(initHeightParam >> shifter) - 1*/h);

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

	/*		Rectangle
			topBlock = new Rectangle(0, 0, initWidthParam, initHeightParam >>> 5),
			bottomBlock = new Rectangle(0, 0, initWidthParam, initHeightParam >>> 5);

		topBlock.setOpacity(0);
		bottomBlock.setOpacity(0);

		elements.add(0, topBlock);
		elements.add(bottomBlock);*/	// * top and bottom blocks - disposable

		getChildren().addAll(elements);
	}

	private void observeWindow(BorderPane root){

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
//				barHeight = (int) ((MainMenu)root.getTop()).getHeight(),
				parameter = (int) (double)newValue - 50 ,
				numberOfElements = getChildren().size();

			if (((Rectangle) getChildren().get(0)).getWidth() != parameter)

				for (int i = 0; i < numberOfElements; i+= 2){

	/*					int
						shifter = (i == 0 || i == numberOfElements - 1)
									  ? 5
									  : 1 + (Math.min(i, numberOfElements - i) >>> 1);*/	// * disposable

	/*					int
							shifter = getShifter(i, numberOfElements);*/	// * disposable

					double
						h = calculateHeight(parameter, i, numberOfElements);

					((Rectangle) getChildren().get(i)).setHeight(/*(parameter >>> shifter) - 1*/h );
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

/*	private int getShifter(int i, int numberOfElements){

		return (i == 0 || i == numberOfElements - 1)
						  ? 5
						  : 1 + (Math.min(i, numberOfElements - i) >>> 1);
	}*/	// * disposable

}