package gui;

import algorithms.analyzers.BitRepresent;
import javafx.beans.property.*;

import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;

public class PreviewRefreshTrigger {

	private final Root
		root;
	
	private DoubleProperty[]
		properties;

	final AtomicReference<Integer> index = new AtomicReference<>();

	public PreviewRefreshTrigger(Root r) {

		root = r;

		properties = new DoubleProperty[] {
			
			r.dynamicAreaWidthProperty(),
			r.dynamicAreaHeightProperty(),
			r.getHorizontalScrollPanel().scrollValueProperty(),
			r.getHorizontalScrollPanel().scaleValueProperty(),
			r.getVerticalScrollPanel().scrollValueProperty(),
			r.getVerticalScrollPanel().scaleValueProperty(),
		};

		for (int i = 0 ; i < properties.length; i++) {

			index.set(1 << i);

			properties[i].addListener((observable, oldValue, newValue) -> {

				scrollPanelState.set(
					((scrollPanelState.get() & index.get()) == index.get())
						? scrollPanelState.get() - index.get()
						: scrollPanelState.get() + index.get());
			});
		}
	}


	private final IntegerProperty
		scrollPanelState = new SimpleIntegerProperty(0);
	
	public final IntegerProperty scrollPanelStateProperty(){
		
		return scrollPanelState;
	}
}














