import gui.MainMenu;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.layout.*;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        MainMenu
            bar = new MainMenu(stage);

        Scene
            scene = new Scene(new VBox(), 800, 600);

        ((VBox)scene.getRoot()).getChildren().addAll(bar);

        stage.setScene(scene);

        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}