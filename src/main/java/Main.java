import data.*;

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

        MenuBar
            bar = new MenuBar();

        Menu
            menuFile = new Menu("File"),
            menuEdit = new Menu("Edit"),
            menuView = new Menu("View"),
            menuAbout = new Menu("About");



        MenuItem
            openFile = new MenuItem("Open"),
            closeFile = new MenuItem("Close"),
            saveAs = new MenuItem("Save as"),
            closeApp = new MenuItem("Close app");

        openFile.setAccelerator(KeyCombination.keyCombination("Ctrl+O"));
        closeFile.setAccelerator(KeyCombination.keyCombination("Ctrl+W"));
        saveAs.setAccelerator(KeyCombination.keyCombination("Ctrl+Shift+S"));
        closeApp.setAccelerator(KeyCombination.keyCombination("Ctrl+Q"));

        closeApp.setOnAction((ActionEvent t ) -> {

            System.exit(0);
        });

        menuFile.getItems().addAll(
            openFile,
            closeFile,
            new SeparatorMenuItem(),
            saveAs,
            new SeparatorMenuItem(),
            closeApp
        );

        bar.getMenus().addAll(menuFile, menuEdit, menuView, menuAbout);

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