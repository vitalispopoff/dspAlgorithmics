package app;

import data.FileCache;
import data.WaveFile;
import data.structure.FileContentStructure;
import data.structure.Strip;
import data.structure.WaveHeader;
import gui.MainMenu;
import javafx.application.Application;
import javafx.geometry.Orientation;
import javafx.scene.*;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.ScrollBar;
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

		double
			s = 5;

	//	borderPane ----------------------------------------------------------------------------

		ScrollBar
			scroller = new ScrollBar();

		scroller.setOrientation(Orientation.HORIZONTAL);



		Rectangle
			rT = new Rectangle(0, 0, s, s),
			rL = new Rectangle(0, 0, s, s),
			rR = new Rectangle(0, 0, s, s),
			rB = new Rectangle(0, 0, s, s);

		rT.setOpacity(0);
		rL.setOpacity(0);
		rR.setOpacity(0);
		rB.setOpacity(1);
//        rT.setFill(BLACK);
//        rL.setFill(BLACK);
//        rR.setFill(BLACK);
        rB.setFill(BLACK);


		VBox
			gT = new VBox(new MainMenu(stage), rT),
			gL = new VBox(rL),
			gR = new VBox(rR),
			gB = new VBox(rB, scroller);

		gB.setPrefHeight(20);

		b.setTop(gT);
		b.setLeft(gL);
		b.setRight(gR);
		b.setBottom(gB);

		b.setCenter(canvas);

	//	---------------------------------------------------------------------------------------

		scroller.valueProperty().addListener(
			((observable, oldValue, newValue) -> redraw(/*true,*/ canvas, scroller, null)));

		FileCache.currentIndexDueProperty().addListener(
			(observable, oldValue, newValue) -> {

				int
				waveLength = FileCache.loadCurrent().getSignal().getStrip(0).size();

				System.out.println(waveLength);



				scroller.setMin(0.);
				scroller.setMax(
					scene.getWidth() > waveLength
					? 0.
					: (waveLength - scene.getWidth())
				);

				redraw(/*(int) newValue >= 0,*/ canvas, scroller, null);

			});

		stage.widthProperty().addListener(
			(observable, oldValue, newValue) -> {

				double
					d = (double) newValue - (2 * s) - 16;

				if (canvas.getWidth() != d) {

					canvas.setWidth(d);

					redraw(/*FileCache.getCurrentIndex() >= 0,*/ canvas, scroller, null);
				}
			}
		);

		stage.heightProperty().addListener(
			(((observable, oldValue, newValue) -> {

				double
					d = (double) newValue
							- (2 * s)	// gap rectangles
							- 25		// main menu
							- 32		// window system header bar ?
//							- 7			// not tracked yet needed, or is it?
							- 20		// bottom border
					;

				if (b.getHeight() != d) {

					canvas.setHeight(d);

					redraw(/*FileCache.getCurrentIndex() >= 0,*/ canvas, scroller, null);
				}
			}))
		);

	//  ---------------------------------------------------------------------------------------

		stage.setScene(scene);
		stage.show();

		//?	temporal  //---------------------------------------------------------------------------

		{
			String[] a = {
				"src\\main\\resources\\shortie-mono-16bit.wav",
				"src\\main\\resources\\sample-mono.wav"
			};

			int x = 0;

//			WaveFile file = new WaveFile(new File(a[x]));
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	void drawEverything(Canvas canvas, ScrollBar horizontal, ScrollBar vertical) {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		clean(canvas);
		context.setLineWidth(1);

		double
			verticalScale = 1., // TODO to be taken from mouseScroll
			horizontalScale = 1.,	// TODO as above
			height = canvas.getHeight(),
			width = canvas.getWidth(),
			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;

	//	horizontals  --------------------------------------------------------------------------

		context.setStroke(BLUE);
		context.strokeLine(0, height / 2, width, height / 2);

		int
			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution));

		context.setStroke(DODGERBLUE);

		for (int i = 1; i <= numberOfLines; i++) {

			double
				y1 = (height / 2) - scaledHeight / (1 << i),
				y2 = (height / 2) + scaledHeight / (1 << i);

			if (y1 >= 0) context.strokeLine(0, y1, width, y1);

			if (y2 <= height && y1 != y2) context.strokeLine(0, y2, width, y2);
		}

	//	---------------------------------------------------------------------------------------

		Strip
			strip = FileCache.loadCurrent().getSignal().getStrip(0);

		int
			bitsPerSample = FileCache.loadCurrent().getHeader().getField(FileContentStructure.BITS_PER_SAMPLE) - 1;

	//	verticals  ----------------------------------------------------------------------------

		context.setStroke(DODGERBLUE);

		context.strokeLine(0, 0, 0, height);
		context.strokeLine(width, 0, width, height);


	//	waveForm  -----------------------------------------------------------------------------

		context.setStroke(RED);

		int movement = (int) horizontal.getValue();

		for (int i = 1 ; i < Math.min(width - 1, strip.size() - width - 1); i++){

			double
				prevSample = (double) strip.get(i + movement - 1),
				sample = (double) strip.get(i + movement),

				verticalCenter = height / 2,

				prevSampleAmplitude = prevSample / (double) ( 1 << bitsPerSample),
				prevDcOffset = verticalCenter * (1 - prevSampleAmplitude),

				sampleAmplitude = sample / (double) (1 << bitsPerSample),
				dcOffset = verticalCenter * (1 - sampleAmplitude);


			context.strokeLine(i, prevDcOffset, i + 1, dcOffset);

		}
	}

	void redraw(/*boolean waveIsLoaded,*/ Canvas canvas, ScrollBar horizontal, ScrollBar vertical) {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

		if (FileCache.fileIsLoaded()) drawEverything(canvas, horizontal, vertical);
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