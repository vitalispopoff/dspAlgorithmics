package gui;

import data.FileCache;
import gui.Menus.MainMenuController;
import javafx.beans.binding.Binding;
import javafx.beans.binding.Bindings;
import javafx.beans.property.*;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.StackPane;

public class ScrollPanel extends StackPane {

	static double
		scrollBarAdjust = 2.;
	private final Root
		parent;

	Orientation
		orientation;

	public final ScrollBar
		scroll,
		scale;



	public ScrollPanel(Root r, Orientation o){

		super();

		parent = r;
		orientation = o;
		scroll = new ScrollBar();
		scale = new ScrollBar();

		scroll.setMin(0.);
		scroll.setValue(0.);
		scale.setValue(1.);

		scroll.maxProperty().bind(
			FileCache.currentFileSignalLengthBinding()
			.subtract(scrollPanelSizeProperty())
			.subtract(20.)	// for preview panel margin
		);

		scale.minProperty().bind(
			Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0))
			.then(5.)
			.otherwise(0.)
		);

		scale.maxProperty().bind(
			Bindings.when(FileCache.currentFileSignalLengthBinding().greaterThan(0))
			.then(FileCache.currentFileSignalLengthBinding())
			.otherwise(0.)
		);

//		scale.visibleAmountProperty().bind(
//			Bindings.when(FileCache.currentFileSignalLengthBinding().greaterThan(0))
//			.then(scrollPanelSizeProperty().subtract(20))
//			.otherwise(0.)
//		);

		scroll.valueProperty();

		scrollValueProperty().bind(scroll.valueProperty());
		scaleValueProperty().bind(scale.valueProperty());

		bindScrollBarSizeProperties();
		setupScrollBars();
		getChildren().addAll(scroll, scale);

		visibleProperty().bind(MainMenuController.cacheIsEmptyStaticProperty().not());
	}



	private void setupScrollBars(){

		scroll.setOrientation(orientation);
		scale.setOrientation(orientation);

		bindPropertyPairs(scroll, orientation);
		bindPropertyPairs(scale, orientation);
	}



	private void bindPropertyPairs(ScrollBar s, Orientation o){

		boolean
			isHorizontal = o == Orientation.HORIZONTAL;

		(isHorizontal ? s.minWidthProperty() : s.minHeightProperty())
			.bind(scrollPanelSizeProperty());

		(isHorizontal ? s.maxWidthProperty() : s.maxHeightProperty())
			.bind(scrollPanelSizeProperty());
	}

	private void bindScrollBarSizeProperties(){

		scrollPanelSizeProperty().bind(
			(orientation == Orientation.HORIZONTAL
				? parent.dynamicAreaWidthProperty()
				: parent.dynamicAreaHeightProperty())
			.subtract(scrollBarAdjust)
		);
	}

	public void bindScrollBarVisibilityProperties(BooleanProperty b){

		scale.visibleProperty().bind(b);
	}



	private final DoubleProperty
			scrollPanelSize = new SimpleDoubleProperty();

	public double getScrollPanelSize(){

		return scrollPanelSize.get();
	}

	public DoubleProperty scrollPanelSizeProperty(){

		return scrollPanelSize;
	}



	private final DoubleProperty
			scrollValue = new SimpleDoubleProperty();

	public double getScrollValue(){

		return scrollValue.get();
	}

	public DoubleProperty scrollValueProperty() {

		return scrollValue;
	}



	private final DoubleProperty
			scaleValue = new SimpleDoubleProperty();

	public double getScaleValue(){

		return scaleValue.get();
	}

	public DoubleProperty scaleValueProperty() {

		return scaleValue;
	}
}