import data.structure.FileContentStructure;
import data.structure.Strip;
import gui.MainMenu;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        int
            sceneHeight = 600,
            barHeight = 25,
            scrollerHeight = 16,
            canvasHeight = sceneHeight - barHeight - scrollerHeight,

            sceneWidth = 1200,
            canvasWidth = sceneWidth - 10;

        MainMenu
            bar = new MainMenu(stage);

        bar.setMinHeight(barHeight);
        bar.setMaxHeight(barHeight);

        VBox
            vBox = new VBox(bar);

        Scene
            scene = new Scene(vBox, sceneWidth, sceneHeight);



    //  ---------------------------------------------------------------------------------------

        data.Wave
            wave = new data.Wave (new java.io.File("src\\main\\resources\\shortie-mono-16bit.wav"));

        Strip
            strip = wave.getSignal().getStrip(0);

        ScrollBar
            scroller = new ScrollBar();

        scroller.setMinHeight(scrollerHeight);
        scroller.setMaxHeight(scrollerHeight);

        scroller.setMax(strip.size() - canvasWidth);
        scroller.setMin(0);

        Canvas
            canvas = new Canvas(canvasWidth, canvasHeight);

        GraphicsContext
            gc = canvas.getGraphicsContext2D();

        gc.setStroke(Color.BLACK);

        gc.setLineWidth(1);

        for (int i = 0; i < strip.size() - 1 ; i ++){

            double
                maxHeight = (int) canvas.getHeight() >>> 1,
                sample = (double) strip.get(i),
                nextSample = (double) strip.get(i + 1);

            gc.strokeLine(
                i + 10,
                maxHeight - sample / maxHeight,
                i + 11,
                maxHeight - nextSample / maxHeight
            );
        }




    //  private void drawCoordinateSystem(Canvas canvas, GraphicsContext gc){   //--------------------------------------

        gc.setStroke(Color.LIGHTBLUE);

        int
            logDistance = ((int)canvas.getHeight()) >>> 2,
            trueDistance = logDistance;

        gc.strokeLine(
            10,
            10,
            canvas.getWidth() - 10,
            10);

        gc.strokeLine(
            10,
            canvas.getHeight() - 10,
            canvas.getWidth() - 10,
            canvas.getHeight() - 10);

        while (logDistance > 2 && trueDistance + 10 < ((int)canvas.getHeight() >>> 1)) {

            gc.strokeLine(
                10,
                10 + trueDistance,
                canvas.getWidth() - 10,
                10 + trueDistance);

            gc.strokeLine(
                10,
                canvas.getHeight() - 10 - trueDistance,
                canvas.getWidth() - 10,
                canvas.getHeight() - 10 - trueDistance);

            logDistance >>>= 1;
            trueDistance += logDistance;
        }

            gc.setStroke(Color.DODGERBLUE);

            gc.setLineWidth(1);

            gc.strokeLine(
                canvas.getWidth() - 10,
                ((int)canvas.getHeight()) >>> 1,
                10,
                ((int)canvas.getHeight()) >>> 1);

            gc.strokeLine(
                10,                         // x start
                10,                         // y start
                10,                         // x send
                canvas.getHeight() - 10     // y end
            );
    //    }     //------------------------------------------------------------------------------------------------------

    //  drawCoordinateSystem(canvas, gc);


        Group
            graphGroup = new Group(canvas);

        ((VBox) scene.getRoot()).getChildren().addAll(graphGroup, scroller);

//        ((VBox) scene.getRoot()).getChildren().add(canvas);

    //  ---------------------------------------------------------------------------------------

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

    private void drawCoordinateSystem(Canvas canvas, GraphicsContext gc){

        gc.setStroke(Color.DARKGREY);

        gc.setLineWidth(1);
        gc.strokeLine(
            canvas.getWidth() - 10,
            ((int)canvas.getHeight()) >>> 1,
            10,
            ((int)canvas.getHeight()) >>> 1);

        gc.strokeLine(
            10,                         // x start
            10,                         // y start
            10,                         // x send
            canvas.getHeight() - 10     // y end
        );
    }
}