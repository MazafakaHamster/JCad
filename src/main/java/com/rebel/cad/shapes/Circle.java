package com.rebel.cad.shapes;

/**
 * Created by Slava on 05.11.2015.
 */
public class Circle extends Shape {
    private double centerX;
    private double centerY;
    private double radius;

    public Circle(double x, double y, double r) {
        this.centerX = x;
        this.centerY = y;
        this.radius = r;

        getPoints().addAll(x + r, y);
        for (int i = 0; i <= 360; i += 6) {
            getPoints().addAll(x + r * Math.cos(i * Math.PI / 180), y + r * Math.sin(i * Math.PI / 180));
        }
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
