package app;

import data.FileCache;
import data.structure.*;
import gui.Menus.MainMenuController;
import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.geometry.HPos;
import javafx.geometry.Orientation;
import javafx.geometry.VPos;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.Control;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.*;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.io.*;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

	static boolean
		canvasArePainted = false;

	@Override
	public void start(Stage stage) {

		MainMenuController.setStage(stage);

		double
			stageW = 640,
			stageH = 480;

		stage.setMinWidth(stageW);
		stage.setMinHeight(stageH);

		/*BorderPane b = new BorderPane();*/	// * old

	//	* GridPane ----------------------------------------------------------------------------

		// * new
		GridPane
			p = new GridPane();

		p.setGridLinesVisible(true);


		double
			col0W = 5,
			col2W = 25,
			col1W = stageW - col0W - col2W,

			row0H = 30,
			row2H = 25,
			row1H = stageH - row0H - row2H,

			mainMenuHeight = 30.,
			gridSize = 25.;

		ColumnConstraints
			col0 = new ColumnConstraints(5, 5, 5),
			col1 = new ColumnConstraints(gridSize, gridSize, gridSize),
			col2 = new ColumnConstraints(gridSize, gridSize, gridSize);

		RowConstraints
			row0 = new RowConstraints(mainMenuHeight, mainMenuHeight, mainMenuHeight),
			row1 = new RowConstraints(0,0,0),
			row2 = new RowConstraints();

		p.getColumnConstraints().add(col0);
		p.getColumnConstraints().add(col1);
		p.getColumnConstraints().add(col2);

		p.getRowConstraints().add(row0);
		p.getRowConstraints().add(row2);
		p.getRowConstraints().add(row1);

		ScrollBar
			hScroll = new ScrollBar(),
			vScroll = new ScrollBar();

		hScroll.setVisible(false);
		vScroll.setVisible(false);

		hScroll.setOrientation(Orientation.HORIZONTAL);
		vScroll.setOrientation(Orientation.VERTICAL);

//		StackPane horizontalScrolls = new StackPane(horizontalScroll);

		p.add(vScroll, 2, 1);
		p.add(hScroll, 1, 2);

		GridPane.setValignment(hScroll, VPos.CENTER);
		GridPane.setHalignment(hScroll, HPos.CENTER);
		GridPane.setValignment(vScroll, VPos.CENTER);
		GridPane.setHalignment(vScroll, HPos.CENTER);


		Rectangle
			rect = new Rectangle(0, 0, 0, 0);
		rect.setOpacity(0.7);
		rect.setFill(GREEN);
		p.add(rect, 1, 1);

		GridPane.setHalignment(rect, HPos.LEFT);
		GridPane.setValignment(rect, VPos.TOP);

	//	* Canvas & Scene ----------------------------------------------------------------------

		Canvas
			canvas = new Canvas();

		canvas.setHeight(0);
		canvas.setWidth(0);

		Scene
			/*scene = new Scene(b, 320, 240);	*/	// * old
			scene = new Scene(p, 320, 240);	// * new

		double
			s = 5;

	//	* borderPane --------------------------------------------------------------------------

		// * temporal

		String
			location = "E:\\_LAB\\pl\\popoff\\dspAlgorithmics\\src\\main\\resources\\FXMLMainMenu.fxml";

		try {

			URL
				url = new File(location).toURI().toURL();

			FXMLLoader
				loader = new FXMLLoader(url);

			Node
				bar = loader.load();

			/*gT.getChildren().addAll(loader.load(), rT);*/	// * old
//			p.add(bar, 0, 0, 3, 1);	// * new
//			GridPane.setHalignment(bar, HPos.LEFT);
//			GridPane.setValignment(bar, VPos.TOP);


		}
		catch (IOException e){ e.printStackTrace();}


/*
		{
			Rectangle
				rT = new Rectangle(0, 0, s, s),
				rL = new Rectangle(0, 0, s, s),
				rR = new Rectangle(0, 0, s, s),
				rB = new Rectangle(0, 0, s, s);

			rT.setOpacity(0);
			rL.setOpacity(0);
			rR.setOpacity(0);
			rB.setOpacity(0);

        rT.setFill(BLACK);
        rL.setFill(BLACK);
        rR.setFill(BLACK);
        rB.setFill(BLACK);

			VBox
				gT = new VBox(),
				gL = new VBox(rL),
				gR = new VBox(rR, verticalScroll),
				gB = new VBox(rB, horizontalScroll);
			gB.setPrefHeight(20);

			b.setTop(gT);
			b.setLeft(gL);
			b.setRight(gR);
			b.setBottom(gB);

			b.setCenter(canvas);
		}
*/	// * old - disposable ?

		/*p.add(canvas, 0, 1, 2, 1);*/	// * new


	//*	listeners	---------------------------------------------------------------------------

		AtomicReference<Double>
			canvasWidth = new AtomicReference<>(0.),
			canvasHeight = new AtomicReference<>(0.),
			scrollHorizontalMax = new AtomicReference<>(0.),
			scrollVerticalMax = new AtomicReference<>(0.);

		AtomicReference<Boolean>
			cacheIsLoaded = new AtomicReference<>(false);

		AtomicReference<Integer>
			currentIndex = new AtomicReference<>(0),
			waveLength = new AtomicReference<>(0);



		stage.widthProperty()
			.addListener((observable, oldValue, newValue) -> {

				double
					temp = (double) newValue

					;

				col1.setMinWidth(temp);
				col1.setMaxWidth(temp);

				rect.setWidth(temp);

			});

		stage.heightProperty()
			.addListener((observable, oldValue, newValue) -> {

				double
					temp = (double) newValue
							   - 30	// row 0 height
							   - 25	// row 2 height
				;

				row1.setMinHeight(temp);
				row1.setMaxHeight(temp);

				rect.setHeight(temp);


			});



		stage.widthProperty()
			.addListener((observable, oldValue, newValue) -> {

				double
					d = (double) newValue
							/*- (2 * s)*/ 	// * old - gap rectangles
							- 16		// ?
							- 2 * 20	// 0th and 2nd column
					;

				if (canvas.getWidth() != d) canvas.setWidth(d);

				canvasWidth.set(d + 20);
				scrollHorizontalMax.set(waveLength.get().doubleValue() - canvasWidth.get());
				hScroll.setMax(scrollHorizontalMax.get());
				hScroll.setMinWidth(canvasWidth.get() + 16);
				hScroll.setMaxWidth(canvasWidth.get() + 16);
				redraw(canvas, hScroll/*, null*/);

				col1.setPrefWidth(d);
				hScroll.setMaxWidth(d - 2);
				hScroll.setMinWidth(d - 2);
			});

		stage.heightProperty()
			.addListener((observable, oldValue, newValue) -> {

				double
					d = (double) newValue
							/*- (2 * s)*/	// * old -  gap rectangles
							- 25		// main menu
							- 32		// window system header bar ?
//							- 7			// ?
//							- 2			// ?
							- 20		// horizontal Scroll row

					;

//				if (b.getHeight() != d) canvas.setHeight(d);
				if (row2.getPrefHeight() != d) canvas.setHeight(d);

				canvasHeight.set( d);
				scrollVerticalMax.set( d);	// TODO to be adjusted with "wave max peak"
				vScroll.setMinHeight(canvasHeight.get() - 12);
				vScroll.setMaxHeight(canvasHeight.get() - 12);
				redraw(canvas, hScroll/*, null*/);

				row2.setPrefHeight(d);
				vScroll.setMaxHeight(d );
				vScroll.setMinHeight(d );
			});

		FileCache.currentIndexDueProperty()
			.addListener((observable, oldValue, newValue) -> {

				currentIndex.set((int) newValue);
				waveLength.set(FileCache.loadCurrent().getSignal().getStrip(0).size());
				scrollHorizontalMax.set(waveLength.get().doubleValue() - canvasWidth.get());
				hScroll.setMax(scrollHorizontalMax.get());

				if( ! cacheIsLoaded.get()) clean(canvas);

				else redraw(canvas, hScroll/*, null*/);
			});

		MainMenuController.cacheIsEmptyStaticProperty()
			.addListener((observable, oldValue, newValue) -> {

				hScroll.setVisible( ! newValue);
				vScroll.setVisible( ! newValue);

				cacheIsLoaded.set(!MainMenuController.cacheIsEmpty());

				if(newValue) clean(canvas);

				else redraw(canvas, hScroll/*, null*/);
			});

		hScroll.valueProperty()
			.addListener((observable, oldValue, newValue) -> redraw(canvas, hScroll/*, null*/));

	//  ---------------------------------------------------------------------------------------

		stage.setScene(scene);
		stage.show();
	}

