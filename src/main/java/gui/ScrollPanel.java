package gui;

import data.FileCache;
import gui.Menus.MainMenuController;
import javafx.beans.binding.*;
import javafx.beans.property.*;
import javafx.geometry.Orientation;
import javafx.scene.control.ScrollBar;
import javafx.scene.layout.StackPane;

import static javafx.geometry.Orientation.*;

public class ScrollPanel extends StackPane {

	static double
		scrollBarAdjust = 1.;
	private final Root
		parent;

	Orientation
		orientation;

	private final ScrollBar
		scroll,
		scale;


	public ScrollPanel(Root r, Orientation o) {

		super();

		parent = r;
		orientation = o;
		scroll = new ScrollBar();
		scale = new ScrollBar();

		scale.setValue(1.);
		scroll.setValue(0.);

		setupScrollBars();
		getChildren().addAll(scroll, scale);

		visibleProperty().bind(MainMenuController.cacheIsEmptyStaticProperty().not());
	}


	private void setupScrollBars() {

		scroll.setOrientation(orientation);
		scale.setOrientation(orientation);

		bindPropertyPairs(scroll);
		bindPropertyPairs(scale);

		scrollValueProperty().bind(scroll.valueProperty());
		scaleValueProperty().bind(scale.valueProperty());

		bindScrollBarSizeProperties();

		bindScrollRangeProperties();
	}


	private void bindScrollRangeProperties() {

		DoubleBinding    // ! TODO - needs rework
			power = new DoubleBinding() {

			{
				super.bind(scale.valueProperty());
			}

			@Override
			protected double computeValue() {

				return 2. * getScrollPanelSize() * Math.pow(2., scale.getValue()) / scale.getMax();
			}
		};

		NumberBinding
			minScroll =
			isHorizontal()
				? (new DoubleBinding() {

				@Override
				protected double computeValue() {

					return 0.;
				}
			})
				: ((Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0)))
					   .then(power.multiply(-1.))
					   .otherwise(0.)),

			maxScroll =
				isHorizontal()
					? FileCache.currentFileSignalLengthBinding()
					: ((Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0)))
						   .then(power)
						   .otherwise(0.)),

			minScale =
				isHorizontal()
					? (Bindings.when(FileCache.currentFileSignalLengthBinding().greaterThan(0))
						   .then(1.)
						   .otherwise(0.))
					: (Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0))
						   .then(1.)
						   .otherwise(0.)),

			maxScale =
				isHorizontal()
					? (Bindings.when(FileCache.currentFileSignalLengthBinding().greaterThan(0))
						   .then(new DoubleBinding() {

							   {
								   super.bind(FileCache.currentFileSignalLengthBinding());
							   }

							   @Override
							   protected double computeValue() {

								   return
									   Math.log(FileCache.getCurrentFileSignalLength()) / Math.log(2.);
							   }
						   })
						   .otherwise(0.))
					: (Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0))
						   .then(FileCache.currentFileBitsPerSampleBinding())
						   .otherwise(0.));

		scroll.minProperty().bind(minScroll);
		scroll.maxProperty().bind(maxScroll);
		scale.minProperty().bind(minScale);
		scale.maxProperty().bind(maxScale);

	}

	private void bindPropertyPairs(ScrollBar s) {

		(isHorizontal() ? s.minWidthProperty() : s.minHeightProperty())
			.bind(scrollPanelSizeProperty());

		(isHorizontal() ? s.maxWidthProperty() : s.maxHeightProperty())
			.bind(scrollPanelSizeProperty());

//		scroll.visibleAmountProperty().bind(scroll.maxProperty().divide(scale.valueProperty().divide(2.)));
	}

	public void bindScrollBarVisibilityProperties(BooleanProperty b) {

		scale.visibleProperty().bind(b);
	}

	private void bindScrollBarSizeProperties() {

		scrollPanelSizeProperty().bind(
			(isHorizontal()
				 ? parent.dynamicAreaWidthProperty()
				 : parent.dynamicAreaHeightProperty())
				.add(scrollBarAdjust)
				.subtract(20.)
		);
	}

	private boolean isHorizontal() {

		return orientation == HORIZONTAL;
	}


	private final DoubleProperty
		scrollPanelSize = new SimpleDoubleProperty();

	public double getScrollPanelSize() {

		return scrollPanelSize.get();
	}

	public DoubleProperty scrollPanelSizeProperty() {

		return scrollPanelSize;
	}


	private final DoubleProperty
		scrollValue = new SimpleDoubleProperty();

	public double getScrollValue() {

		return scrollValue.get();
	}

	public DoubleProperty scrollValueProperty() {

		return scrollValue;
	}


	private final DoubleProperty
		scaleValue = new SimpleDoubleProperty();

	public double getScaleValue() {

		return scaleValue.get();
	}

	public DoubleProperty scaleValueProperty() {

		return scaleValue;
	}
}