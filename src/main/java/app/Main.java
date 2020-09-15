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

/*		AtomicReference<Double>
			stageW = new AtomicReference<>(640.),
			stageH = new AtomicReference<>(480.),

			col0W = new AtomicReference<>(5.),
			col2W = new AtomicReference<>(25.),

			row0H = new AtomicReference<>(30.),
			row2H = new AtomicReference<>(25.);
			
					DoubleBinding
			temp1 = stage.widthProperty().subtract(col0W.get()).subtract(col2W.get()),
			temp2 = stage.heightProperty().subtract(row0H.get()).subtract(row2H.get());
			*/

		double
			stageW = 640.,
			stageH = 480.,

			col0W = 5.,
			col2W = 25.,

			row0H = 30.,
			row2H = 25.;

//		DoubleBinding
//			temp1 = stage.widthProperty().subtract(col0W).subtract(col2W),
//			temp2 = stage.heightProperty().subtract(row0H).subtract(row2H);


		stage.setMinWidth(stageW/*.get()*/);
		stage.setMinHeight(stageH/*.get()*/);

//		MainMenuController.setStage(stage);

	//	* Scene -------------------------------------------------------------------------------

		GridPane
			p = new GridPane();

		p.setGridLinesVisible(true);

//		Scene scene = new Scene(p, stage.widthProperty().getValue(), stage.heightProperty().getValue());

		Scene scene = new Scene(p, 640, 480);

	//	* GridPane ----------------------------------------------------------------------------

		p.setGridLinesVisible(true);

		ColumnConstraints
			col0 = new ColumnConstraints(col0W/*.get()*/, col0W/*.get()*/, col0W/*.get()*/),
			col1 = new ColumnConstraints(600/*temp1.getValue()*/,600/* temp1.getValue()*/,600/* temp1.getValue()*/),
			col2 = new ColumnConstraints(col2W/*.get()*/, col2W/*.get()*/, col2W/*.get()*/);

		RowConstraints
			row0 = new RowConstraints(row0H/*.get()*/, row0H/*.get()*/, row0H/*.get()*/),
			row1 = new RowConstraints(400/*temp2.getValue()*/,400/*temp2.getValue()*/,400/*temp2.getValue()*/),
			row2 = new RowConstraints(row2H/*.get()*/, row2H/*.get()*/, row2H/*.get()*/);


		//	? MainMenu ----------------------------------------

/*		String
			location = "E:\\_LAB\\pl\\popoff\\dspAlgorithmics\\src\\main\\resources\\FXMLMainMenu.fxml";

		Node bar = null;

		try {

			URL
				url = new File(location).toURI().toURL();

			FXMLLoader
				loader = new FXMLLoader(url);

				bar = loader.load();
		}

		catch (IOException e){ e.printStackTrace();}*/

		//	? Canvas ------------------------------------------

//		Canvas
//			canvas = new Canvas();

		//	? ScrollBars --------------------------------------

//		ScrollBar
//			hScroll = new ScrollBar(),
//			vScroll = new ScrollBar();

		{
			p.getColumnConstraints().add(col0);
			p.getColumnConstraints().add(col1);
			p.getColumnConstraints().add(col2);

			p.getRowConstraints().add(row0);
			p.getRowConstraints().add(row1);
			p.getRowConstraints().add(row2);
//
//			hScroll.setVisible(false);
//			vScroll.setVisible(false);
//
//			hScroll.setOrientation(Orientation.HORIZONTAL);
//			vScroll.setOrientation(Orientation.VERTICAL);

//			if (bar != null) p.add(bar, 0, 0, 3, 1);
//			p.add(canvas, 1, 1);

//			p.add(hScroll, 1, 2);
//			GridPane.setValignment(hScroll, VPos.CENTER);
//			GridPane.setHalignment(hScroll, HPos.CENTER);
//
//			p.add(vScroll, 2, 1);
//			GridPane.setValignment(vScroll, VPos.CENTER);
//			GridPane.setHalignment(vScroll, HPos.CENTER);

		}	// ? GridPane settings

//		Rectangle
//			rect = new Rectangle(0, 0, 0, 0);
//		rect.setOpacity(0.7);
//		rect.setFill(GREEN);
//		p.add(rect, 1, 1);

//		GridPane.setHalignment(rect, HPos.LEFT);
//		GridPane.setValignment(rect, VPos.TOP);

	//	* Canvas & Scene ----------------------------------------------------------------------



		/*p.add(canvas, 1, 1, 2, 1);*/	// * new



	//	* borderPane --------------------------------------------------------------------------



	//*	listeners	---------------------------------------------------------------------------

/*		AtomicReference<Double>
			canvasWidth = new AtomicReference<>(0.),
			canvasHeight = new AtomicReference<>(0.),
			scrollHorizontalMax = new AtomicReference<>(0.),
			scrollVerticalMax = new AtomicReference<>(0.);

		AtomicReference<Boolean>
			cacheIsLoaded = new AtomicReference<>(false);

		AtomicReference<Integer>
			currentIndex = new AtomicReference<>(0),
			waveLength = new AtomicReference<>(0);*/



/*		stage.widthProperty()
			.addListener((observable, oldValue, newValue) -> {

				stageW.set((double) newValue);

//				double
//					temp = (double) newValue - col0W - col2W;

//				col1.setMinWidth(temp);
//				col1.setMaxWidth(temp);

//				rect.setWidth(temp);

			});*/

/*		stage.heightProperty()
			.addListener((observable, oldValue, newValue) -> {

				stageW.set((double) newValue);

//				row1.setMinHeight(temp);
//				row1.setMaxHeight(temp);

//				rect.setHeight(temp);


			});*/


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