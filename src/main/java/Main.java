import gui.Grid;
import gui.MainMenu;

import javafx.application.Application;
import javafx.geometry.Rectangle2D;
import javafx.scene.*;
import javafx.scene.layout.*;
import javafx.stage.*;

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

    //  ---------------------------------------------------------------------------------------

        stage.show();

//        stage.close();
    }

    public static void main(String[] args) {

        launch(args);
    }
}