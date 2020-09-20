package gui;

import algorithms.analyzers.BitRepresent;
import gui.Menus.MainMenuController;
import javafx.beans.binding.IntegerBinding;
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

    private final ScrollPanel
            horizontalScrollPanel,
            verticalScrollPanel;


    public Root(Stage stage) {

        super();

        setMainMenu(stage);

        horizontalScrollPanel = new ScrollPanel(this, Orientation.HORIZONTAL);
        verticalScrollPanel = new ScrollPanel(this, Orientation.VERTICAL);

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

        setScrollPanelsStateValues();
        bindScrollPanelsState();

		horizontalScrollPanel.scrollValueProperty().addListener((observable, oldValue, newValue) -> {

			System.out.println("BOOP");
		});

        scrollPanelsState.addListener((observable, oldValue, newValue) -> {

            System.out.println("BEEP");
        });

        setGridLinesVisible(true);
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
        } catch (IOException e) {
            e.printStackTrace();
        }
    }


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

    public DoubleProperty dynamicAreaWidthProperty() {

        return dynamicAreaWidth;
    }

    private final DoubleProperty
            dynamicAreaHeight = new SimpleDoubleProperty();

    public DoubleProperty dynamicAreaHeightProperty() {

        return dynamicAreaHeight;
    }



    private double
            areaWidth,
            areaHeight,
            hScrollValue,
            hScaleValue,
            vScrollValue,
            vScaleValue;

    private int
			change = -1;

    private void setScrollPanelsStateValues() {

        areaWidth = dynamicAreaWidth.get();
        areaHeight = dynamicAreaHeight.get();
        hScrollValue = horizontalScrollPanel.getScrollValue();
        hScaleValue = horizontalScrollPanel.getScaleValue();
        vScrollValue = verticalScrollPanel.getScrollValue();
        vScaleValue = verticalScrollPanel.getScaleValue();
    }

	private IntegerBinding scrollPanelsState;

	public IntegerBinding getScrollPanelsState() {

		return scrollPanelsState;
	}

    private void bindScrollPanelsState() {

        scrollPanelsState = new IntegerBinding() {

            {
                super.bind(
                        dynamicAreaWidthProperty(),
                        dynamicAreaHeightProperty(),
                        horizontalScrollPanel.scrollValueProperty(),
                        horizontalScrollPanel.scaleValueProperty(),
                        verticalScrollPanel.scrollValueProperty(),
                        verticalScrollPanel.scaleValueProperty()
                );
            }

            @Override
            protected int computeValue() {

                int
                        a = dynamicAreaWidth.get() - areaWidth != 0 ? ((- 1 *  change) & 1) : change & 1,
                        b = dynamicAreaHeight.get() - areaHeight != 0 ? ((- 1 *  change) & 2) : change & 2,
                        c = horizontalScrollPanel.getScrollValue() - hScrollValue != 0 ? ((- 1 *  change) & 4) : change & 4,
                        d = horizontalScrollPanel.getScaleValue() - hScaleValue != 0 ? ((- 1 *  change) & 8) : change & 8,
                        e = verticalScrollPanel.getScrollValue() - vScrollValue != 0 ? ((- 1 *  change) & 16) : change & 16,
                        f = verticalScrollPanel.getScaleValue() - vScaleValue != 0 ? ((- 1 *  change) & 32) : change & 32,
                        result = (a & 1) + (b & 2) + (c & 4) + (d & 8) + (e & 16) + (f & 32);

                setScrollPanelsStateValues();

				System.out.println(BitRepresent.bitRepresent(result));

                return result;
            }
        };
    }
}