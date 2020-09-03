import gui.MainMenu;
import gui.WavePane;

import javafx.application.Application;
import javafx.beans.binding.Bindings;
import javafx.beans.binding.DoubleBinding;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.ReadOnlyDoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.text.Text;
import javafx.stage.*;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        Rectangle2D
            screenBounds = Screen.getPrimary().getBounds();

        double
            sceneWidth = screenBounds.getWidth() * 0.75,
            sceneHeight = screenBounds.getHeight() * 0.75;

        MainMenu
            bar = new MainMenu(stage);

        VBox
            root = new VBox(bar);

        WavePane
            wavePane = new WavePane(sceneWidth, sceneHeight);

        root.getChildren().add(wavePane);

        Scene
            scene = new Scene(root, sceneWidth, sceneHeight);

    // !  temporal  //-------------------------------------------------------------------------


        Text
            text1 = new Text(),
            text2 = new Text();

        root.getChildren().addAll(text1, text2);

        ReadOnlyDoubleProperty
            currentWidth = scene.widthProperty(),
            currentHeight = scene.heightProperty();

        stage.widthProperty().addListener(
            ((observable, oldValue, newValue) ->
                 text1.setText(String.valueOf(newValue))));

        stage.heightProperty().addListener(
            (observable, oldValue, newValue) ->
                text2.setText(String.valueOf(newValue)));

    //  ---------------------------------------------------------------------------------------

        stage.setScene(scene);
        stage.show();

//        stage.close();
    }

    public static void main(String[] args) {

        launch(args);
    }
}