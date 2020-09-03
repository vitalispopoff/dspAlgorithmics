import gui.MainMenu;
import gui.WavePane;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.stage.*;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        Rectangle2D
            screenBounds = Screen.getPrimary().getBounds();

        MainMenu
            bar = new MainMenu(stage);

        VBox
            root = new VBox(bar);

        Scene
            scene = new Scene(root, 640, 480);

        stage.setScene(scene);

        WavePane
            wavePane = new WavePane(stage);

/*        Line
            center = new Line(0, 250, 1000, 250);

        center.setStroke(Color.BLUE);

        wavePane.getChildren().add(center);*/

        root.getChildren().add(wavePane);

    // !  temporal  //-------------------------------------------------------------------------

    /*
            stage.widthProperty().addListener((observable, oldValue, newValue) -> {

            wavePane.setWidth((Double) newValue * 0.75);
        });

        stage.heightProperty().addListener((observable, oldValue, newValue) -> {

            wavePane.setHeight((Double) newValue * 0.75);
        });
    */     // * disposable

    /*
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
    */     // * temporal disposable

    // ! rebuild wavePane ---------------------------------------------------------------------

/*        Line
            center = new Line(0, 0, 1920, 0);

        center.setStroke(Color.BLUE);*/

/*
        stage.heightProperty().addListener(
            (observable, oldValue, newValue) -> {

                wavePane.setHeight((double) newValue);
                center.setTranslateY(((double)oldValue - (double) newValue) / 2.);

            }
        );
*/

//        wavePane.getChildren().add(center);

/*        for (int i = 2; i < yScale / 2; i <<= 1) {

            Line
                line1 = new Line(0, 0, yScale, 0),
                line2 = new Line(0, 0, yScale, 0);

            line1.setStroke(Color.LIGHTBLUE);
            line2.setStroke(Color.LIGHTBLUE);

            line1.setTranslateY(i);
            line2.setTranslateY(-i);

            wavePane.getChildren().addAll(line1, line2);*/

    //  ---------------------------------------------------------------------------------------

        stage.show();

//        stage.close();
    }

    public static void main(String[] args) {

        launch(args);
    }
}