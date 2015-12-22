package com.rebel.cad.shape;

import com.rebel.cad.util.DoubleProperty;

import java.io.Serializable;

/**
 * Created by Slava on 22.09.2015.
 */
public class Point implements Serializable {
    private DoubleProperty x = new DoubleProperty();
    private DoubleProperty y = new DoubleProperty();

    public Point() {
    }

    public Point(double x, double y) {
        this.x.setValue(x);
        this.y.setValue(y);
    }

    public double getX() {
        return x.getValue();
    }

    public DoubleProperty getXProperty() {
        return x;
    }

    public void setX(double x) {
        this.x.setValue(x);
    }

    public double getY() {
        return y.getValue();
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
                "x=" + x.getValue() +
                ", y=" + y.getValue() +
                '}';
    }
}
