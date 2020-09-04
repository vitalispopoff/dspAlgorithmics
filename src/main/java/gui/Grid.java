package gui;

import javafx.geometry.Insets;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;

import static javafx.scene.paint.Color.*;

public class Grid extends VBox{

	public Grid(BorderPane root) {

		int
			initHeightParam = (int) root.getHeight() - 50,
			initWidthParam = (int) root.getWidth() - 20;

		Rectangle
			borderTop = new Rectangle(0, 0, initWidthParam, initHeightParam >> 5),
			block_0 = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 2) - 1),
			block_1 = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 3) - 1),
			block_2 = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 4) - 1),
			block_3 = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 5) - 1),
			block_3last = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 5) - 1),
			block_2last = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 4) - 1),
			block_1last = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 3) - 1),
			block_last = new Rectangle(0, 0, initWidthParam, (initHeightParam >> 2) - 1),
			borderBottom = new Rectangle(0, 0, initWidthParam, initHeightParam >> 5);

		Line
			full = new Line(0, 0, initWidthParam, 0),
			half = new Line(0, 0, initWidthParam, 0),
			quarter = new Line(0, 0, initWidthParam, 0),
			eighth = new Line(0, 0, initWidthParam, 0),
				center = new Line(0, 0, initWidthParam, 0),
			_eighth = new Line(0, 0, initWidthParam, 0),
			_quarter = new Line(0, 0, initWidthParam, 0),
			_half = new Line(0, 0, initWidthParam, 0),
			_full = new Line(0, 0, initWidthParam, 0);

		VBox
			grid = new VBox(
			borderTop,
			full, block_0,
			half, block_1,
			quarter, block_2,
			eighth, block_3,
			center,
			block_3last, _eighth,
			block_2last, _quarter,
			block_1last, _half,
			block_last, _full,
			borderBottom
		);

		Color
			color = Color.LIGHTBLUE;

		Background
			bcg = new Background(new BackgroundFill(GRAY, CornerRadii.EMPTY, Insets.EMPTY));

        this.setBackground(bcg);

		StackPane
			left = new StackPane(),
			rite = new StackPane(),
			bottom = new StackPane();

	        left.setBackground(bcg);
	        rite.setBackground(bcg);
		    bottom.setBackground(bcg);
	        left.setPrefWidth(10);
	        rite.setPrefWidth(10);
	        bottom.setMinHeight(25);

	        root.setLeft(left);
	        root.setRight(rite);
	        root.setBottom(bottom);

		{

			borderTop.setOpacity(0);
			block_0.setOpacity(0);
			block_1.setOpacity(0);
			block_2.setOpacity(0);
			block_3.setOpacity(0);
			block_3last.setOpacity(0);
			block_2last.setOpacity(0);
			block_1last.setOpacity(0);
			block_last.setOpacity(0);
			borderBottom.setOpacity(0);

			center.setStroke(color);
			center.setStrokeWidth(1);
			half.setStroke(color);
			half.setStrokeWidth(1);
			_half.setStroke(color);
			_half.setStrokeWidth(1);
			quarter.setStroke(color);
			quarter.setStrokeWidth(1);
			_quarter.setStroke(color);
			_quarter.setStrokeWidth(1);
			eighth.setStroke(color);
			eighth.setStrokeWidth(1);
			_eighth.setStroke(color);
			_eighth.setStrokeWidth(1);
			full.setStroke(color);
			full.setStrokeWidth(1);
			_full.setStroke(color);
			full.setStrokeWidth(1);

		}   // * setting colors & opacity

        root.widthProperty().addListener((observable, oldValue, newValue) -> {

			double
				parameter = ((double)newValue) - 20;

			grid.setPrefWidth(parameter);

			block_0.setWidth(parameter);
			block_last.setWidth(parameter);
			block_1.setWidth(parameter);
			block_1last.setWidth(parameter);
			block_2.setWidth(parameter);
			block_2last.setWidth(parameter);
			block_3.setWidth(parameter);
			block_3last.setWidth(parameter);

			center.setEndX(parameter);
			half.setEndX(parameter);
			_half.setEndX(parameter);
			quarter.setEndX(parameter);
			_quarter.setEndX(parameter);
			eighth.setEndX(parameter);
			_eighth.setEndX(parameter);
			full.setEndX(parameter);
			_full.setEndX(parameter);
		});

        root.heightProperty().addListener((observable, oldValue, newValue) -> {

			int
				barHeight = (int) ((MainMenu)root.getTop()).getHeight(),
				parameter = (int) (double)newValue - 50 ;

			if (block_0.getHeight() != (parameter >>> 2) - 1.){

				borderTop.setHeight(parameter >> 5);
				borderBottom.setHeight(parameter >> 5);

				block_0.setHeight((parameter >>> 2) - 1.);
				block_last.setHeight((parameter >>> 2) - 1.);

				block_1.setHeight((parameter >>> 3) - 1.);
				block_1last.setHeight((parameter >>> 3) - 1.);

				block_2.setHeight((parameter >>> 4) - 1.);
				block_2last.setHeight((parameter >>> 4) - 1.);

				block_3.setHeight((parameter >>> 5) - 1.);
				block_3last.setHeight((parameter >>> 5) - 1.);
			}
		});



		this.getChildren().addAll(
			borderTop,
			full, block_0,
			half, block_1,
			quarter, block_2,
			eighth, block_3,
			center,
			block_3last, _eighth,
			block_2last, _quarter,
			block_1last, _half,
			block_last, _full,
			borderBottom
		);

		root.setCenter(this);
	}
}