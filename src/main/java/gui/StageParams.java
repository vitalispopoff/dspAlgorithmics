package gui;

import javafx.beans.property.*;

public abstract class StageParams{

	public static double
		stageWAdjust = 16,
		stageHAdjust = 39;

//	-------------------------------------------------------------------------------------------

	private static final DoubleProperty
		initialStageWidth = new SimpleDoubleProperty(640.);

	public static double getInitialStageWidth() {

		return initialStageWidth.get();
	}

	public static DoubleProperty initialStageWidthProperty() {

		return initialStageWidth;
	}



	private static final DoubleProperty
		initialStageHeight = new SimpleDoubleProperty(480.);

	public static double getInitialStageHeight() {

		return initialStageHeight.get();
	}

	public static DoubleProperty initialStageHeightProperty() {

		return initialStageHeight;
	}

}