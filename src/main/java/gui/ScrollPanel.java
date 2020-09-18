package gui;

import javafx.beans.property.DoubleProperty;
import javafx.beans.property.SimpleDoubleProperty;
import javafx.geometry.Orientation;
import javafx.scene.Node;
import javafx.scene.Parent;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.StackPane;

public class ScrollPanel extends StackPane {

	static double
			scrollBarAdjust = 2.;
	Root
		parent;

	Orientation
		orientation;

	ScrollBar
		scroll,
		scale;

	private final DoubleProperty
		scrollPanelSize = new SimpleDoubleProperty();

	public ScrollPanel(Root r, Orientation o){

		super();

		parent = r;
		orientation = o;
		scroll = new ScrollBar();
		scale = new ScrollBar();

		bindScrollBarProperties();
		setupScrollBars();
		getChildren().addAll(scale, scroll);
	}

	private void setupScrollBars(){

		scroll.setOrientation(orientation);
		scale.setOrientation(orientation);

		bindPropertyPairs(scroll, orientation);
		bindPropertyPairs(scale, orientation);

/*		if (orientation == Orientation.HORIZONTAL){

			scroll.minWidthProperty().bind(scrollPanelSizeProperty());
			scroll.maxWidthProperty().bind(scrollPanelSizeProperty());
			scale.minWidthProperty().bind(scrollPanelSizeProperty());
			scale.maxWidthProperty().bind(scrollPanelSizeProperty());
		}

		else {

			scroll.minHeightProperty().bind(scrollPanelSizeProperty());
			scroll.maxHeightProperty().bind(scrollPanelSizeProperty());
			scale.minHeightProperty().bind(scrollPanelSizeProperty());
			scale.maxHeightProperty().bind(scrollPanelSizeProperty());
		}*/
	}

	private void bindPropertyPairs(ScrollBar s, Orientation o){

		boolean isHorizontal = o == Orientation.HORIZONTAL;

		(isHorizontal ? s.minWidthProperty() : s.minHeightProperty())
		.bind(scrollPanelSizeProperty());

		(isHorizontal ? s.maxWidthProperty() : s.maxHeightProperty())
			.bind(scrollPanelSizeProperty());
	}

	private void bindScrollBarProperties(){

		scrollPanelSizeProperty().bind(
			(orientation == Orientation.HORIZONTAL
				? parent.dynamicAreaWidthProperty()
				: parent.dynamicAreaHeightProperty())
			.subtract(scrollBarAdjust)
		);
	}

	public DoubleProperty scrollPanelSizeProperty(){

		return scrollPanelSize;
	}
}