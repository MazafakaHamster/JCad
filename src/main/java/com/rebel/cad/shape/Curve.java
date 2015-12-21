package com.rebel.cad.shape;

import com.rebel.cad.collections.ShapeGroup;
import javafx.beans.value.ChangeListener;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Slava on 15.12.2015.
 */
public class Curve extends ShapeGroup {
    private CurvePoint a;
    private CurvePoint b;
    private CurvePoint c;
    private CurvePoint d;
    private Line line;

    public Curve(CurvePoint a, CurvePoint b, CurvePoint c, CurvePoint d) {
        this.a = a;
        this.b = b;
        this.c = c;
        this.d = d;
        this.line = new Line();

        ChangeListener<Number> listener = (observable, oldValue, newValue) -> {
            line.getPoints().clear();
            build();
        };

        a.addListener(listener);
        b.addListener(listener);
        c.addListener(listener);
        d.addListener(listener);
        build();
        getChildren().addAll(a, b, c, d, line);
    }

    private void build() {
        List<Point> points = new ArrayList<>();
        for (double i = 0; i < 1; i += 0.001) {
            points.add(new Point(
                    function(a.getX(), b.getX(), c.getX(), d.getX(), i),
                    function(a.getY(), b.getY(), c.getY(), d.getY(), i)
            ));
        }
        line.addPoints(points);
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
}
