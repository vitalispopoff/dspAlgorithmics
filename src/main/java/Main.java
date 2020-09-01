import gui.MainMenu;
import javafx.application.Application;
import javafx.event.EventHandler;
import javafx.scene.*;
import javafx.scene.control.Button;
import javafx.scene.layout.*;
import javafx.scene.shape.*;
import javafx.stage.Stage;

import java.awt.event.ActionEvent;
import java.awt.event.MouseEvent;

public class Main extends Application {

    @Override
    public void start(Stage stage){

        MainMenu
            bar = new MainMenu(stage);

        Scene
            scene = new Scene(new VBox(), 800, 600);

        ((VBox)scene.getRoot()).getChildren().addAll(bar);

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


        Button
            button = new Button("Close stage");

        button.setOnAction(event -> stage.close());

        ((VBox) scene.getRoot()).getChildren().add(button);

        stage.setScene(scene);



        stage.show();
    }

    public static void main(String[] args) {

        launch(args);
    }
}