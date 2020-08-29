import algorithms.metaProcessors.FileManager;
import data.*;
import javafx.application.Application;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.layout.StackPane;
import javafx.stage.Stage;

public class Main extends Application {

    @Override
    public void start(Stage primaryStage) /*throws Exception*/ {

        String
                title = "Hello World";

        Button
                button = new Button();

        button.setText(title);
        button.setOnAction(event -> System.out.println(title));

        StackPane
                root = new StackPane();

        root.getChildren().add(button);

        int
                width = 300,
                height = 275;

        Scene
                scene = new Scene(root, width, height);

        primaryStage.setTitle(title);
        primaryStage.setScene(scene);
        primaryStage.show();

    }

    public static void main(String[] args) {

        launch(args);

        Wave
                temporal,
                temporal1;

        String
                address;

        {
            String
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
                    address_404 = "nope.wave";

            address = address_folder_0 + address_0;

            temporal = new Wave(address);

        }    // * load waveFile

        FileManager.saveFile(temporal);


    }
}