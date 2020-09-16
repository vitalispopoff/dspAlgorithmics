package app;

import data.FileCache;
import data.structure.*;
import gui.Menus.MainMenuController;
import javafx.application.Application;
import javafx.beans.binding.DoubleBinding;
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

		double
			stageW = 640.,
			stageH = 480.,

			stageWAdjust = 16,
			stageHAdjust = 39,

			col0W = 5.,
			col2W = 25.,

			row0H = 30.,
			row2H = 25.;

		AtomicReference<Double>
			col1W = new AtomicReference<>(stageW - col0W - col2W),
			row1H = new AtomicReference<>(stageH - row0H - row2H);

		{
			stage.setWidth(stageW + stageWAdjust);
			stage.setHeight(stageH + stageHAdjust);

			stage.setResizable(true);
		}	// * stage setup

		//	* Scene -------------------------------------------------------------------------------

		GridPane
			p = new GridPane();

		p.setGridLinesVisible(true);

		Scene
			scene = new Scene(p, stageW, stageH);

		//	* GridPane ----------------------------------------------------------------------------

		p.setGridLinesVisible(true);

		ColumnConstraints
			col0 = new ColumnConstraints(col0W, col0W, col0W),
			col1 = new ColumnConstraints(col1W.get(), col1W.get(), col1W.get()),
			col2 = new ColumnConstraints(col2W, col2W, col2W);

		RowConstraints
			row0 = new RowConstraints(row0H, row0H, row0H),
			row1 = new RowConstraints(row1H.get(), row1H.get(), row1H.get()),
			row2 = new RowConstraints(row2H, row2H, row2H);

		{
			p.getColumnConstraints().add(col0);
			p.getColumnConstraints().add(col1);
			p.getColumnConstraints().add(col2);

			p.getRowConstraints().add(row0);
			p.getRowConstraints().add(row1);
			p.getRowConstraints().add(row2);
		}	// * gridPane setup

		//	? MainMenu ----------------------------------------

		MainMenuController.setStage(stage);

		String
			location = "E:\\_LAB\\pl\\popoff\\dspAlgorithmics\\src\\main\\resources\\FXMLMainMenu.fxml";

		URL
			url;

		FXMLLoader
			loader;

		Node
			bar;

		try {

			url = new File(location).toURI().toURL();
			loader = new FXMLLoader(url);
			bar = loader.load();

			p.add(bar, 0, 0, 3, 1);
			GridPane.setValignment(bar, VPos.TOP);
			GridPane.setHalignment(bar, HPos.LEFT);

		}    //	* loading and adding bar to the gridPane

		catch (IOException e) {

			e.printStackTrace();
		}

		//	? Canvas ------------------------------------------

		Canvas
			canvas = new Canvas();

		canvas.setWidth(col1W.get());
		canvas.setHeight(row1H.get());

		p.add(canvas, 1, 1);

		Runnable r = () -> {

			GraphicsContext
				g = canvas.getGraphicsContext2D();

			g.clearRect(0, 0, col1.getMinWidth(), row1.getMinHeight());
			g.strokeLine(0, 0, col1.getMinWidth(), row1.getMinHeight());
		};	// ! temporal
		r.run();

		//	? ScrollBars --------------------------------------
		
		double
			scrollBarAdjust = 2.;

		ScrollBar
			hScroll = new ScrollBar(),
			vScroll = new ScrollBar();

		{
//			hScroll.setVisible(false);
//			vScroll.setVisible(false);

			hScroll.setOrientation(Orientation.HORIZONTAL);
			hScroll.setMinWidth(col1W.get() - scrollBarAdjust);
			hScroll.setMaxWidth(col1W.get() - scrollBarAdjust);
			hScroll.setMin(0.);
			hScroll.setMax(col1W.get() - scrollBarAdjust);

			vScroll.setOrientation(Orientation.VERTICAL);
			vScroll.setMinHeight(row1H.get() - scrollBarAdjust);
			vScroll.setMaxHeight(row1H.get() - scrollBarAdjust);
			vScroll.setMin(0.);
			hScroll.setMax(row1H.get() - scrollBarAdjust);

			p.add(hScroll, 1, 2);
			GridPane.setValignment(hScroll, VPos.CENTER);
			GridPane.setHalignment(hScroll, HPos.CENTER);

			p.add(vScroll, 2, 1);
			GridPane.setValignment(vScroll, VPos.CENTER);
			GridPane.setHalignment(vScroll, HPos.CENTER);
		}	// * scrollBars setup

		//*	listeners	---------------------------------------------------------------------------

		stage.widthProperty().addListener((observable, oldValue, newValue) -> {

			double
				value = (double) newValue - stageWAdjust,
				v = value - col0W - col2W;

			{
				col1.setMinWidth(v);
				col1.setMaxWidth(v);

				canvas.setWidth(v);

				r.run();    //	! temporal
			}	// * scene scale horizontal

			{
				hScroll.setMinWidth(v - scrollBarAdjust);
				hScroll.setMaxWidth(v - scrollBarAdjust);
				hScroll.setMax(v - scrollBarAdjust);
			}	// * hScroll scale


		});

		stage.heightProperty().addListener((observable, oldValue, newValue) -> {

			double
				value = (double) newValue - stageHAdjust,
				v = value - row0H - row2H;

			{
				row1.setMinHeight(v);
				row1.setMaxHeight(v);

				canvas.setHeight(v);

				r.run();	//	! temporal
			}	// * scene scale vertical

			{
				vScroll.setMinHeight(v - scrollBarAdjust);
				vScroll.setMaxHeight(v - scrollBarAdjust);
				hScroll.setMax(v - scrollBarAdjust);
			}	// * vScroll scale
		});





