package gui;

import javafx.beans.property.*;
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
		col1Width = new SimpleDoubleProperty(),
		row1Height = new SimpleDoubleProperty();
	
	public Root(Stage stage){
		
		super();

		col1WidthProperty().bind(
			stage.widthProperty()
				.subtract(stageWAdjust)
				.subtract(col0Dimension)
				.subtract(col2Dimension));

		col1.minWidthProperty().bind(col1WidthProperty());
		col1.maxWidthProperty().bind(col1WidthProperty());

		getRow1HeightProperty().bind(
			stage.heightProperty()
				.subtract(stageHAdjust)
				.subtract(row0Dimension)
				.subtract(row2Dimension)
		);

		row1.minHeightProperty().bind(getRow1HeightProperty());
		row1.maxHeightProperty().bind(getRow1HeightProperty());

		getColumnConstraints().add(new ColumnConstraints(col0Dimension));
		getColumnConstraints().add(col1);
		getColumnConstraints().add(new ColumnConstraints(col2Dimension));

		getRowConstraints().add(new RowConstraints(row0Dimension));
		getRowConstraints().add(row1);
		getRowConstraints().add(new RowConstraints(row2Dimension));

		setGridLinesVisible(true);
	}

	public DoubleProperty col1WidthProperty(){

		return col1Width;
	}

	public DoubleProperty getRow1HeightProperty(){

		return row1Height;
	}
}