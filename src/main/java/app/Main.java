package app;

import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

    @Override
    public void start(Stage stage){

/*
        stage.setMinWidth(320);
        stage.setMinHeight(240);
*/

        BorderPane
            b = new BorderPane();

        Canvas
            canvas = new Canvas();

        Scene
            scene = new Scene(new Group(canvas), 320, 240);

        double
            s = 20;

        Rectangle
            rT = new Rectangle(0, 0, s, s),
            rL = new Rectangle(0, 0, s, s),
            rR = new Rectangle(0, 0, s, s),
            rB = new Rectangle(0, 0, s, s);

        rT.setOpacity(1);
        rL.setOpacity(0);
        rR.setOpacity(1);
        rB.setOpacity(1);
        rT.setFill(BLACK);
        rL.setFill(BLACK);
        rR.setFill(BLACK);
        rB.setFill(BLACK);

        VBox
            gT = new VBox(/*new MainMenu(stage),*/ rT),
            gL = new VBox (rL),
            gR = new VBox (rR),
            gB = new VBox (rB);

        b.setTop(gT);
        b.setLeft(gL);
        b.setRight(gR);
        b.setBottom(gB);



//        b.setCenter(canvas);


        stage.widthProperty().addListener(
            ( (observable, oldValue, newValue) -> {

                double
                    d = (double) newValue /*- (2 * s)*/ - 15 - 2;

                if (canvas.getWidth() != d) {

                    canvas.setWidth(d);
                    redraw(canvas);
                }
            })
        );

        stage.heightProperty().addListener(
            ( ((observable, oldValue, newValue) -> {

                double
                    d = (double) newValue /*- (2 * s)*/ /*- 25*/ - 31 - 7 - 2;

                if (b.getHeight() != d) {

                    canvas.setHeight(d);
                    redraw(canvas);
                }
            }))
        );

    //  ---------------------------------------------------------------------------------------

        stage.setScene(scene);
        stage.show();

//        stage.close();
    }

    void redraw(Canvas canvas){

        GraphicsContext
            context= canvas.getGraphicsContext2D();

        context.clearRect(0 , 0 ,canvas.getWidth(), canvas.getHeight());

        context.setFill(BLACK);
        context.fillRect(0, 0, canvas.getWidth(), canvas.getHeight());

        double
            x = canvas.getWidth(),
            y = canvas.getHeight() / 2 ;

//        context.strokeLine(0, y, x, y);

        context.setStroke(BLUE);
        context.setLineWidth(1);


    }

    public static void main(String[] args) {

        launch(args);
    }
}