import data.structure.FileContentStructure;
import data.structure.Strip;
import gui.MainMenu;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.*;
import javafx.scene.layout.*;
import javafx.scene.paint.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        int
            sceneHeight = 800,
            barHeight = 25,
            scrollerHeight = 16,
            canvasHeight = sceneHeight - barHeight /*- scrollerHeight*/,

            sceneWidth = 1200;

        MainMenu
            bar = new MainMenu(stage);

        ScrollPane
            scrollPane = new ScrollPane();

        Canvas
            canvas = new Canvas(/*strip.size() / hScale*/ sceneWidth, canvasHeight);

        VBox
            vBox = new VBox(bar, scrollPane/*, canvas*/);

        bar.setMinHeight(barHeight);
        bar.setMaxHeight(barHeight);

        Scene
            scene = new Scene(vBox, sceneWidth, sceneHeight);

        data.Wave
            wave = new data.Wave (new java.io.File("H:\\_LIBS\\User Lib\\Samples\\Full Loops\\Pistons(120).wav"));

        double
            hScale = 9.,
            vScale = 4. - wave.getHeader().getField(FileContentStructure.BLOCK_ALIGN);

        Strip
            strip = wave.getSignal().getStrip(0);



        GraphicsContext
            gc = canvas.getGraphicsContext2D();

        Path
            line = drawLine(gc, strip, canvas, canvasHeight, hScale, vScale);

        drawVolumeLines(gc, canvas, strip, hScale);

        drawCoordinateSystem(canvas, gc);

        Pane pane = new Pane();

        Canvas
            dummy = new Canvas(sceneWidth, canvasHeight);

        pane.getChildren().add(canvas);
        pane.getChildren().add(line);

        scrollPane.setContent(pane);

        stage.setScene(scene);
        stage.show();
    }

    Path drawLine(GraphicsContext gc, Strip strip, Canvas canvas, int canvasHeight, double hScale, double vScale){

        gc.setStroke(Color.BLACK);
        gc.setLineWidth(1);

        Path
            line = new Path();

        line.getElements().add(new MoveTo(0. , canvasHeight / 2.));

        for (int i = 1; i < strip.size() - hScale ; i += hScale){

            double
                maxHeight = canvas.getHeight() / 2.,
                sample = strip.get(i) /* * 2.*/,
                nextSample = (double) strip.get(i);

            LineTo
                lineTo = new LineTo((double) i / hScale,  maxHeight - (sample / maxHeight));

            line.getElements().add(lineTo);
        }
        return line;
    }

    void drawVolumeLines(GraphicsContext gc, Canvas canvas, Strip strip, double hScale){
        gc.setStroke(Color.LIGHTBLUE);

        int
            logDistance = ((int)canvas.getHeight()) >>> 2,
            trueDistance = logDistance;

        gc.strokeLine(
            0,
            0,
            (double)strip.size() / hScale,
            0);

        gc.strokeLine(
            0,
            canvas.getHeight(),
            canvas.getWidth(),
            canvas.getHeight());

        while (logDistance > 2 && trueDistance < canvas.getHeight() / 2.) {

            gc.strokeLine(
                0,
                trueDistance,
                (double)strip.size() / hScale,
                trueDistance);

            gc.strokeLine(
                0,
                canvas.getHeight() - trueDistance,
                (double) strip.size() / hScale,
                canvas.getHeight() - trueDistance);

            logDistance >>>= 1;
            trueDistance += logDistance;
        }

        gc.setStroke(Color.DODGERBLUE);

        gc.setLineWidth(1);
    }

    private void drawCoordinateSystem(Canvas canvas, GraphicsContext gc){

        gc.setStroke(Color.DARKGREY);
        gc.setLineWidth(1);

        gc.strokeLine(
            0,
            canvas.getHeight() / 2.,
            canvas.getWidth(),
            canvas.getHeight() / 2.
        );

        gc.strokeLine(
            0,
            0,
            0,
            canvas.getHeight()
        );
    }

    public static void main(String[] args) {

        launch(args);
    }
}