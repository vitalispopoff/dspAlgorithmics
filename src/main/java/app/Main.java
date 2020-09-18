package app;

import data.FileCache;
import data.structure.*;
import gui.Menus.MainMenuController;
import javafx.application.Application;
import javafx.beans.property.*;
import javafx.fxml.FXMLLoader;
import javafx.geometry.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.control.ScrollBar;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.*;
import javafx.scene.text.Font;
import javafx.stage.*;

import java.io.*;
import java.net.URL;
import java.util.concurrent.atomic.AtomicReference;

import static javafx.scene.paint.Color.*;

public class Main extends Application {

	static Stage
		theStage;

	static double
		stageW = 640.,
		stageH = 480.;

	private static void setupTheStage(Stage stage) {

		theStage = stage;

		theStage.setWidth(stageW + stageWAdjust);
		theStage.setHeight(stageH + stageHAdjust);

		theStage.setResizable(true);
	}

	@Override
	public void start(Stage stage) {

		setupTheStage(stage);

		bindings(stage);

		stage.setScene(scene);
		stage.show();
	}

	public static void main(String[] args) {

		launch(args);
	}



	static boolean
		canvasArePainted = false;

	static double
		stageWAdjust = 16,
		stageHAdjust = 39,
		col0W = 5.,
		col2W = 25.,
		row0H = 30.,
		row2H = 25.,
		scrollBarAdjust = 2.,
		leftMargin = 20.,
		bottomMargin = 20.;

	static AtomicReference<Double>
		col1W = new AtomicReference<>(stageW - col0W - col2W),
		row1H = new AtomicReference<>(stageH - row0H - row2H);

	static ColumnConstraints
		col0 = new ColumnConstraints(col0W, col0W, col0W),
		col1 = new ColumnConstraints(col1W.get(), col1W.get(), col1W.get()),
		col2 = new ColumnConstraints(col2W, col2W, col2W);

	static RowConstraints
		row0 = new RowConstraints(row0H, row0H, row0H),
		row1 = new RowConstraints(row1H.get(), row1H.get(), row1H.get()),
		row2 = new RowConstraints(row2H, row2H, row2H);

	static GridPane
		p = new GridPane();

	static Scene
		scene = new Scene(p);

	static Canvas
		canvas = new Canvas();

	static ScrollBar
		hScroll = new ScrollBar(),
		vScroll = new ScrollBar(),
		hScale = new ScrollBar(),
		vScale = new ScrollBar();

	static StackPane
		hScrollPane = new StackPane(),
		vScrollPane = new StackPane();

//	* local properties ------------------------------------------------------------------------

	private static final DoubleProperty
		hScrollWidth = new SimpleDoubleProperty(),
		vScrollHeight = new SimpleDoubleProperty();

	private static final BooleanProperty
		ctrlKeyIsDown = new SimpleBooleanProperty(false);

	public static double getHScrollWidth() {

		return hScrollWidth.get();
	}

	public static DoubleProperty hScrollWidthProperty() {

		return hScrollWidth;
	}

	public static double getVScrollHeight() {

		return vScrollHeight.get();
	}

	public static DoubleProperty vScrollHeightProperty() {

		return vScrollHeight;
	}

	public static boolean getCtrlKeyIsDown() {

		return ctrlKeyIsDown.get();
	}

	public static void setCtrlKeyIsDown(boolean b) {

		ctrlKeyIsDown.set(b);
	}

	public static BooleanProperty ctrlKeyIsDownProperty() {

		return ctrlKeyIsDown;
	}

//	-------------------------------------------------------------------------------------------

	private static void setupGridPaneP() {

		p.getColumnConstraints().add(col0);
		p.getColumnConstraints().add(col1);
		p.getColumnConstraints().add(col2);

		p.getRowConstraints().add(row0);
		p.getRowConstraints().add(row1);
		p.getRowConstraints().add(row2);

		p.setGridLinesVisible(true);

		col1.minWidthProperty().bind(hScrollWidthProperty());
		col1.maxWidthProperty().bind(hScrollWidthProperty());
		row1.minHeightProperty().bind(vScrollHeightProperty());
		row1.maxHeightProperty().bind(vScrollHeightProperty());
	}

