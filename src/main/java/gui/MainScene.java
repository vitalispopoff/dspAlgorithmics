package gui;

//import data.FileCache;
//import data.structure.*;
//import gui.Menus.MainMenuController;

import javafx.beans.property.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.KeyCode;
import javafx.scene.input.KeyEvent;
//import javafx.scene.text.Font;
//import java.util.concurrent.atomic.AtomicReference;
//
//import static gui.StageParams.*;
//import static javafx.scene.paint.Color.*;

public class MainScene extends Scene {

//	static Stage
//		stage;
//
//	static boolean
//		canvasArePainted = false;
//
//	static double
//		stageW = getInitialStageWidth(),
//		stageH = StageParams.getInitialStageHeight(),
//		stageWAdjust = StageParams.stageWAdjust,
//		stageHAdjust = StageParams.stageHAdjust,
//		col0W = 5.,
//		col2W = 25.,
//		row0H = 30.,
//		row2H = 25.,
//		scrollBarAdjust = 2.,
//		leftMargin = 20.,
//		bottomMargin = 20.;
//
//	static AtomicReference<Double>
//		col1W = new AtomicReference<>(stageW - col0W - col2W),
//		row1H = new AtomicReference<>(stageH - row0H - row2H);

    static Canvas
            canvas = new Canvas();


// ? Constructor	-------------------------------------------

//	public static MainScene getInstance(Stage stage){
//
//		Root root = new Root(stage);
//
//		return new MainScene(root);
//	}

    public MainScene(Parent root) {

        super(root);

        addEventFilter(KeyEvent.KEY_PRESSED, (KeyEvent event) -> {

            setCtrlKeyIsDown(event);
/*            if (event.getCode() == KeyCode.CONTROL)
                setCtrlKeyIsDown(true);*/
        });

        addEventFilter(KeyEvent.KEY_RELEASED, (KeyEvent event) -> {

            setCtrlKeyIsDown(event);
//            setCtrlKeyIsDown(event.isControlDown());
        });

        addEventFilter(KeyEvent.KEY_TYPED, (KeyEvent event) -> {

            setCtrlKeyIsDown(event);
//            setCtrlKeyIsDown(false);
        });



        ((Root) root).bindScrollBarsVisibleProperties(this);

//		setupCanvas();

    }

//	? element setups ------------------------------------------

    static void bindings() {

//		hScrollWidthProperty().bind(
//			stage.widthProperty()
//				.subtract(stageWAdjust)
//				.subtract(col0W)
//				.subtract(col2W)
//		);
//
//		vScrollHeightProperty().bind(
//			stage.heightProperty()
//				.subtract(stageHAdjust)
//				.subtract(row0H)
//				.subtract(row2H)
//		);
//
//		hScroll.visibleProperty().bind(ctrlKeyIsDownProperty().not());
//		vScroll.visibleProperty().bind(ctrlKeyIsDownProperty().not());

//		col1.minWidthProperty().bind(hScrollWidthProperty());
//		col1.maxWidthProperty().bind(hScrollWidthProperty());
//		row1.minHeightProperty().bind(vScrollHeightProperty());
//		row1.maxHeightProperty().bind(vScrollHeightProperty());
    }

    private static void setupCanvas() {

//		pane.add(canvas, 1, 1);
//
//		canvas.widthProperty().bind(
//			stage.widthProperty()
//				.subtract(stageWAdjust)
//				.subtract(col0W)
//				.subtract(col2W)
//		);
//
//		canvas.heightProperty().bind(
//			stage.heightProperty()
//				.subtract(stageHAdjust)
//				.subtract(row0H)
//				.subtract(row2H)
//		);

    }

    private static void setupScrollBars() {

//		{
//			hScroll.setOrientation(Orientation.HORIZONTAL);
//			hScroll.setMin(0.);
//			hScroll.setMax(1.);
//			hScroll.setValue(0.);
//
//			vScroll.setOrientation(Orientation.VERTICAL);
//			vScroll.setMin(0.);
//			vScroll.setMax(1.);
//			vScroll.setValue(0.5);
//
//			hScale.setOrientation(Orientation.HORIZONTAL);
//			hScale.setMin(32.);
//			hScale.setValue(col1W.get() - scrollBarAdjust);
//			hScale.setMax(col1W.get() - scrollBarAdjust);
//
//			vScale.setOrientation(Orientation.VERTICAL);
//			vScale.setMin(33.);
//			vScale.setValue((row1H.get() - scrollBarAdjust) / 2.);
//			vScale.setMax(row1H.get() - scrollBarAdjust);
//
//		}    // * scrollBars setup
//
//		{
//
//
////			hScrollPane.visibleProperty().bind(MainMenuController.cacheIsEmptyStaticProperty().not());
////			vScrollPane.visibleProperty().bind(MainMenuController.cacheIsEmptyStaticProperty().not());
//
//			hScroll.minWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
//			hScroll.maxWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
//			hScale.minWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
//			hScale.maxWidthProperty().bind(hScrollWidthProperty().subtract(scrollBarAdjust));
//
//			vScroll.minHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
//			vScroll.maxHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
//			vScale.minHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
//			vScale.maxHeightProperty().bind(vScrollHeightProperty().subtract(scrollBarAdjust));
//
//		}    // * bindings
//
//		{
//			hScrollPane.getChildren().addAll(/*hScale,*/ hScroll);
//			vScrollPane.getChildren().addAll(/*vScale,*/ vScroll);

//			pane.add(hScrollPane, 1, 2);
//			pane.add(vScrollPane, 2, 1);

//		}    // adding scrollbarPanes to the grid

    }

// ? Properties	-----------------------------------------------

