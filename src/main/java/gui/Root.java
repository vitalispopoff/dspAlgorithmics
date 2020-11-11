package gui;

import gui.Menus.MainMenuController;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.Node;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;

import static gui.StageParams.*;

public class Root extends GridPane {

    private final static String
            location = "src\\main\\resources\\FXMLMainMenu.fxml";

    private final static double
            col0Dimension = 5.,
            col2Dimension = 25.,
            row0Dimension = 30.,
            row2Dimension = 25.;

    private final static ColumnConstraints
            col1 = new ColumnConstraints();

    private final static RowConstraints
            row1 = new RowConstraints();


    public final PreviewRefreshTrigger
        previewRefreshTrigger;


    public Root(Stage stage) {

        super();

        horizontalScrollPanel = new ScrollPanel(this, Orientation.HORIZONTAL);
        getHorizontalScrollPanel().setAlignment(Pos.CENTER_RIGHT);

        verticalScrollPanel = new ScrollPanel(this, Orientation.VERTICAL);
        getVerticalScrollPanel().setAlignment(Pos.TOP_CENTER);

        previewRefreshTrigger = new PreviewRefreshTrigger(this);

        PreviewPanel
            preview = new PreviewPanel(this);

        setMainMenu(stage);

        add(preview, 1, 1);
        add(horizontalScrollPanel, 1, 2);
        add(verticalScrollPanel, 2, 1);

        {
            dynamicAreaWidthProperty().bind(
                    stage.widthProperty()
                            .subtract(stageWAdjust)
                            .subtract(col0Dimension)
                            .subtract(col2Dimension));

            col1.minWidthProperty().bind(dynamicAreaWidthProperty());
            col1.maxWidthProperty().bind(dynamicAreaWidthProperty());

            getColumnConstraints().add(new ColumnConstraints(col0Dimension));
            getColumnConstraints().add(col1);
            getColumnConstraints().add(new ColumnConstraints(col2Dimension));

        }    // ? width property binding, and adding columns to the root

        {
            dynamicAreaHeightProperty().bind(
                    stage.heightProperty()
                            .subtract(stageHAdjust)
                            .subtract(row0Dimension)
                            .subtract(row2Dimension));

            row1.minHeightProperty().bind(dynamicAreaHeightProperty());
            row1.maxHeightProperty().bind(dynamicAreaHeightProperty());

            getRowConstraints().add(new RowConstraints(row0Dimension));
            getRowConstraints().add(row1);
            getRowConstraints().add(new RowConstraints(row2Dimension));

        }    // ? height property binding, and adding rows to the root
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
        catch (IOException e) {
            e.printStackTrace();
        }
    }



    private final ScrollPanel
        horizontalScrollPanel,
        verticalScrollPanel;

    public ScrollPanel getHorizontalScrollPanel() {

        return horizontalScrollPanel;
    }

    public ScrollPanel getVerticalScrollPanel() {

        return verticalScrollPanel;
    }

    public void bindScrollBarsVisibleProperties(BooleanProperty b) {

        horizontalScrollPanel.bindScrollBarVisibilityProperties(b);
        verticalScrollPanel.bindScrollBarVisibilityProperties(b);
    }



    private final DoubleProperty
            dynamicAreaWidth = new SimpleDoubleProperty();

    public double getDynamicAreaWidth(){

        return dynamicAreaWidth.get();
    }

    public DoubleProperty dynamicAreaWidthProperty() {

        return dynamicAreaWidth;
    }



    private final DoubleProperty
            dynamicAreaHeight = new SimpleDoubleProperty();

    public double getDynamicAreaHeight(){

        return dynamicAreaHeight.get();
    }

    public DoubleProperty dynamicAreaHeightProperty() {

        return dynamicAreaHeight;
    }

}