	private static void setupMainMenu() {

		MainMenuController.setStage(theStage);

		String
			location = "E:\\_LAB\\pl\\popoff\\dspAlgorithmics\\src\\main\\resources\\FXMLMainMenu.fxml";

		try {

			URL
				url = new File(location).toURI().toURL();

			FXMLLoader
				loader = new FXMLLoader(url);

			Node
				bar = loader.load();

			p.add(bar, 0, 0, 3, 1);
			GridPane.setValignment(bar, VPos.TOP);
			GridPane.setHalignment(bar, HPos.LEFT);

		}    //	* adding bar to the gridPane

		catch (IOException e) {

			e.printStackTrace();
		}
	}

	private static void setupCanvas() {

		canvas.setWidth(col1W.get());
		canvas.setHeight(row1H.get());

		p.add(canvas, 1, 1);

		canvas.widthProperty().bind(
			theStage.widthProperty()
				.subtract(stageWAdjust)
				.subtract(col0W)
				.subtract(col2W)
		);

		canvas.heightProperty().bind(
			theStage.heightProperty()
				.subtract(stageHAdjust)
				.subtract(row0H)
				.subtract(row2H)
		);
	}

	private static void setupScrollBars() {

		{
			hScroll.setOrientation(Orientation.HORIZONTAL);
			hScroll.setMin(0.);
			hScroll.setMax(1.);
			hScroll.setValue(0.);

			vScroll.setOrientation(Orientation.VERTICAL);
			vScroll.setMin(0.);
			vScroll.setMax(1.);
			vScroll.setValue(0.5);

			hScale.setOrientation(Orientation.HORIZONTAL);
			hScale.setMin(32.);
			hScale.setValue(col1W.get() - scrollBarAdjust);
			hScale.setMax(col1W.get() - scrollBarAdjust);

			vScale.setOrientation(Orientation.VERTICAL);
			vScale.setMin(33.);
			vScale.setValue((row1H.get() - scrollBarAdjust) / 2.);
			vScale.setMax(row1H.get() - scrollBarAdjust);

		}    // * scrollBars setup

		{

			hScroll.visibleProperty().bind(ctrlKeyIsDownProperty().not());
			vScroll.visibleProperty().bind(ctrlKeyIsDownProperty().not());

			hScrollPane.visibleProperty().bind(MainMenuController.cacheIsEmptyStaticProperty().not());
			vScrollPane.visibleProperty().bind(MainMenuController.cacheIsEmptyStaticProperty().not());

			hScroll.minWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
			hScroll.maxWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
			hScale.minWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
			hScale.maxWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));

			vScroll.minHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
			vScroll.maxHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
			vScale.minHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
			vScale.maxHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));

		}    // * bindings

		{

			hScrollPane.getChildren().addAll(hScale, hScroll);
			vScrollPane.getChildren().addAll(vScale, vScroll);

			p.add(hScrollPane, 1, 2);
			p.add(vScrollPane, 2, 1);

		}    // * adding scrollbarPanes to the grid

	}

//	-------------------------------------------------------------------------------------------

	private static void bindings(Stage stage){

		hScrollWidthProperty().bind(
			stage.widthProperty()
				.subtract(stageWAdjust)
				.subtract(col0W)
				.subtract(col2W)
		);

		vScrollHeightProperty().bind(
			stage.heightProperty()
				.subtract(stageHAdjust)
				.subtract(row0H)
				.subtract(row2H)
		);

		setupTheStage(stage);
		setupGridPaneP();
		setupMainMenu();
		setupCanvas();
		setupScrollBars();

		scene.addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> setCtrlKeyIsDown(event.isControlDown()));
		scene.addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> setCtrlKeyIsDown(event.isControlDown()));
	}

