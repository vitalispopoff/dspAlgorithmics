package gui;

import javafx.scene.layout.ColumnConstraints;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.RowConstraints;
import javafx.stage.Stage;

import java.util.concurrent.atomic.AtomicReference;

import static gui.StageParams.*;

public class Root extends GridPane {

	static double
		stageW = getInitialStageWidth(),
		stageH = StageParams.getInitialStageHeight(),
		stageWAdjust = StageParams.stageWAdjust,
		stageHAdjust = StageParams.stageHAdjust,
		col0Dimension = 5.,
		col2Dimension = 25.,
		row0Dimension = 30.,
		row2Dimension = 25.;

	static AtomicReference<Double>
		col1W = new AtomicReference<>(stageW - col0Dimension - col2Dimension),
		row1H = new AtomicReference<>(stageH - row0Dimension - row2Dimension);

	static ColumnConstraints
		col0 = new ColumnConstraints(col0Dimension/*, col0Dimension, col0Dimension*/),
		col1 = new ColumnConstraints(col1W.get()/*, col1W.get(), col1W.get()*/),
		col2 = new ColumnConstraints(col2Dimension/*, col2Dimension, col2Dimension*/);

	static RowConstraints
		row0 = new RowConstraints(row0Dimension/*, row0Dimension, row0Dimension*/),
		row1 = new RowConstraints(row1H.get()/*, row1H.get(), row1H.get()*/),
		row2 = new RowConstraints(row2Dimension/*, row2Dimension, row2Dimension*/);
	
	public Root(Stage stage){
		
		super();

		col1.minWidthProperty().bind(
			stage.widthProperty()
				.subtract(stageWAdjust)
				.subtract(col0Dimension)
				.subtract(col2Dimension));

		col1.maxWidthProperty().bind(
			stage.widthProperty()
				.subtract(stageWAdjust)
				.subtract(col0Dimension)
				.subtract(col2Dimension));

		row1.minHeightProperty().bind(
			stage.heightProperty()
				.subtract(stageHAdjust)
				.subtract(row0Dimension)
				.subtract(row2Dimension)
		);
		row1.maxHeightProperty().bind(
			stage.heightProperty()
				.subtract(stageHAdjust)
				.subtract(row0Dimension)
				.subtract(row2Dimension)
		);

		getColumnConstraints().add(col0);
		getColumnConstraints().add(col1);
		getColumnConstraints().add(col2);

		getRowConstraints().add(row0);
		getRowConstraints().add(row1);
		getRowConstraints().add(row2);

		setGridLinesVisible(true);
	}
	
	
	
	
	
}