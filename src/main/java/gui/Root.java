package gui;

import gui.Menus.MainMenuController;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.File;
import java.io.IOException;
import java.net.URL;

import static gui.StageParams.*;

public class Root extends GridPane {

	private final static String
		location = "E:\\_LAB\\pl\\popoff\\dspAlgorithmics\\src\\main\\resources\\FXMLMainMenu.fxml";

	private final static double
		col0Dimension = 5.,
		col2Dimension = 25.,
		row0Dimension = 30.,
		row2Dimension = 25.;

	private final static ColumnConstraints
		col1 = new ColumnConstraints();

	private final static RowConstraints
		row1 = new RowConstraints();

	public final ScrollPanel
		horizontalScrollPanel,
		verticalScrollPanel;

	private final DoubleProperty
		dynamicAreaWidth = new SimpleDoubleProperty(),
		dynamicAreaHeight = new SimpleDoubleProperty();


	
	public Root(Stage stage){
		
		super();

		setMainMenu(stage);

		horizontalScrollPanel = new ScrollPanel(this, Orientation.HORIZONTAL);
		verticalScrollPanel = new ScrollPanel(this, Orientation.VERTICAL);

		add(horizontalScrollPanel, 1, 2);
		add(verticalScrollPanel, 2, 1);

		{
			dynamicAreaWidthProperty().bind(stage.widthProperty()
												.subtract(stageWAdjust)
												.subtract(col0Dimension)
												.subtract(col2Dimension));

			col1.minWidthProperty().bind(dynamicAreaWidthProperty());
			col1.maxWidthProperty().bind(dynamicAreaWidthProperty());

			getColumnConstraints().add(new ColumnConstraints(col0Dimension));
			getColumnConstraints().add(col1);
			getColumnConstraints().add(new ColumnConstraints(col2Dimension));

		}	// ? width property binding, and adding columns to the root

		{
			dynamicAreaHeightProperty().bind(stage.heightProperty()
												 .subtract(stageHAdjust)
												 .subtract(row0Dimension)
												 .subtract(row2Dimension));

			row1.minHeightProperty().bind(dynamicAreaHeightProperty());
			row1.maxHeightProperty().bind(dynamicAreaHeightProperty());

			getRowConstraints().add(new RowConstraints(row0Dimension));
			getRowConstraints().add(row1);
			getRowConstraints().add(new RowConstraints(row2Dimension));

		}	// ? height property binding, and adding rows to the root

		setGridLinesVisible(true);
	}

	public void bindScrollBarsVisibleProperties(MainScene s){

		horizontalScrollPanel.bindScrollBarVisibilityProperties(s);
		verticalScrollPanel.bindScrollBarVisibilityProperties(s);
	}

	public DoubleProperty dynamicAreaWidthProperty(){

		return dynamicAreaWidth;
	}

	public DoubleProperty dynamicAreaHeightProperty(){

		return dynamicAreaHeight;
	}

	private void setMainMenu(Stage stage) {

		MainMenuController.setStage(stage);

		try {

			URL
				url = new File(location).toURI().toURL();

			FXMLLoader
				loader = new FXMLLoader(url);

			Node
				bar = loader.load();

			add(bar, 0, 0, 3, 1);
			GridPane.setValignment(bar, VPos.TOP);
			GridPane.setHalignment(bar, HPos.LEFT);
		}

		catch (IOException e) {e.printStackTrace();}
	}
}