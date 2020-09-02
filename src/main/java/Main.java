import data.structure.FileContentStructure;
import gui.MainMenu;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.chart.XYChart;
import javafx.scene.layout.*;
import javafx.stage.Stage;

import java.util.Arrays;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        MainMenu
            bar = new MainMenu(stage);

        Scene
            scene = new Scene(new VBox(), 800, 600);

        ((VBox)scene.getRoot()).getChildren().addAll(bar);

    //  -------------------------------------------------------------

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

        System.out.println(wave.getHeader().toString());


        java.util.ArrayList<Integer>
            averagedSignal = new java.util.ArrayList<>();

        for (int i = 0 ; i < averagingJumps ; i ++){

            Integer
                localAverage = 0;

            for (int j = i * averagingScope; j < (i + 1) *  averagingScope ; j ++){

                localAverage += Math.abs(wave.getSignal().getStrip(0).get(j));
            }
            averagedSignal.add(localAverage / averagingScope);
        }

        for (int sample : averagedSignal) sample *= (averageAmp);


        javafx.scene.chart.NumberAxis
            xAxis = new javafx.scene.chart.NumberAxis(),
            yAxis = new javafx.scene.chart.NumberAxis();

        javafx.scene.chart.AreaChart<Number, Number>
            chart = new javafx.scene.chart.AreaChart<>(xAxis, yAxis);

        javafx.scene.chart.XYChart.Series
            positiveSeries = new XYChart.Series(),
            negativeSeries = new XYChart.Series();

        for(int i = 0; i < averagedSignal.size(); i++) {

            positiveSeries.getData().add(new XYChart.Data<>(i, averagedSignal.get(i)));
            negativeSeries.getData().add(new XYChart.Data<>(i, -1 * averagedSignal.get(i)));
        }

        chart.getData().add(positiveSeries);
        chart.getData().add(negativeSeries);

        ((VBox) scene.getRoot()).getChildren().addAll(chart);








        System.out.println(
            "\n\tsignalLength = " + signalLength
            + "\n\twindowLength = " + windowLength
            + "\n\tsignalAmp = " + signalAmp
            + "\n\twindowHeight = " + windowHeight);



    //  -------------------------------------------------------------

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}