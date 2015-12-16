package com.rebel.cad.shape;

import com.rebel.cad.listeners.ValueChangeListener;

/**
 * Created by Slava on 22.09.2015.
 */
public class Point {
    private double x;
    private double y;
    protected ValueChangeListener listener;

    public Point() {
    }

    public Point(double x, double y) {
        this.x = x;
        this.y = y;
    }

    public double getX() {
        return x;
    }

    public void setX(double x) {
        this.x = x;
        if (listener != null)
            listener.valueChanged(this);
    }

    public double getY() {
        return y;
    }

    public void setY(double y) {
        this.y = y;
        if (listener != null)
            listener.valueChanged(this);
    }

    public void setListener(ValueChangeListener valueChangeListener) {
        this.listener = valueChangeListener;
    }

    @Override
    public String toString() {
        return "Point{" +
                "x=" + x +
                ", y=" + y +
                '}';
    }
}
