package com.rebel.cad.shape;

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
        for (int i = 0; i <= 360; i += 1) {
            double rad = Math.toRadians(i);
            double cos = Math.cos(rad);
            double sin = Math.sin(rad);
            double x = a * Math.pow(Math.abs(cos), 2 / n) * Math.signum(cos) + centerX;
            double y = b * Math.pow(Math.abs(sin), 2 / n) * Math.signum(sin) + centerY;
            dots.add(new Dot(x, y));
        }
        addPoints(dots);
    }

    public double derivative(double x0, double y0) {
        return (-b * b * x0 * Math.pow(Math.abs(x0 / a), n - 2)) / (a * a * y0 * Math.pow(Math.abs(y0 / b), n - 2));
        //return ((b * x0 *  Math.Pow(Math.Abs(x0 / a), n - 2) * Math.Pow(1 - Math.Pow(Math.Abs(x0 / a), n), 1 / n)) / (a * a * (Math.Pow(Math.Abs(x0 / a), n) - 1)));
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
