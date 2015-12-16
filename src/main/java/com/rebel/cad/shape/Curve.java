package com.rebel.cad.shape;

import javafx.beans.value.ChangeListener;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 15.12.2015.
 */
public class Curve extends Line {
    private WeightPoint a;
    private WeightPoint b;
    private WeightPoint c;
    private WeightPoint d;

    public Curve(DraggableWeightPoint a, DraggableWeightPoint b, DraggableWeightPoint c, DraggableWeightPoint d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;

        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            getPoints().clear();
            build();
        };

        a.addListener(listener);
        b.addListener(listener);
        c.addListener(listener);
        d.addListener(listener);
        build();
    }

    private void build() {
        List<Point> points = new ArrayList<>();
        for (double i = 0; i < 1; i += 0.001) {
            points.add(new Point(
                    function(a.getX(), b.getX(), c.getX(), d.getX(), i),
                    function(a.getY(), b.getY(), c.getY(), d.getY(), i)
            ));
        }
        addPoints(points);
    }

    public WeightPoint getA() {
        return a;
    }

    public void setA(WeightPoint a) {
        this.a = a;
    }

    public WeightPoint getB() {
        return b;
    }

    public void setB(WeightPoint b) {
        this.b = b;
    }

    public WeightPoint getC() {
        return c;
    }

    public void setC(WeightPoint c) {
        this.c = c;
    }

    public WeightPoint getD() {
        return d;
    }

    public void setD(WeightPoint d) {
        this.d = d;
    }

    private double function(double ra, double rb, double rc, double rd, double u) {
        return ra * a.getWeight() * Math.pow((1 - u), 3)
                + 3 * rb * b.getWeight() * Math.pow((1 - u), 2) * u
                + 3 * rc * c.getWeight() * (1 - u) * Math.pow(u, 2)
                + rd * d.getWeight() * Math.pow(u, 3);
    }
}
