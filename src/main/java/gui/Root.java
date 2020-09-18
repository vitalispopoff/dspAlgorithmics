package gui;

import javafx.beans.property.*;
import javafx.geometry.Orientation;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import static gui.StageParams.*;

public class Root extends GridPane {

	static double
		col0Dimension = 5.,
		col2Dimension = 25.,
		row0Dimension = 30.,
		row2Dimension = 25.;

	static ColumnConstraints
		col1 = new ColumnConstraints();

	static RowConstraints
		row1 = new RowConstraints();

	private final DoubleProperty
		dynamicAreaWidth = new SimpleDoubleProperty(),
		dynamicAreaHeight = new SimpleDoubleProperty();
	
	public Root(Stage stage){
		
		super();

		add(new ScrollPanel(this, Orientation.HORIZONTAL), 1, 2);
		add(new ScrollPanel(this, Orientation.VERTICAL), 2, 1);

		dynamicAreaWidthProperty().bind(
			stage.widthProperty()
				.subtract(stageWAdjust)
				.subtract(col0Dimension)
				.subtract(col2Dimension));

		col1.minWidthProperty().bind(dynamicAreaWidthProperty());
		col1.maxWidthProperty().bind(dynamicAreaWidthProperty());

		dynamicAreaHeightProperty().bind(
			stage.heightProperty()
				.subtract(stageHAdjust)
				.subtract(row0Dimension)
				.subtract(row2Dimension)
		);

		row1.minHeightProperty().bind(dynamicAreaHeightProperty());
		row1.maxHeightProperty().bind(dynamicAreaHeightProperty());

		getColumnConstraints().add(new ColumnConstraints(col0Dimension));
		getColumnConstraints().add(col1);
		getColumnConstraints().add(new ColumnConstraints(col2Dimension));

		getRowConstraints().add(new RowConstraints(row0Dimension));
		getRowConstraints().add(row1);
		getRowConstraints().add(new RowConstraints(row2Dimension));

		setGridLinesVisible(true);


	}

	public DoubleProperty dynamicAreaWidthProperty(){

		return dynamicAreaWidth;
	}

	public DoubleProperty dynamicAreaHeightProperty(){

		return dynamicAreaHeight;
	}
}