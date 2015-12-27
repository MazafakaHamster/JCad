package com.rebel.cad.shape;

/**
 * Created by Slava on 05.11.2015.
 */
public class Circle extends Arc {

    public Circle(double x, double y, double r) {
        super(x, y, r, 0, 360);
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }

    public Circle(Point point, double r) {
        super(point.getX(), point.getY(), r, 0, 360);
        this.centerX = point.getX();
        this.centerY = point.getY();
        this.radius = r;
    }

    public Circle(double x, double y, double r, Axis axis) {
        super(x, y, r, 0, 360, axis);
        this.centerX = x;
        this.centerY = y;
        this.radius = r;
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }
}
