import gui.MainMenu;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;

import javafx.scene.layout.*;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;
import static javafx.scene.paint.Color.*;

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

    /*
        WavePane
            wavePane = new WavePane(stage);
        root.getChildren().add(wavePane);
*/  // ! stackPane - fails miserably

        Rectangle
            block_0 = new Rectangle(0, 0, 640, 119),
            block_1 = new Rectangle(0, 0, 640, 59),
            block_2 = new Rectangle(0, 0, 640, 29),
            block_3 = new Rectangle(0, 0, 640, 14),
            block_3last = new Rectangle(0, 0, 640, 14),
            block_2last = new Rectangle(0, 0, 640, 24),
            block_1last = new Rectangle(0, 0, 640, 59),
            block_last = new Rectangle(0, 0, 640, 119);

        block_0.setOpacity(0);
        block_1.setOpacity(0);
        block_2.setOpacity(0);
        block_3.setOpacity(0);
        block_3last.setOpacity(0);
        block_2last.setOpacity(0);
        block_1last.setOpacity(0);
        block_last.setOpacity(0);

        Line
            half = new Line(0, 0, 640, 0),
            quarter = new Line(0, 0, 640, 0),
            eighth = new Line(0, 0, 640, 0),
            center = new Line(0, 0, 640, 0),
            _eighth = new Line(0, 0, 640, 0),
            _quarter = new Line(0, 0, 640, 0),
            _half = new Line(0, 0, 640, 0);

        center.setStroke(RED);
        center.setStrokeWidth(1);
        half.setStroke(RED);
        half.setStrokeWidth(1);
        _half.setStroke(RED);
        _half.setStrokeWidth(1);
        quarter.setStroke(RED);
        quarter.setStrokeWidth(1);
        _quarter.setStroke(RED);
        _quarter.setStrokeWidth(1);
        eighth.setStroke(RED);
        eighth.setStrokeWidth(1);
        _eighth.setStroke(RED);
        _eighth.setStrokeWidth(1);

        VBox
            grid = new VBox(
            block_0,
            half,
            block_1,
            quarter,
            block_2,
            eighth,
            block_3,
            center,
            block_3last,
            _eighth,
            block_2last,
            _quarter,
            block_1last,
            _half,
            block_last
        );

        root.getChildren().add(grid);


        stage.getScene().widthProperty().addListener((observable, oldValue, newValue) -> {

            double
                parameter = ((double)newValue);

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
        });

        stage.getScene().heightProperty().addListener((observable, oldValue, newValue) -> {

            int
                parameter = (int) (double)newValue;

            if (block_0.getHeight() != (parameter >>> 2) - 1.){

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