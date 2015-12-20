package com.rebel.cad.shape;

import com.rebel.cad.MainApp;
import com.rebel.cad.collections.ShapeGroup;
import com.rebel.cad.controllers.MainController;
import com.rebel.cad.util.DoubleProp;
import javafx.beans.property.DoubleProperty;
import javafx.beans.value.ChangeListener;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 16.12.2015.
 */
public class CurvePoint extends ShapeGroup {

    private List<ChangeListener> listeners = new ArrayList<>();
    private WeightPoint weightPoint;
    private javafx.scene.shape.Circle circle;

    public CurvePoint(double x, double y, double weight) {
        weightPoint = new WeightPoint(x, y, weight);
        circle = new javafx.scene.shape.Circle(x, y, 7, Color.BLUE);
        circle.setOpacity(0.6);
        getChildren().add(circle);
        enableDrag();
    }

    public void addListener(ChangeListener<? extends Number> listener) {
        this.listeners.add(listener);
    }

    private void enableDrag() {
        final Delta dragDelta = new Delta();
        getCircle().setOnMouseClicked(mouseEvent -> {
            MainController.setCurrPoint(this);
        });
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
                weightPoint.setX(newX);
                listeners.forEach(changeListener -> changeListener.changed(null, null, null));
            }
            double newY = mouseEvent.getY() + dragDelta.y;
            if (newY > 0 && newY < MainApp.getMainStage().getScene().getHeight()) {
                getCircle().setCenterY(newY);
                weightPoint.setY(newY);
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

    public Circle getCircle() {
        return circle;
    }

    public double getX() {
        return weightPoint.getX();
    }

    public DoubleProperty getXProperty() {
        return weightPoint.getXProperty();
    }

    public void setX(double x) {
        weightPoint.setX(x);
    }

    public double getY() {
        return weightPoint.getY();
    }

    public DoubleProperty getYProperty() {
        return weightPoint.getYProperty();
    }

    public void setY(double y) {
        weightPoint.setY(y);
    }

    public double getWeight() {
        return weightPoint.getWeight();
    }

    public DoubleProperty getWeightProp() {
        return weightPoint.getWeightProperty();
    }

    public void setWeight(double weight) {
        weightPoint.setWeight(weight);
    }

    private class Delta { double x, y; }

    @Override
    public String toString() {
        return "CurvePoint{"+
                ", weightPoint=" + weightPoint.toString() +
                '}';
    }
}