    private final BooleanProperty
            keyIsPressed = new SimpleBooleanProperty();

    public BooleanProperty getKeyIsPressed() {

        return keyIsPressed;
    }


    private final BooleanProperty
            ctrlKeyIsDown = new SimpleBooleanProperty(true);

    public boolean getCtrlKeyIsDown() {

        return ctrlKeyIsDown.get();
    }

    private void setCtrlKeyIsDown(boolean b) {

        ctrlKeyIsDown.set(b);

        System.out.println(getCtrlKeyIsDown());
    }

    private void setCtrlKeyIsDown(KeyEvent event) {


        if (event.getCode() == KeyCode.CONTROL) {

            ctrlKeyIsDown.set(event.isControlDown());

 /*           if (event.getEventType() == KeyEvent.KEY_PRESSED)
                ctrlKeyIsDown.set(true);

            if (event.getEventType() == KeyEvent.KEY_RELEASED)
                ctrlKeyIsDown.set(false);*/

        }

        if (event.getEventType() == KeyEvent.KEY_TYPED)
            ctrlKeyIsDown.set(false);

//        System.out.println(getCtrlKeyIsDown());
    }

    public BooleanProperty ctrlKeyIsDownProperty() {

        return ctrlKeyIsDown;
    }

//	drawings ----------------------------------------------------------------------------------


    static void drawHorizontals() {
//
//		GraphicsContext
//			context = canvas.getGraphicsContext2D();
//
//		double
//			verticalScale = 1.,
//			horizontalScale = 1.,
//
//			height = canvas.getHeight(),
//			width = canvas.getWidth(),
//
//			scaledHeight = height * verticalScale,
//			adjustToVerticalCenter = (scaledHeight / 2.),
//			accountForMinResolution = adjustToVerticalCenter / 4;
//
//		context.setLineWidth(1);
//		context.setStroke(BLUE);
//		context.strokeLine(0, height / 2, width, height / 2);
//
//		int
//			numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)),
//			leftMargin = 20;
//
//		context.setFont(new Font("Arial", 10));
//
//		for (int i = 1; i <= numberOfLines; i++) {
//
//			context.setStroke(DODGERBLUE);
//
//			double
//				y1 = (height / 2) - scaledHeight / (1 << i),
//				y2 = (height / 2) + scaledHeight / (1 << i);
//
//			int
//				txt = (-(i - 1) * 6);
//
//			if (y1 >= 0) {
//
//				if (i > 1 && i < numberOfLines) {
//
//					context.setStroke(BLUE);
//					context.strokeText(Integer.toString(txt), 1, y1 + 4);
//
//					context.setStroke(DODGERBLUE);
//					context.strokeLine(leftMargin * 0.85, y1, width, y1);
//				}
//
//				else {
//					context.strokeLine(leftMargin, y1, width, y1);
//				}
//			}
//
//			if (y2 <= height && y1 != y2) {
//
//				if (i > 1 && i < numberOfLines) {
//
//					context.setStroke(BLUE);
//					context.strokeText(Integer.toString(txt), 1, y2 + 4);
//
//					context.setStroke(DODGERBLUE);
//					context.strokeLine(leftMargin * 0.85, y2, width, y2);
//				}
//
//				else {
//					context.setStroke(DODGERBLUE);
//					context.strokeLine(leftMargin, y2, width, y2);
//				}
//			}
//		}
    }

    static void drawVerticals() {

//		GraphicsContext
//			context = canvas.getGraphicsContext2D();
//
//		double
//			height = canvas.getHeight(),
//			width = canvas.getWidth();
//
//		Strip
//			strip = FileCache.loadCurrent().getSignal().getStrip(0);
//
//		WaveHeader
//			currentHeader = FileCache.loadCurrent().getHeader();
//
//		int
//			waveLength = strip.size(),
//			bitsPerSample = currentHeader.getField(FileContentStructure.BITS_PER_SAMPLE) - 1,
//			samplingRate = currentHeader.getField(FileContentStructure.SAMPLE_PER_SEC),
//			verticalGridResolution = samplingRate / 1000,
//			movement = (int) hScroll.getValue(),
//			verticalGridMovement = movement % verticalGridResolution;
//
//		context.setStroke(DODGERBLUE);
//
//		context.strokeLine(leftMargin, 0, leftMargin, height);
//		context.strokeLine(width, 0, width + leftMargin, height);
//
//		context.setStroke(LIGHTGREY);
//
//		for (int i = 0; i < width - leftMargin - 2; i++) {
//
//			int
//				x = (i * verticalGridResolution) - verticalGridMovement;
//
//			context.strokeLine(x, 0, x, height);
//		}
    }

