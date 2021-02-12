package gui;

import data.FileCache;
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
		root;

	Orientation
		orientation;

	public final ScrollBar
		scroll,
		scale;


	public ScrollPanel(Root r, Orientation o) {

		super();

		root = r;
		orientation = o;

		(scroll = new ScrollBar()).setValue(0.);
		(scale = new ScrollBar()).setValue(isHorizontal() ? 0. : 1.);

//		scroll.setValue(0.);
//		scale.setValue(isHorizontal() ? 0. : 1.);

//	! LIKE SERIOUSLY THIS THING ABOVE... ¯\_(ツ)_/¯

		setupScrollBars();
		getChildren().addAll(scroll, scale);

		visibleProperty().bind(FileCache.fileCacheIsEmptyStaticProperty().not());
	}


	private void setupScrollBars() {

		scroll.setOrientation(orientation);
		scale.setOrientation(orientation);

		bindScrollBarSizeProperties(scroll);
		bindScrollBarSizeProperties(scale);

		scrollValueProperty().bind(scroll.valueProperty());
		scaleValueProperty().bind(scale.valueProperty());

		bindScrollBarSizeProperties();
		bindScrollBarRangeProperties();
	}

	private boolean isHorizontal() {

		return orientation == HORIZONTAL;
	}



	public void bindScrollBarVisibilityProperties(BooleanProperty b) {

		scale.visibleProperty().bind(b);
	}

	private void bindScrollBarRangeProperties() {

		DoubleBinding
			power = new DoubleBinding() {

			{
				super.bind(scale.valueProperty());
			}

			@Override
			protected double computeValue() {

				return 2. * getScrollPanelSize() * Math.pow(2., scale.getValue()) / scale.getMax();
			}
		},
			zero = new DoubleBinding() {

				@Override
				protected double computeValue() {
					return 0.;
				}
			};

		NumberBinding
			minScroll =
			isHorizontal()
				? (zero)
				: ((Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0)))
					   .then(-1.).otherwise(0.)),

			maxScroll =
				isHorizontal()
					? (new DoubleBinding() {

					{ super.bind(
							FileCache.currentFileSignalLengthBinding(),
							scaleValueProperty()
						); }

					@Override
					protected double computeValue() {
						return FileCache.getCurrentFileSignalLength();
					}
				})

					: ((Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0)))
						   .then(1.).otherwise(0.)),

			minScale =
				isHorizontal()
					? (Bindings.when(FileCache.currentFileSignalLengthBinding().greaterThan(0))
						   .then(new DoubleBinding() {

							   {
								   super.bind(
								   		FileCache.currentFileSignalLengthBinding()
									   , root.dynamicAreaWidthProperty()
								   );
							   }

							   @Override
							   protected double computeValue() {

							   	double
									logMinWidth = (int) Math.log(root.getDynamicAreaWidth())/* * 1.4426950408889634*/;

								return (logMinWidth - Math.log(FileCache.getCurrentFileSignalLength())) * 1.4426950408889634;
							   }
						   }).otherwise(0.))
					: (Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0))
						   .then(0.9).otherwise(0.)),

			maxScale =
				isHorizontal()
					? (Bindings.when(FileCache.currentFileSignalLengthBinding().greaterThan(0))
						   .then(4.).otherwise(0.))
					: (Bindings.when(FileCache.currentFileBitsPerSampleBinding().greaterThan(0))
						   .then(FileCache.currentFileBitsPerSampleBinding().subtract(3.)).otherwise(0.));

		scroll.minProperty().bind(minScroll);
		scroll.maxProperty().bind(maxScroll);
		scale.minProperty().bind(minScale);
		scale.maxProperty().bind(maxScale);

	}

	private void bindScrollBarSizeProperties(ScrollBar s) {

		(isHorizontal() ? s.minWidthProperty() : s.minHeightProperty())
			.bind(scrollPanelSizeProperty());

		(isHorizontal() ? s.maxWidthProperty() : s.maxHeightProperty())
			.bind(scrollPanelSizeProperty());
	}

	private void bindScrollBarSizeProperties() {

		scrollPanelSizeProperty().bind(
			(isHorizontal()
				 ? root.dynamicAreaWidthProperty()
				 : root.dynamicAreaHeightProperty())
				.add(scrollBarAdjust)
				.subtract(20.)
		);
	}

	// ----- properties ------------------------------------------------------------------------

	private final DoubleProperty
		scrollPanelSize = new SimpleDoubleProperty(),
		scrollValue = new SimpleDoubleProperty(),
		scaleValue = new SimpleDoubleProperty();


	public double getScrollPanelSize() {

		return scrollPanelSize.get();
	}

	public DoubleProperty scrollPanelSizeProperty() {

		return scrollPanelSize;
	}



	public double getScrollValue() {

		return scrollValue.get();
	}

	public DoubleProperty scrollValueProperty() {

		return scrollValue;
	}



	public double getScaleValue() {

		return scaleValue.get();
	}

	public DoubleProperty scaleValueProperty() {

		return scaleValue;
	}
}