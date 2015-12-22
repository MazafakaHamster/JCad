package com.rebel.cad.shape;

import com.rebel.cad.collections.ShapeGroup;
import com.rebel.cad.shape.wrappers.LineWrapper;
import com.rebel.cad.util.DoubleChangeListener;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Slava on 15.12.2015.
 */
public class Curve extends ShapeGroup implements Serializable {
    private CurvePoint a;
    private CurvePoint b;
    private CurvePoint c;
    private CurvePoint d;
    private BoundLine ab;
    private BoundLine dc;
    private Line line;

    public Curve(CurvePoint a, CurvePoint b, CurvePoint c, CurvePoint d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.line = new Line();
        LocalChangeListener listener = new LocalChangeListener();

        a.addListener(listener);
        b.addListener(listener);
        c.addListener(listener);
        d.addListener(listener);
        ab = new BoundLine(a, b);
        dc = new BoundLine(d, c);
        getChildren().addAll(ab);
        getChildren().addAll(dc);
        build();
        getChildren().addAll(a, b, c, d, line);
    }

    public void build() {
        line.getPoints().clear();
        List<Point> points = new ArrayList<>();
        for (double i = 0; i < 1; i += 0.001) {
            points.add(new Point(
                    function(a.getX(), b.getX(), c.getX(), d.getX(), i),
                    function(a.getY(), b.getY(), c.getY(), d.getY(), i)
            ));
        }
        line.addPoints(points);
    }

    public void setA(CurvePoint newA) {
        this.a = newA;
        getChildren().remove(ab);
        ab = new BoundLine(a, b);
        getChildren().add(ab);
        build();
    }

    public void setB(CurvePoint newB) {
        this.b = newB;
        getChildren().remove(ab);
        ab = new BoundLine(a, b);
        getChildren().add(ab);
        build();
    }

    public void setC(CurvePoint newC) {
        this.c = newC;
        getChildren().remove(dc);
        dc = new BoundLine(d, c);
        getChildren().add(dc);
        build();
    }

    public void setD(CurvePoint newD) {
        this.d = newD;
        getChildren().remove(dc);
        dc = new BoundLine(d, c);
        getChildren().add(dc);
        build();
    }

    private double function(double ra, double rb, double rc, double rd, double u) {
        return (ra * a.getWeight() * Math.pow((1 - u), 3)
                + 3 * rb * b.getWeight() * Math.pow((1 - u), 2) * u
                + 3 * rc * c.getWeight() * (1 - u) * Math.pow(u, 2)
                + rd * d.getWeight() * Math.pow(u, 3)) /
                (a.getWeight() * Math.pow((1 - u), 3)
                        + 3 * b.getWeight() * Math.pow((1 - u), 2) * u
                        + 3 * c.getWeight() * (1 - u) * Math.pow(u, 2)
                        + d.getWeight() * Math.pow(u, 3));
    }

    public Set<CurvePoint> getPoints() {
        HashSet<CurvePoint> points = new HashSet<>();
        points.add(a);
        points.add(b);
        points.add(c);
        points.add(d);
        return points;
    }

    public void connect(CurvePoint newP, CurvePoint oldP) {
        if (getPoints().contains(oldP)) {
            if (oldP == a) {
                setA(newP);
            } else if (oldP == b) {
                setB(newP);
            } else if (oldP == c) {
                setC(newP);
            } else if (oldP == d) {
                setD(newP);
            }
            getChildren().remove(oldP);
            newP.addListeners(oldP.getListeners());
            build();
        }
    }

    public CurvePoint getA() {
        return a;
    }

    public CurvePoint getB() {
        return b;
    }

    public CurvePoint getC() {
        return c;
    }

    public CurvePoint getD() {
        return d;
    }

    private class BoundLine extends Line {

        public BoundLine(CurvePoint a, CurvePoint b) {
            super(a, b);
            setStrokeWidth(2);
            setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
            setStrokeLineCap(StrokeLineCap.BUTT);
            getStrokeDashArray().setAll(10.0, 5.0);
            build();
        }
    }

    @Override
    public String toString() {
        return "Curve{" +
                "dc=" + dc +
                ", ab=" + ab +
                ", d=" + d +
                ", c=" + c +
                ", b=" + b +
                ", a=" + a +
                '}';
    }

    private class LocalChangeListener implements DoubleChangeListener, Serializable {
        @Override
        public void changed(double oldValue, double newValue) {
            build();
        }
    }
}
