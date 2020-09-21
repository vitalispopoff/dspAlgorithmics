package gui;

import javafx.beans.property.*;
import javafx.scene.*;
import javafx.scene.canvas.*;
import javafx.scene.input.*;

public class MainScene extends Scene {

    static Canvas
            canvas = new Canvas();



    public MainScene(Parent root) {

        super(root);

        addEventFilter(KeyEvent.KEY_PRESSED, this::setCtrlKeyIsDown);
        addEventFilter(KeyEvent.KEY_RELEASED, this::setCtrlKeyIsDown);
        addEventFilter(KeyEvent.KEY_TYPED, this::setCtrlKeyIsDown);

        ((Root) root).bindScrollBarsVisibleProperties(ctrlKeyIsDownProperty());
    }



    private static final IntegerProperty
        changeNotifier = new SimpleIntegerProperty(1);

    public static Integer getChangeNotifier(){

        return changeNotifier.get();
    }

    public static IntegerProperty changeNotifierProperty(){

        return changeNotifier;
    }



    private final BooleanProperty
            keyIsPressed = new SimpleBooleanProperty();

    public BooleanProperty getKeyIsPressedProperty() {

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

        if (event.getCode() == KeyCode.CONTROL) ctrlKeyIsDown.set(event.isControlDown());

        if (event.getEventType() == KeyEvent.KEY_TYPED) ctrlKeyIsDown.set(false);
    }

    public BooleanProperty ctrlKeyIsDownProperty() {

        return ctrlKeyIsDown;
    }

}