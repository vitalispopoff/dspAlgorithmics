import data.WaveFile;
import data.structure.Strip;
import gui.Grid;
import gui.MainMenu;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        stage.setMinHeight(240);
        stage.setMinWidth(320);

        Rectangle2D
            screenBounds = Screen.getPrimary().getBounds();

        MainMenu
            bar = new MainMenu(stage);

        BorderPane
            root = new BorderPane();

        root.setTop(bar);

        Scene
            scene = new Scene(root, 640, 480);

        stage.setScene(scene);

        Grid
            grid = new Grid(root);

    //  ? temporal  //-------------------------------------------------------------------------

        WaveFile
            waveFile = new WaveFile(new java.io.File("src\\main\\resources\\shortie-mono-16bit.wav"));

        Strip
            strip = waveFile.getSignal().getStrip(0);

        grid.gc.setStroke(BLUE);
        grid.gc.setLineWidth(1);

        for(int i = 0; i < strip.size() - 1; i++){

            double
                maxHeight = (int) grid.gc.getCanvas().getHeight() >>> 1,
                sample = (double) strip.get(i),
                nextSample = (double) strip.get(i + 1);

            grid.gc.strokeLine(
                i + 10,
                maxHeight - sample / maxHeight,
                i + 11,
                maxHeight - nextSample / maxHeight
            );
        }

    //  ---------------------------------------------------------------------------------------

        stage.show();

//        stage.close();
    }

    public static void main(String[] args) {

        launch(args);
    }
}