package com.rebel.cad.shape;

import com.rebel.cad.MainApp;
import com.rebel.cad.collections.ShapeGroup;
import com.rebel.cad.controllers.MainController;
import com.rebel.cad.shape.wrappers.CircleWrapper;
import com.rebel.cad.util.DoubleChangeListener;
import com.rebel.cad.util.DoubleProperty;
import javafx.animation.Animation;
import javafx.animation.Transition;
import javafx.scene.Cursor;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.util.Duration;

import java.util.Collection;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by Slava on 16.12.2015.
 */
public class CurvePoint extends ShapeGroup {

    private Set<DoubleChangeListener> listeners = new HashSet<>();
    private WeightPoint weightPoint;
    private CircleWrapper circle;
    private Animation animation;
    private double tempX;
    private double tempY;
    private boolean recording;

    public CurvePoint(double x, double y, double weight) {
        weightPoint = new WeightPoint(x, y, weight);
        circle = new CircleWrapper(x, y, 7, Color.BLUE);
        circle.setOpacity(0.6);
        getChildren().add(circle);
        enableDrag();
    }

    public Set<DoubleChangeListener> getListeners() {
        return listeners;
    }

    public void addListeners(Collection<DoubleChangeListener> listeners) {
        this.listeners.addAll(listeners);
    }

    public void savePosition() {
        tempX = getX();
        tempY = getY();
        recording = true;
    }

    public void restorePosition() {
        recording = false;
        if (getX() != tempX && getY() != tempY) {
            setX(tempX);
            setY(tempY);
            setAnimation(tempX, getX(), tempY, getY(), 2000);
        }
    }

    public boolean isRecording() {
        return recording;
    }

    public void setAnimation(double startX, double endX, double startY, double endY, double t) {
        setX(startX);
        setY(startY);
        animation = new Transition() {
            {
                setCycleDuration(Duration.millis(t));
                setCycleCount(2);
            }

            double calcY(double x) {
                return ((x - startX) * (endY - startY)) / (endX - startX) + startY;
            }

            protected void interpolate(double frac) {
                double sign = (getX() < endX) ? 1 : -1;
                setX(getX() + sign * Math.abs(getX() - endX) * frac);
                setY(calcY(getX()));
            }

            @Override
            public void play() {
                setX(startX);
                setY(startY);
                super.play();
            }
        };
    }

    public void play() {
        if (animation != null) {
            animation.play();
        }
    }

    public void addListener(DoubleChangeListener listener) {
        this.listeners.add(listener);
    }

    private void enableDrag() {
        final Delta dragDelta = new Delta();
        getCircle().setOnMouseClicked(mouseEvent -> MainController.setCurrPoint(this));
        getCircle().setOnMousePressed(mouseEvent -> {
            dragDelta.x = getCircle().getCenterX() - mouseEvent.getX();
            dragDelta.y = getCircle().getCenterY() - mouseEvent.getY();
            MainApp.getMainStage().getScene().setCursor(Cursor.MOVE);
        });
        getCircle().setOnMouseReleased(mouseEvent -> MainApp.getMainStage().getScene().setCursor(Cursor.HAND));
        getCircle().setOnMouseDragged(mouseEvent -> {
            double newX = mouseEvent.getX() + dragDelta.x;
            if (newX > 0 && newX < MainApp.getMainStage().getScene().getWidth()) {
                setX(newX);
            }
            double newY = mouseEvent.getY() + dragDelta.y;
            if (newY > 0 && newY < MainApp.getMainStage().getScene().getHeight()) {
                setY(newY);
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

    public void setX(double x) {
        circle.setCenterX(x);
        weightPoint.setX(x);
        changed();
    }

    public DoubleProperty getXProperty() {
        return weightPoint.getXProperty();
    }

    public double getY() {
        return weightPoint.getY();
    }

    public void setY(double y) {
        circle.setCenterY(y);
        weightPoint.setY(y);
        changed();
    }

    public DoubleProperty getYProperty() {
        return weightPoint.getYProperty();
    }

    public double getWeight() {
        return weightPoint.getWeight();
    }

    public void setWeight(double weight) {
        weightPoint.setWeight(weight);
        changed();
    }

    public DoubleProperty getWeightProp() {
        return weightPoint.getWeightProperty();
    }

    @Override
    public String toString() {
        return "CurvePoint{" +
                "listeners=" + listeners +
                ", weightPoint=" + weightPoint +
                ", circle=" + circle +
                ", animation=" + animation +
                ", tempX=" + tempX +
                ", tempY=" + tempY +
                ", recording=" + recording +
                '}';
    }

    private void changed() {
        listeners.forEach(changeListener -> changeListener.changed(0, 0));
    }

    private class Delta {
        double x, y;
    }

    public void startConnection() {
        getCircle().setOnMouseClicked(mouseEvent -> {
            getCircle().setFill(Color.RED);
            MainController.setCurrPoint(this);
        });
    }

    public void resetCircle() {
        circle.setCenterX(getX());
        circle.setCenterY(getY());
        circle.setRadius(7);
        circle.setFill(Color.BLUE);
        circle.setOpacity(0.6);

    }

    public void endConnection() {
        getCircle().setFill(Color.BLUE);
        getCircle().setOnMouseClicked(mouseEvent -> {
            MainController.setCurrPoint(this);
        });
    }

    @Override
    public boolean equals(Object object) {
        if (!(object instanceof CurvePoint)) {
            return false;
        }
        CurvePoint curvePoint = (CurvePoint) object;
        return getX() == curvePoint.getX() && getY() == curvePoint.getY() && getWeight() == curvePoint.getWeight();
    }
}
