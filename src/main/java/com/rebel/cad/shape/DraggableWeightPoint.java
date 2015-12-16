package com.rebel.cad.shape;

import com.rebel.cad.MainApp;
import javafx.beans.value.ChangeListener;
import javafx.scene.Cursor;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 16.12.2015.
 */
public class DraggableWeightPoint extends WeightPoint {

    List<ChangeListener> listeners = new ArrayList<>();

    public DraggableWeightPoint(double x, double y, double weight) {
        super(x, y, weight);
        enableDrag();
    }

    public void addListener(ChangeListener<? extends Number> listener) {
        this.listeners.add(listener);
    }

    private void enableDrag() {
        final Delta dragDelta = new Delta();
        getCircle().setOnMousePressed(mouseEvent -> {
            dragDelta.x = getCircle().getCenterX() - mouseEvent.getX();
            dragDelta.y = getCircle().getCenterY() - mouseEvent.getY();
            MainApp.getMainStage().getScene().setCursor(Cursor.MOVE);
        });
        getCircle().setOnMouseReleased(mouseEvent -> MainApp.getMainStage().getScene().setCursor(Cursor.HAND));
        getCircle().setOnMouseDragged(mouseEvent -> {
            double newX = mouseEvent.getX() + dragDelta.x;
            if (newX > 0 && newX < MainApp.getMainStage().getScene().getWidth()) {
                getCircle().setCenterX(newX);
                setX(newX);
                listeners.forEach(changeListener -> changeListener.changed(null, null, null));
            }
            double newY = mouseEvent.getY() + dragDelta.y;
            if (newY > 0 && newY < MainApp.getMainStage().getScene().getHeight()) {
                getCircle().setCenterY(newY);
                setY(newY);
                listeners.forEach(changeListener -> changeListener.changed(null, null, null));
            }
        });
        getCircle().setOnMouseEntered(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                MainApp.getMainStage().getScene().setCursor(Cursor.HAND);
            }
        });
        getCircle().setOnMouseExited(mouseEvent -> {
            if (!mouseEvent.isPrimaryButtonDown()) {
                MainApp.getMainStage().getScene().setCursor(Cursor.DEFAULT);
            }
        });
    }

    private class Delta { double x, y; }

}
