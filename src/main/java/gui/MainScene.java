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

        addEventFilter(KeyEvent.KEY_PRESSED, this::ctrlKeyIsDown);
        addEventFilter(KeyEvent.KEY_RELEASED, this::ctrlKeyIsDown);
        addEventFilter(KeyEvent.KEY_TYPED, this::ctrlKeyIsDown);

        ((Root) root).bindScrollBarsVisibleProperties(ctrlKeyIsDownProperty());
    }



    // ----- properties ------------------------------------------------------------------------

    private static final IntegerProperty
        changeNotifier = new SimpleIntegerProperty(1);

    private final BooleanProperty
        keyIsPressed = new SimpleBooleanProperty(),
        ctrlKeyIsDown = new SimpleBooleanProperty(false);


    public static Integer getChangeNotifier(){

        return changeNotifier.get();
    }

    public static IntegerProperty changeNotifierProperty(){

        return changeNotifier;
    }



    public BooleanProperty getKeyIsPressedProperty() {

        return keyIsPressed;
    }



    public BooleanProperty ctrlKeyIsDownProperty() {

        return ctrlKeyIsDown;
    }


    public boolean getCtrlKeyIsDown() {

        return ctrlKeyIsDown.get();
    }

    private void ctrlKeyIsDown(boolean b) {

        ctrlKeyIsDown.set(b);

        System.out.println(getCtrlKeyIsDown());
    }

    private void ctrlKeyIsDown(KeyEvent event) {

        if (event.getCode() == KeyCode.CONTROL) ctrlKeyIsDown.set(event.isControlDown());

        if (event.getEventType() == KeyEvent.KEY_TYPED) ctrlKeyIsDown.set(false);
    }

}