package gui;

import javafx.beans.property.*;
import javafx.stage.Stage;

public abstract class StageParams{

	public static double
		stageWAdjust = 16,
		stageHAdjust = 39;

/*	public MainStage() {

		super();

		setWidth(getInitialStageWidth() + stageWAdjust);
		setHeight(getInitialStageHeight() + stageHAdjust);
		setResizable(true);
	}*/	// ? disposable

//	-------------------------------------------------------------------------------------------

	private static final DoubleProperty
		initialStageWidth = new SimpleDoubleProperty(640.);

	public static double getInitialStageWidth() {

		return initialStageWidth.get();
	}
	public static void setInitialStageWidth(double d) {

		initialStageWidth.set(d);
	}
	public static DoubleProperty initialStageWidthProperty() {

		return initialStageWidth;
	}



	private static final DoubleProperty
		initialStageHeight = new SimpleDoubleProperty(480.);

	public static double getInitialStageHeight() {

		return initialStageHeight.get();
	}
	public static void setInitialStageHeight(double d) {

		initialStageHeight.set(d);
	}
	public static DoubleProperty initialStageHeightProperty() {

		return initialStageHeight;
	}

}