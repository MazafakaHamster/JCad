package com.rebel.cad.shape.impl;

import com.rebel.cad.controllers.MainController2D;
import com.rebel.cad.shape.Point;
import com.rebel.cad.shape.Line;
import com.rebel.cad.shape.SuperEllipse;
import com.rebel.cad.shape.Tangent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 08.12.2015.
 */
public class SuperEllipseTangent extends Line implements Tangent {

    public SuperEllipseTangent(SuperEllipse ellipse, double x0, double y0) {

        List<Point> points = new ArrayList<>();
        for (double x = x0 - 50; x < x0 + 50; x += 0.5) {
            double y;
            if (MainController2D.toRealY(y0) < 0) {
                y = MainController2D.toRealY(-ellipse.function(x0)) + ellipse.derivative(x0, y0) * (x - x0);
            } else {
                y = MainController2D.toRealY(ellipse.function(x0)) + ellipse.derivative(x0, y0) * (x - x0);
            }
            points.add(new Point(x, y));
        }
        addPoints(points);
    }
}
