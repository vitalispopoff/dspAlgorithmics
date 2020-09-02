import data.structure.FileContentStructure;
import data.structure.Strip;
import gui.MainMenu;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.ScrollPane;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        MainMenu
            bar = new MainMenu(stage);

        ScrollPane
            grid = new ScrollPane();

        VBox
            vBox = new VBox(bar, grid);

        int
            sceneHeight = 600,
            barHeight = 25,
            scrollerHeight = 16,
            canvasHeight = sceneHeight - barHeight - scrollerHeight,

            sceneWidth = 1200,
            canvasWidth = sceneWidth - 10;

        bar.setMinHeight(barHeight);
        bar.setMaxHeight(barHeight);

        Scene
            scene = new Scene(vBox, sceneWidth, sceneHeight);

        data.Wave
            wave = new data.Wave (new java.io.File("src\\main\\resources\\shortie-mono-16bit.wav"));

        int
            hScale = 10,
            vScale = 4 - wave.getHeader().getField(FileContentStructure.BLOCK_ALIGN);

        Strip
            strip = wave.getSignal().getStrip(0);

        Canvas
            canvas = new Canvas(strip.size() / (double) hScale, canvasHeight);

        GraphicsContext
            gc = canvas.getGraphicsContext2D();

        Path line = setLine(gc, strip, canvas, canvasHeight, hScale, vScale);

/*        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Path
            line = new Path();

        line.getElements().add(new MoveTo(0 , strip.get(0) - canvasHeight >>> 1));

        for (int i = 1; i < strip.size() - 1 ; i += hScale){

            double
                maxHeight = (int) canvas.getHeight() >>> 1,
                sample = (strip.get(i) << vScale),
                nextSample = (double) strip.get(i + 1);

            LineTo
                lineTo = new LineTo((double) i / hScale, maxHeight - (sample / maxHeight));

            line.getElements().add(lineTo);
        }*/

    //  ----------------------------------------------------------------------------------------------------------------

/*        gc.setStroke(Color.LIGHTBLUE);

        int
            logDistance = ((int)canvas.getHeight()) >>> 2,
            trueDistance = logDistance;

        gc.strokeLine(
            0,
            0,
            (double)strip.size() / (double)hScale,
            0);

        gc.strokeLine(
            0,
            canvas.getHeight(),
            canvas.getWidth(),
            canvas.getHeight());

        while (logDistance > 2 && trueDistance < ((int)canvas.getHeight() >>> 1)) {

            gc.strokeLine(
                0,
                trueDistance,
                (double)strip.size() / (double)hScale,
                trueDistance);

            gc.strokeLine(
                0,
                canvas.getHeight() - trueDistance,
                (double) strip.size() / (double)hScale,
                canvas.getHeight() - trueDistance);

            logDistance >>>= 1;
            trueDistance += logDistance;
        }

            gc.setStroke(Color.DODGERBLUE);

            gc.setLineWidth(1);*/

        whatever(gc, canvas, strip, hScale);

    //  ----------------------------------------------------------------------------------------------------------------

        Group
            graphGroup = new Group(canvas, line);

            grid.setContent(graphGroup);

        stage.setScene(scene);

        stage.show();
    }

    Path setLine(GraphicsContext gc, Strip strip, Canvas canvas, int canvasHeight, int hScale, int vScale){

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Path
            line = new Path();

        line.getElements().add(new MoveTo(0 , strip.get(0) - canvasHeight >>> 1));

        for (int i = 1; i < strip.size() - 1 ; i += hScale){

            double
                maxHeight = (int) canvas.getHeight() >>> 1,
                sample = (strip.get(i) << vScale),
                nextSample = (double) strip.get(i + 1);

            LineTo
                lineTo = new LineTo((double) i / hScale, maxHeight - (sample / maxHeight));

            line.getElements().add(lineTo);
        }

        return line;
    }


    void whatever(GraphicsContext gc, Canvas canvas, Strip strip, int hScale){
        gc.setStroke(Color.LIGHTBLUE);

        int
            logDistance = ((int)canvas.getHeight()) >>> 2,
            trueDistance = logDistance;

        gc.strokeLine(
            0,
            0,
            (double)strip.size() / (double)hScale,
            0);

        gc.strokeLine(
            0,
            canvas.getHeight(),
            canvas.getWidth(),
            canvas.getHeight());

        while (logDistance > 2 && trueDistance < ((int)canvas.getHeight() >>> 1)) {

            gc.strokeLine(
                0,
                trueDistance,
                (double)strip.size() / (double)hScale,
                trueDistance);

            gc.strokeLine(
                0,
                canvas.getHeight() - trueDistance,
                (double) strip.size() / (double)hScale,
                canvas.getHeight() - trueDistance);

            logDistance >>>= 1;
            trueDistance += logDistance;
        }

        gc.setStroke(Color.DODGERBLUE);

        gc.setLineWidth(1);
    }

/*    private void drawCoordinateSystem(Canvas canvas, GraphicsContext gc){

        gc.setStroke(Color.DARKGREY);

        gc.setLineWidth(1);
        gc.strokeLine(
            canvas.getWidth(),
            ((int)canvas.getHeight()) >>> 1,
            10,
            ((int)canvas.getHeight()) >>> 1);

        gc.strokeLine(
            0,
            10,
            10,
            canvas.getHeight() - 10
        );
    }*/

    public static void main(String[] args) {

        launch(args);
    }


}