//	drawings ----------------------------------------------------------------------------------

	static void drawHorizontals() {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		double
			verticalScale = 1.,
			horizontalScale = 1.,

			height = canvas.getHeight(),
			width = canvas.getWidth(),

			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;

		context.setLineWidth(1);
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
					context.strokeText(Integer.toString(txt), 1, y1 + 4);

					context.setStroke(DODGERBLUE);
					context.strokeLine(leftMargin * 0.85, y1, width, y1);
				}

				else {
					context.strokeLine(leftMargin, y1, width, y1);
				}
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
	}

	static void drawVerticals() {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		double
			height = canvas.getHeight(),
			width = canvas.getWidth();

		Strip
			strip = FileCache.loadCurrent().getSignal().getStrip(0);

		WaveHeader
			currentHeader = FileCache.loadCurrent().getHeader();

		int
			waveLength = strip.size(),
			bitsPerSample = currentHeader.getField(FileContentStructure.BITS_PER_SAMPLE) - 1,
			samplingRate = currentHeader.getField(FileContentStructure.SAMPLE_PER_SEC),
			verticalGridResolution = samplingRate / 1000,
			movement = (int) hScroll.getValue(),
			verticalGridMovement = movement % verticalGridResolution;

		context.setStroke(DODGERBLUE);

		context.strokeLine(leftMargin, 0, leftMargin, height);
		context.strokeLine(width, 0, width + leftMargin, height);

		context.setStroke(LIGHTGREY);

		for (int i = 0; i < width - leftMargin - 2; i++) {

			int
				x = (i * verticalGridResolution) - verticalGridMovement;

			context.strokeLine(x, 0, x, height);
		}
	}

	void drawEverything() {

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		clean();
		context.setLineWidth(1);

		double
			verticalScale = 1.,
			horizontalScale = 1.,

			height = canvas.getHeight(),
			width = canvas.getWidth(),

			scaledHeight = height * verticalScale,
			adjustToVerticalCenter = (scaledHeight / 2.),
			accountForMinResolution = adjustToVerticalCenter / 4;


		/*{
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
						context.strokeText(Integer.toString(txt), 1, y1 + 4);

						context.setStroke(DODGERBLUE);
						context.strokeLine(leftMargin * 0.85, y1, width, y1);
					}

					else {
						context.strokeLine(leftMargin, y1, width, y1);
					}
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
		}
		*/    // * horizontals

		drawHorizontals();
		drawVerticals();


		//	-------------------------------------------------------------------------------

		Strip
			strip = FileCache.loadCurrent().getSignal().getStrip(0);

		WaveHeader
			currentHeader = FileCache.loadCurrent().getHeader();

		int
			leftMargin = 20;

		int
			waveLength = strip.size(),
			bitsPerSample = currentHeader.getField(FileContentStructure.BITS_PER_SAMPLE) - 1,
			samplingRate = currentHeader.getField(FileContentStructure.SAMPLE_PER_SEC),
			verticalGridResolution = samplingRate / 1000,
			movement = (int) hScroll.getValue(),
			verticalGridMovement = movement % verticalGridResolution;

		// * verticals  -------------------------------------------------------------------

/*		context.setStroke(DODGERBLUE);

		context.strokeLine(leftMargin, 0, leftMargin, height);
		context.strokeLine(width, 0, width + leftMargin, height);

		context.setStroke(LIGHTGREY);

		for (int i = 0; i < width - leftMargin - 2; i++) {

			int
				x = (i * verticalGridResolution) - verticalGridMovement;

			context.strokeLine(x, 0, x, height);
		}*/

		// * waveForm  --------------------------------------------------------------------

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

	void redraw() {

		clean();

		GraphicsContext
			context = canvas.getGraphicsContext2D();

		drawEverything();
	}

	void clean() {

		if (canvasArePainted) {

			GraphicsContext
				context = canvas.getGraphicsContext2D();

			context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());

			canvasArePainted = false;
		}
	}

//	--------------------------------------------------------------------------------------------------------------------


}