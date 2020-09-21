package gui;

import javafx.beans.binding.IntegerBinding;

public class PreviewRefreshTrigger {

    private final Root
        root;

    private final double[]
        observedParameters = new double[6];

/*    private final int
        change = -1;*/



    public PreviewRefreshTrigger(Root r){

        root = r;

        scrollPanelsState = bindScrollPanelsState();

        setScrollPanelsStateValues();

    }



    private final IntegerBinding
            scrollPanelsState;

    public IntegerBinding getScrollPanelsState() {

        return scrollPanelsState;
    }



    private void setScrollPanelsStateValues() {

        observedParameters[0] = root.getDynamicAreaWidth();
        observedParameters[1] = root.getDynamicAreaHeight();
        observedParameters[2] = root.getHorizontalScrollPanel().getScrollValue();
        observedParameters[3] = root.getHorizontalScrollPanel().getScaleValue();
        observedParameters[4] = root.getVerticalScrollPanel().getScrollValue();
        observedParameters[5] = root.getVerticalScrollPanel().getScaleValue();
    }

    private IntegerBinding bindScrollPanelsState() {

        IntegerBinding iB = new IntegerBinding() {

            {
                super.bind(
                        root.dynamicAreaWidthProperty(),
                        root.dynamicAreaHeightProperty(),
                        root.getHorizontalScrollPanel().scrollValueProperty(),
                        root.getHorizontalScrollPanel().scaleValueProperty(),
                        root.getVerticalScrollPanel().scrollValueProperty(),
                        root.getVerticalScrollPanel().scaleValueProperty()
                );
            }

            private int adjust(int index, double newValue){

                int
                    i = 1 << index;

                return newValue - observedParameters[index] != 0
                        ? /*((-1 * change) & i)*/ 0
                        : /*-1 & i*/ i;
            }

            @Override
            protected int computeValue() {

                int
                    a = adjust(0, root.getDynamicAreaWidth()),
                    b = adjust(1, root.getDynamicAreaHeight()),
                    c = adjust(2, root.getHorizontalScrollPanel().getScrollValue()),
                    d = adjust(3, root.getHorizontalScrollPanel().getScaleValue()),
                    e = adjust(4, root.getVerticalScrollPanel().getScrollValue()),
                    f = adjust(5, root.getVerticalScrollPanel().getScaleValue()),
                    result = a + b + c + d + e + f;

                setScrollPanelsStateValues();
                return result;
            }
        };

        return iB;
    }
}