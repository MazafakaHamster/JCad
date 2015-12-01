package com.rebel.cad.shapes;

import java.util.ArrayList;

/**
 * Created by Slava on 01.12.2015.
 */
public class SuperEllipse extends TPolyline {
    private double centerX;
    private double centerY;
    private double a;
    private double b;
    private double n;

    public SuperEllipse(double centerX, double centerY, double a, double b, double n) {
        this.centerX = centerX;
        this.centerY = centerY;
        this.a = a;
        this.b = b;
        this.n = n;

        ArrayList<Dot> dots = new ArrayList<>();
        for (int i = 0; i < 360; i += 1) {
            double rad = Math.toRadians(i);
            double cos = Math.cos(rad);
            double sin = Math.sin(rad);
            double x = a * Math.pow(cos, n) + centerX;
            double y = b * Math.pow(sin, n) + centerY;
            dots.add(new Dot(x, y));
        }
        addPoints(dots);
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getA() {
        return a;
    }

    public double getB() {
        return b;
    }

    public double getN() {
        return n;
    }
}