/*		AtomicReference<Double>
			canvasWidth = new AtomicReference<>(0.),
			canvasHeight = new AtomicReference<>(0.),
			scrollHorizontalMax = new AtomicReference<>(0.),
			scrollVerticalMax = new AtomicReference<>(0.);

		AtomicReference<Boolean>
			cacheIsLoaded = new AtomicReference<>(false);

		AtomicReference<Integer>
			currentIndex = new AtomicReference<>(0),
			waveLength = new AtomicReference<>(0);
*/    // ? disposable ?

/*
		stage.widthProperty()
			.addListener((observable, oldValue, newValue) -> {

				double
					d = (double) newValue
							- 16		// ?
							- 2 * 20	// 0th and 2nd column
					;

				if (canvas.getWidth() != d) canvas.setWidth(d);

				canvasWidth.set(d + 20);
				scrollHorizontalMax.set(waveLength.get().doubleValue() - canvasWidth.get());
				hScroll.setMax(scrollHorizontalMax.get());
				hScroll.setMinWidth(canvasWidth.get() + 16);
				hScroll.setMaxWidth(canvasWidth.get() + 16);
				redraw(canvas, hScroll);

				col1.setPrefWidth(d);
				hScroll.setMaxWidth(d - 2);
				hScroll.setMinWidth(d - 2);
			});
*/

/*
		stage.heightProperty()
			.addListener((observable, oldValue, newValue) -> {

				double
					d = (double) newValue
							- 25		// main menu
							- 32		// window system header bar ?
							- 20		// horizontal Scroll row

					;

//				if (b.getHeight() != d) canvas.setHeight(d);
				if (row2.getPrefHeight() != d) canvas.setHeight(d);

				canvasHeight.set( d);
				scrollVerticalMax.set( d);	// TODO to be adjusted with "wave max peak"
				vScroll.setMinHeight(canvasHeight.get() - 12);
				vScroll.setMaxHeight(canvasHeight.get() - 12);
				redraw(canvas, hScroll);

				row2.setPrefHeight(d);
				vScroll.setMaxHeight(d );
				vScroll.setMinHeight(d );
			});
*/

/*
		FileCache.currentIndexDueProperty()
			.addListener((observable, oldValue, newValue) -> {

				currentIndex.set((int) newValue);
				waveLength.set(FileCache.loadCurrent().getSignal().getStrip(0).size());
				scrollHorizontalMax.set(waveLength.get().doubleValue() - canvasWidth.get());
				hScroll.setMax(scrollHorizontalMax.get());

				if( ! cacheIsLoaded.get()) clean(canvas);

				else redraw(canvas, hScroll);
			});
*/

/*
		MainMenuController.cacheIsEmptyStaticProperty()
			.addListener((observable, oldValue, newValue) -> {

				hScroll.setVisible( ! newValue);
				vScroll.setVisible( ! newValue);

				cacheIsLoaded.set(!MainMenuController.cacheIsEmpty());

				if(newValue) clean(canvas);

				else redraw(canvas, hScroll);
			});
*/

/*
		hScroll.valueProperty().addListener((observable, oldValue, newValue) -> redraw(canvas, hScroll));
*/

		//  ---------------------------------------------------------------------------------------

		stage.setScene(scene);
		stage.show();
	}

//	--------------------------------------------------------------------------------------------------------------------

/*
	void drawEverything(Canvas canvas, ScrollBar horizontal) {

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
					x = (i * verticalGridResolution) - verticalGridMovement;

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
*/

/*
	void redraw(Canvas canvas, ScrollBar horizontal) {

		clean(canvas);

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		if (FileCache.fileCache.size() > 0) drawEverything(canvas, horizontal);
	}
*/

/*
	void clean(Canvas canvas) {

		if (canvasArePainted) {

			GraphicsContext
				context = canvas.getGraphicsContext2D();

			context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

			canvasArePainted = false;
		}
	}
*/

//	--------------------------------------------------------------------------------------------------------------------

	public static void main(String[] args) {

		launch(args);
	}
}