//	--------------------------------------------------------------------------------------------------------------------

	void drawEverything(Canvas canvas, ScrollBar horizontal/*, ScrollBar vertical*/) {

		if ( ! canvasArePainted) {

			GraphicsContext
				context = canvas.getGraphicsContext2D();

			clean(canvas);
			context.setLineWidth(1);

			double
				verticalScale = 1., // TODO to be taken from mouseScroll
				horizontalScale = 1.,    // TODO as above

				height = canvas.getHeight(),
				width = canvas.getWidth(),

				scaledHeight = height * verticalScale,
				adjustToVerticalCenter = (scaledHeight / 2.),
				accountForMinResolution = adjustToVerticalCenter / 4;

			//*	horizontals  --------------------------------------------------------------------------

			context.setStroke(BLUE);
			context.strokeLine(0, height / 2, width, height / 2);

			int
				numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)),
				leftMargin = 20;

			context.setFont(new Font("Arial", 10));

			for (int i = 1; i <= numberOfLines; i++) {

				context.setStroke(DODGERBLUE);

				double
					y1 = (height / 2) - scaledHeight / (1 << i),
					y2 = (height / 2) + scaledHeight / (1 << i);

				int
					txt = (-(i - 1) * 6);

				if (y1 >= 0) {

					if (i > 1 && i < numberOfLines) {

						context.setStroke(BLUE);
						context.strokeText(Integer.toString(txt), 1, y1  + 4);

						context.setStroke(DODGERBLUE);
						context.strokeLine(leftMargin * 0.85, y1, width, y1);
					}

					else context.strokeLine(leftMargin, y1, width, y1);
				}

				if (y2 <= height && y1 != y2) {

					if (i > 1 && i < numberOfLines) {

						context.setStroke(BLUE);
						context.strokeText(Integer.toString(txt), 1, y2 + 4);

						context.setStroke(DODGERBLUE);
						context.strokeLine(leftMargin * 0.85, y2, width, y2);
					}

					else {
						context.setStroke(DODGERBLUE);
						context.strokeLine(leftMargin, y2, width, y2);
					}
				}
			}

			//	---------------------------------------------------------------------------------------

			Strip
				strip = FileCache.loadCurrent().getSignal().getStrip(0);

			WaveHeader
				currentHeader = FileCache.loadCurrent().getHeader();

			int
				bitsPerSample = currentHeader.getField(FileContentStructure.BITS_PER_SAMPLE) - 1,
				samplingRate = currentHeader.getField(FileContentStructure.SAMPLE_PER_SEC),
				verticalGridResolution = samplingRate / 1000,
				movement = (int) horizontal.getValue(),
				verticalGridMovement = movement % verticalGridResolution;

			//*	verticals  ----------------------------------------------------------------------------

			context.setStroke(DODGERBLUE);

			context.strokeLine(leftMargin, 0, leftMargin, height);
			context.strokeLine(width, 0, width + leftMargin, height);

			context.setStroke(LIGHTGREY);

			for (int i = 0 ; i < width - leftMargin - 2; i++) {

				int
					x = (i * verticalGridResolution) /*+ leftMargin*/ - verticalGridMovement;

				context.strokeLine(x, 0, x, height);
			}

			//*	waveForm  -----------------------------------------------------------------------------

			context.setStroke(RED);

			for (int i = 1; i < Math.min(width, strip.size() - width) - 2 - 20; i++) {

				double
					prevSample = (double) strip.get(i + movement - 1),
					sample = (double) strip.get(i + movement),

					verticalCenter = height / 2,

					prevSampleAmplitude = prevSample / (double) (1 << bitsPerSample),
					prevDcOffset = verticalCenter * (1 - prevSampleAmplitude),

					sampleAmplitude = sample / (double) (1 << bitsPerSample),
					dcOffset = verticalCenter * (1 - sampleAmplitude);

				context.strokeLine(i + 20, prevDcOffset, i + 20 + 1, dcOffset);
			}
			if (!canvasArePainted) canvasArePainted = true;
		}
	}

	void redraw(Canvas canvas, ScrollBar horizontal/*, ScrollBar vertical*/) {

		clean(canvas);

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		if (FileCache.fileCache.size() > 0) drawEverything(canvas, horizontal/*, vertical*/);
	}

	void clean(Canvas canvas) {

		if (canvasArePainted) {

			GraphicsContext
				context = canvas.getGraphicsContext2D();

			context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

			canvasArePainted = false;
		}
	}

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		launch(args);
	}
}