package app;

import data.FileCache;
import data.WaveFile;
import data.structure.Strip;
import gui.MainMenu;
import javafx.application.Application;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.stage.*;

import java.io.File;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

	@Override
	public void start(Stage stage) {

		stage.setMinWidth(320);
		stage.setMinHeight(240);

		BorderPane
			b = new BorderPane();

		Canvas
			canvas = new Canvas();

		Scene
			scene = new Scene(b, 320, 240);

//        scene.setFill(DARKGRAY);

		double
			s = 5;

		Rectangle
			rT = new Rectangle(0, 0, s, s),
			rL = new Rectangle(0, 0, s, s),
			rR = new Rectangle(0, 0, s, s),
			rB = new Rectangle(0, 0, s, s);

		rT.setOpacity(0);
		rL.setOpacity(0);
		rR.setOpacity(0);
		rB.setOpacity(0);
//        rT.setFill(BLACK);
//        rL.setFill(BLACK);
//        rR.setFill(BLACK);
//        rB.setFill(BLACK);

		VBox
			gT = new VBox(new MainMenu(stage), rT),
			gL = new VBox(rL),
			gR = new VBox(rR),
			gB = new VBox(rB);

		b.setTop(gT);
		b.setLeft(gL);
		b.setRight(gR);
		b.setBottom(gB);

		b.setCenter(canvas);

		FileCache.currentIndexDueProperty().addListener(
			(((observable, oldValue, newValue) -> {

//				if ((int) newValue >= 0 )



				redraw(canvas, (int) newValue >= 0);
			}))
		);

		stage.widthProperty().addListener(
			((observable, oldValue, newValue) -> {

				double
					d = (double) newValue - (2 * s) - 16;

				if (canvas.getWidth() != d) {

					canvas.setWidth(d);

					redraw(canvas, FileCache.getCurrentIndex() >= 0);
				}
			})
		);

		stage.heightProperty().addListener(
			(((observable, oldValue, newValue) -> {

				double
					d = (double) newValue - (2 * s) - 25 - 32 - 7;

				if (b.getHeight() != d) {

					canvas.setHeight(d);

					redraw(canvas, FileCache.getCurrentIndex() >= 0);
				}
			}))
		);

		//  ---------------------------------------------------------------------------------------

		stage.setScene(scene);
		stage.show();

//        stage.close();
	}

	void drawVerticalGrid(Canvas canvas) {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		clean(canvas);
		context.setLineWidth(1);

		double
			verticalScale = 1., // TODO to be taken from mouseScroll
			height = canvas.getHeight(),
			width = canvas.getWidth(),
			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;

		context.setStroke(DODGERBLUE);
		context.strokeLine(0, height / 2, width, height / 2);

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution));

		context.setStroke(LIGHTBLUE);

		for (int i = 1; i <= numberOfLines; i++) {

			double
				y1 = (height / 2) - scaledHeight / (1 << i),
				y2 = (height / 2) + scaledHeight / (1 << i);

			if (y1 >= 0) context.strokeLine(0, y1, width, y1);

			if (y2 <= height && y1 != y2) context.strokeLine(0, y2, width, y2);
		}
	}

	void redraw(Canvas canvas, boolean waveIsLoaded) {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		if (waveIsLoaded) drawVerticalGrid(canvas);
	}

	void clean(Canvas canvas) {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
	}

	public static void main(String[] args) {

		launch(args);
	}
}