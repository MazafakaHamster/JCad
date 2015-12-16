package com.rebel.cad.shape;

import com.rebel.cad.listeners.ValueChangeListener;
import com.sun.javafx.scene.DirtyBits;
import javafx.beans.InvalidationListener;
import javafx.beans.property.DoubleProperty;
import javafx.beans.property.DoublePropertyBase;
import javafx.beans.value.ChangeListener;
import javafx.beans.value.ObservableValue;

/**
 * Created by Slava on 22.09.2015.
 */
public class Point {
    private final DoubleProperty x = new DoublePropertyBase() {

        @Override
        public void invalidated() {
        }

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "X";
        }
    };

    private final DoubleProperty y = new DoublePropertyBase() {

        @Override
        public void invalidated() {
        }

        @Override
        public Object getBean() {
            return this;
        }

        @Override
        public String getName() {
            return "Y";
        }
    };

    public Point() {
    }

    public Point(double x, double y) {
        this.x.setValue(x);
        this.y.setValue(y);
    }

    public double getX() {
        return x.doubleValue();
    }

    public DoubleProperty getXProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.setValue(x);
    }

    public double getY() {
        return y.doubleValue();
    }

    public DoubleProperty getYProperty() {
        return y;
    }

    public void setY(double y) {
        this.y.setValue(y);
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