    void drawEverything() {

//		GraphicsContext
//			context = canvas.getGraphicsContext2D();
//
//		clean();
//		context.setLineWidth(1);
//
//		double
//			verticalScale = 1.,
//			horizontalScale = 1.,
//
//			height = canvas.getHeight(),
//			width = canvas.getWidth(),
//
//			scaledHeight = height * verticalScale,
//			adjustToVerticalCenter = (scaledHeight / 2.),
//			accountForMinResolution = adjustToVerticalCenter / 4;
//
//
//		/*{
//			context.setStroke(BLUE);
//			context.strokeLine(0, height / 2, width, height / 2);
//
//			int
//				numberOfLines = (32 - Integer.numberOfLeadingZeros((int) accountForMinResolution)),
//				leftMargin = 20;
//
//			context.setFont(new Font("Arial", 10));
//
//			for (int i = 1; i <= numberOfLines; i++) {
//
//				context.setStroke(DODGERBLUE);
//
//				double
//					y1 = (height / 2) - scaledHeight / (1 << i),
//					y2 = (height / 2) + scaledHeight / (1 << i);
//
//				int
//					txt = (-(i - 1) * 6);
//
//				if (y1 >= 0) {
//
//					if (i > 1 && i < numberOfLines) {
//
//						context.setStroke(BLUE);
//						context.strokeText(Integer.toString(txt), 1, y1 + 4);
//
//						context.setStroke(DODGERBLUE);
//						context.strokeLine(leftMargin * 0.85, y1, width, y1);
//					}
//
//					else {
//						context.strokeLine(leftMargin, y1, width, y1);
//					}
//				}
//
//				if (y2 <= height && y1 != y2) {
//
//					if (i > 1 && i < numberOfLines) {
//
//						context.setStroke(BLUE);
//						context.strokeText(Integer.toString(txt), 1, y2 + 4);
//
//						context.setStroke(DODGERBLUE);
//						context.strokeLine(leftMargin * 0.85, y2, width, y2);
//					}
//
//					else {
//						context.setStroke(DODGERBLUE);
//						context.strokeLine(leftMargin, y2, width, y2);
//					}
//				}
//			}
//		}
//		*/    // * horizontals
//
//		drawHorizontals();
//		drawVerticals();
//
//
//		//	-------------------------------------------------------------------------------
//
//		Strip
//			strip = FileCache.loadCurrent().getSignal().getStrip(0);
//
//		WaveHeader
//			currentHeader = FileCache.loadCurrent().getHeader();
//
//		int
//			leftMargin = 20;
//
//		int
//			waveLength = strip.size(),
//			bitsPerSample = currentHeader.getField(FileContentStructure.BITS_PER_SAMPLE) - 1,
//			samplingRate = currentHeader.getField(FileContentStructure.SAMPLE_PER_SEC),
//			verticalGridResolution = samplingRate / 1000,
//			movement = (int) hScroll.getValue(),
//			verticalGridMovement = movement % verticalGridResolution;
//
//		// * verticals  -------------------------------------------------------------------
//
///*		context.setStroke(DODGERBLUE);
//
//		context.strokeLine(leftMargin, 0, leftMargin, height);
//		context.strokeLine(width, 0, width + leftMargin, height);
//
//		context.setStroke(LIGHTGREY);
//
//		for (int i = 0; i < width - leftMargin - 2; i++) {
//
//			int
//				x = (i * verticalGridResolution) - verticalGridMovement;
//
//			context.strokeLine(x, 0, x, height);
//		}*/
//
//		// * waveForm  --------------------------------------------------------------------
//
//		context.setStroke(RED);
//
//		for (int i = 1; i < Math.min(width, strip.size() - width) - 2 - 20; i++) {
//
//			double
//				prevSample = (double) strip.get(i + movement - 1),
//				sample = (double) strip.get(i + movement),
//
//				verticalCenter = height / 2,
//
//				prevSampleAmplitude = prevSample / (double) (1 << bitsPerSample),
//				prevDcOffset = verticalCenter * (1 - prevSampleAmplitude),
//
//				sampleAmplitude = sample / (double) (1 << bitsPerSample),
//				dcOffset = verticalCenter * (1 - sampleAmplitude);
//
//			context.strokeLine(i + 20, prevDcOffset, i + 20 + 1, dcOffset);
//		}
//		if (!canvasArePainted) canvasArePainted = true;
    }

    void redraw() {
//
//		clean();
//
//		GraphicsContext
//			context = canvas.getGraphicsContext2D();
//
//		drawEverything();
//	}
//
//	void clean() {
//
//		if (canvasArePainted) {
//
//			GraphicsContext
//				context = canvas.getGraphicsContext2D();
//
//			context.clearRect(0, 0, canvas.getWidth(), canvas.getHeight());
//
//			canvasArePainted = false;
//		}
    }

}
