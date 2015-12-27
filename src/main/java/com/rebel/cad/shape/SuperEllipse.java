package com.rebel.cad.shape;

import com.rebel.cad.controllers.MainController;
import com.rebel.cad.shape.wrappers.PolylineWrapper;

import java.util.ArrayList;

/**
 * Created by Slava on 01.12.2015.
 */
public class SuperEllipse extends PolylineWrapper {
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

        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i <= 360; i += 1) {
            double rad = Math.toRadians(i);
            double cos = Math.cos(rad);
            double sin = Math.sin(rad);
            double x = a * Math.pow(Math.abs(cos), 2 / n) * Math.signum(cos) + centerX;
            double y = b * Math.pow(Math.abs(sin), 2 / n) * Math.signum(sin) + centerY;
            points.add(new Point(x, y));
        }
        addPoints(points);
    }

    public double derivative(double x, double y) {
        return (b * b * MainController.toFakeX(x) * Math.pow(Math.abs(MainController.toFakeX(x) / a), n - 2)) / (a * a * MainController.toFakeY(y) * Math.pow(Math.abs(MainController.toFakeY(y) / b), n - 2));
    }

    public double function(double x) {
        return b * Math.pow(1 - Math.pow(Math.abs(MainController.toFakeX(x) / a), n), 1 / n);
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

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        this.centerX = centerX + x;
        this.centerY = centerY + y;
   }
}
