package gui.temporarilyDisabled;

import javafx.beans.property.*;
import javafx.scene.Scene;
import javafx.scene.canvas.Canvas;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.StackPane;

public interface WindowListening {

/*
	static void setWatchedObjects(
		Scene scene, Canvas canvas,
		StackPane hSP, StackPane vSP,
		ScrollBar hScroll, ScrollBar vScroll,
		ScrollBar hScale, ScrollBar vScale) {

		MainWindowListener.scene = scene;
		MainWindowListener.canvas = canvas;
		MainWindowListener.hScroll = hScroll;
		MainWindowListener.vScroll = vScroll;
		MainWindowListener.hScale = hScale;
		MainWindowListener.vScale = vScale;
		MainWindowListener.hScrollPane = hSP;
		MainWindowListener.vScrollPane = vSP;
	}
*/

/*
	// ? mustRedraw -------------------------------------------

	static BooleanProperty mustRedrawProperty() {

		return MainWindowListener.mustRedraw;
	}

	// ? alreadyRedrawn	---------------------------------------

	static void setAlreadyRedrawn(boolean b) {

		MainWindowListener.alreadyRedrawn.set(b);
	}

	static BooleanProperty alreadyRedrawnProperty() {

		return MainWindowListener.alreadyRedrawn;
	}

	// ? canvasWidthChange ------------------------------------

	static void setCanvasWidthChange(boolean b) {

		MainWindowListener.canvasWidthChange.set(b);
	}

	static BooleanProperty canvasWidthChangeProperty() {

		return MainWindowListener.canvasWidthChange;
	}

	// ? canvasHeightChange -----------------------------------

	static BooleanProperty canvasHeightChangeProperty() {

		return MainWindowListener.canvasHeightChange;
	}

	static void setCanvasHeightChange(boolean b) {

		MainWindowListener.canvasHeightChange.set(b);
	}

	// ? hScrollValueChange -----------------------------------

	static void setHScrollValueChange(boolean b) {

		MainWindowListener.hScrollValueChange.set(b);
	}

	static BooleanProperty hScrollValueChangeProperty() {

		return MainWindowListener.hScrollValueChange;
	}

	// ? vScrollValueChange -----------------------------------

	static void setVScrollValueChange(boolean b) {

		MainWindowListener.vScrollValueChange.set(b);
	}

	static BooleanProperty vScrollValueChangeProperty() {

		return MainWindowListener.vScrollValueChange;
	}

	// ? hScaleValueChange ------------------------------------

	static void setHScaleValueChange(boolean b) {

		MainWindowListener.hScaleValueChange.set(b);
	}

	static BooleanProperty hScaleValueChangeProperty() {

		return MainWindowListener.hScaleValueChange;
	}

	// ? vScaleValueChange ------------------------------------

	static void setVScaleValueChange(boolean b) {

		MainWindowListener.vScaleValueChange.set(b);
	}

	static BooleanProperty vScaleValueChangeProperty() {

		return MainWindowListener.vScaleValueChange;
	}

	// ? ctrlKeyIsDown ----------------------------------------

	static void setCtrlKeyIsDown(boolean b) {

		MainWindowListener.ctrlKeyIsDown.set(b);
	}

	static BooleanProperty ctrlKeyIsDownProperty() {

		return MainWindowListener.ctrlKeyIsDown;
	}
*/

//	* bindings ------------------------------------------------

/*
	static void bindCtrlKeyIsDownProperty() {

		MainWindowListener.scene.addEventFilter(
			KeyEvent.KEY_PRESSED, (KeyEvent event) -> setCtrlKeyIsDown(event.isControlDown()));

		MainWindowListener.scene.addEventFilter(
			KeyEvent.KEY_RELEASED, (KeyEvent event) -> setCtrlKeyIsDown(event.isControlDown()));
	}
*/

/*static void bindAnother() {

		MainWindowListener.hScroll.visibleProperty()
			.bind(ctrlKeyIsDownProperty().not());
		MainWindowListener.vScroll.visibleProperty()
			.bind(ctrlKeyIsDownProperty().not());

		MainWindowListener.hScrollPane.visibleProperty()
			.bind(MainMenuController.cacheIsEmptyStaticProperty().not());
		MainWindowListener.vScrollPane.visibleProperty()
			.bind(MainMenuController.cacheIsEmptyStaticProperty().not());

		MainWindowListener.hScroll.minWidthProperty()
			.bind(MainWindowListener.canvas.widthProperty().subtract(MainWindowListener.scrollBarAdjust));
		MainWindowListener.hScroll.maxWidthProperty()
			.bind(MainWindowListener.canvas.widthProperty().subtract(MainWindowListener.scrollBarAdjust));
		MainWindowListener.hScale.minWidthProperty()
			.bind(MainWindowListener.canvas.widthProperty().subtract(MainWindowListener.scrollBarAdjust));
		MainWindowListener.hScale.maxWidthProperty()
			.bind(MainWindowListener.canvas.widthProperty().subtract(MainWindowListener.scrollBarAdjust));

		MainWindowListener.vScroll.minHeightProperty()
			.bind(MainWindowListener.canvas.heightProperty().subtract(MainWindowListener.scrollBarAdjust));
		MainWindowListener.vScroll.maxHeightProperty()
			.bind(MainWindowListener.canvas.heightProperty().subtract(MainWindowListener.scrollBarAdjust));
		MainWindowListener.vScale.minHeightProperty()
			.bind(MainWindowListener.canvas.heightProperty().subtract(MainWindowListener.scrollBarAdjust));
		MainWindowListener.vScale.maxHeightProperty()
			.bind(MainWindowListener.canvas.heightProperty().subtract(MainWindowListener.scrollBarAdjust));
	}
*/	// * bindings

/*
	static void bindLocalProperties() {

		MainWindowListener.canvas.widthProperty()
			.addListener((observable, oldValue, newValue) -> MainWindowListener.canvasWidthChange
			.set(!(MainWindowListener.alreadyRedrawn.get() || oldValue.equals(newValue))));

		MainWindowListener.canvas.heightProperty()
			.addListener((observable, oldValue, newValue) -> MainWindowListener.canvasHeightChange
			.set((MainWindowListener.alreadyRedrawn.get() || oldValue.equals(newValue))));

		MainWindowListener.hScroll.valueProperty().addListener((observable, oldValue, newValue)
			-> MainWindowListener.hScrollValueChange
				.set(MainWindowListener.alreadyRedrawn.get() && !oldValue.equals(newValue)));

		MainWindowListener.vScroll.valueProperty().addListener((observable, oldValue, newValue)
			-> MainWindowListener.vScrollValueChange
				.set(MainWindowListener.alreadyRedrawn.get() && !oldValue.equals(newValue)));

		MainWindowListener.hScale.valueProperty().addListener((observable, oldValue, newValue)
			-> MainWindowListener.hScaleValueChange
				.set(MainWindowListener.alreadyRedrawn.get() && !oldValue.equals(newValue)));

		MainWindowListener.hScroll.valueProperty().addListener((observable, oldValue, newValue)
			-> MainWindowListener.vScaleValueChange
				.set(MainWindowListener.alreadyRedrawn.get() && !oldValue.equals(newValue)));
	]
*/	// * temporal

/*
	static void bindMustRedrawProperty() {

		mustRedrawProperty()
			.bind(MainMenuController
					  .cacheIsEmptyStaticProperty()
					  .not()
					  .or(canvasWidthChangeProperty())
					  .or(canvasWidthChangeProperty())
//				.or(canvasHeightChangeProperty())
//				.or(hScaleValueChangeProperty())
//				.or(vScrollValueChangeProperty())
//				.or(hScaleValueChangeProperty())
//				.or(hScaleValueChangeProperty())
			);
	}
*/

/*
	static void resetLocalProperties() {

		setCanvasWidthChange(false);
		setCanvasHeightChange(false);
//		setHScrollValueChange(false);
//		setVScrollValueChange(false);
//		setHScaleValueChange(false);
//		setVScaleValueChange(false);
	}
*/

}

//	* properties class ----------------------------------------

abstract class MainWindowListener implements WindowListening {

	static double
		stageW = 640.,
		stageH = 480.,
		stageWAdjust = 16,
		stageHAdjust = 39,
		col0W = 5.,
		col2W = 25.,
		row0H = 30.,
		row2H = 25.,
		scrollBarAdjust = 2.,
		leftMargin = 20.;

	static Scene
		scene;

	static Canvas
		canvas;

	static ScrollBar
		hScroll, vScroll, hScale, vScale;

	static StackPane
		hScrollPane,
		vScrollPane;

	static final BooleanProperty
		mustRedraw = new SimpleBooleanProperty(false),
		alreadyRedrawn = new SimpleBooleanProperty(true),
		canvasWidthChange = new SimpleBooleanProperty(),
		canvasHeightChange = new SimpleBooleanProperty(),
		hScrollValueChange = new SimpleBooleanProperty(),
		vScrollValueChange = new SimpleBooleanProperty(),
		hScaleValueChange = new SimpleBooleanProperty(),
		vScaleValueChange = new SimpleBooleanProperty(),

	ctrlKeyIsDown = new SimpleBooleanProperty(false);
}