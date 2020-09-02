import data.structure.FileContentStructure;
import gui.MainMenu;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.layout.*;
import javafx.scene.paint.Color;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        MainMenu
            bar = new MainMenu(stage);

        int
            barHeight = 25;

        bar.setMinHeight(barHeight);
        bar.setMaxHeight(barHeight);

        VBox
            vBox = new VBox(bar);

        Scene
            scene = new Scene(vBox, 800, 600);

    //  ---------------------------------------------------------------------------------------

        /*{
            javafx.scene.shape.Path
                thePath = new Path();

            MoveTo
                moveTo = new MoveTo(108, 71);

            LineTo
                line1 = new LineTo(321, 161),
                line2 = new LineTo(126, 232),
                line3 = new LineTo(232, 52),
                line4 = new LineTo(269, 250),
                line5 = new LineTo(108, 71);

            thePath.getElements().add(moveTo);
            thePath.getElements().addAll(line1, line2, line3, line4, line5);

            Group
                group = new Group(thePath);

            ((VBox) scene.getRoot()).getChildren().addAll(group);

        }*/   // drawing
        /*        Button
            button = new Button("Close stage");

        button.setOnAction(event -> {

            System.out.println(stage.getWidth());
            System.out.println(stage.getHeight());
        });

        ((VBox) scene.getRoot()).getChildren().add(button);*/   // button

    //  ---------------------------------------------------------------------------------------


        data.Wave
            wave = new data.Wave (new java.io.File("src\\main\\resources\\shortie-mono-16bit.wav"));

        int
            signalLength = wave.getSignal().getStrip(0).size(),
            windowLength = (int) scene.getWidth(),
            signalAmp = 1 << wave.getHeader().getField(FileContentStructure.BITS_PER_SAMPLE),
            windowHeight = (int) scene.getHeight(),
            averagingScope = signalLength / windowLength,
            averagingJumps = signalLength / averagingScope,
            averageAmp = windowHeight / signalAmp;

        java.util.ArrayList<Integer>
            averagedSignal = new java.util.ArrayList<>();

        for (int i = 0 ; i < averagingJumps ; i ++){

            int
                localAverage = 0;

            for (int j = i * averagingScope; j < (i + 1) *  averagingScope ; j ++){

                localAverage += Math.abs(wave.getSignal().getStrip(0).get(j));
            }
            averagedSignal.add((localAverage / averagingScope)* averageAmp);
        }

    //  ---------------------------------------------------------------------------------------

        Group
            graphGroup = new Group();

        Canvas
            canvas = new Canvas(scene.getWidth() - (barHeight << 1), scene.getHeight() - (barHeight << 1));

        GraphicsContext
            gc = canvas.getGraphicsContext2D();

        drawShapes(canvas, gc);

        ((VBox) scene.getRoot()).getChildren().add(canvas);

        /*{
            javafx.scene.chart.NumberAxis
                xAxis = new javafx.scene.chart.NumberAxis(),
                yAxis = new javafx.scene.chart.NumberAxis();

            javafx.scene.chart.AreaChart<Number, Number>
                chart = new javafx.scene.chart.AreaChart<>(xAxis, yAxis);

            javafx.scene.chart.XYChart.Series
                positiveSeries = new XYChart.Series(),
                negativeSeries = new XYChart.Series();

            for (int i = 0; i < averagedSignal.size(); i++) {

                positiveSeries.getData().add(new XYChart.Data<>(i, averagedSignal.get(i)));
                negativeSeries.getData().add(new XYChart.Data<>(i, -1 * averagedSignal.get(i)));
            }

            chart.getData().add(positiveSeries);
            chart.getData().add(negativeSeries);

            ((VBox) scene.getRoot()).getChildren().addAll(chart);
        }*/     // lineChart

        { }
    //  ---------------------------------------------------------------------------------------

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }

    private void drawShapes(Canvas canvas, GraphicsContext gc){

        gc.setStroke(Color.DARKGREY);

        gc.setLineWidth(1);
        gc.strokeLine(
            canvas.getWidth() - 10,
            ((int)canvas.getHeight()) >>> 1,
            10,
            ((int)canvas.getHeight()) >>> 1)
        ;

        gc.strokeLine(
            10,                         // x start
            10,                         // y start
            10,                         // x send
            canvas.getHeight() -35      // y end
        );
    }
}