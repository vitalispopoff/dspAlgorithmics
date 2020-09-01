import data.*;

import gui.MainMenu;
import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.geometry.*;
import javafx.scene.Scene;
import javafx.scene.chart.LineChart;
import javafx.scene.control.*;
import javafx.scene.input.KeyCombination;
import javafx.scene.layout.*;
import javafx.stage.FileChooser;
import javafx.stage.Stage;

public class Main extends Application {

/*    static String
            address_folder_0 = "src\\main\\resources\\",
            address_folder_1 = "C:\\Users\\Voo\\Desktop\\unpeak\\shortie\\",

    address_shortie = "shortie-mono-16bit.wav",

    address_0 = "sample-mono.wav",
            address_1 = "sample-mono-byte.wav",
            address_2 = "sample-mono-byte_unsigned.wav",
            address_3 = "sample-mono-byte.wav",
            address_4 = "sample-mono-float.wav",
            address_5 = "sample-mono-double.wav",

    address_400 = "*.wav",
            address_404 = "nope.wave";*/

    @Override
    public void start(Stage stage){

        MainMenu
            bar = new MainMenu(stage);

        /*        FileChooser
                browser = new FileChooser();

            browser.setTitle("Open File");
            browser.showOpenDialog(stage);*/    // file browser

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