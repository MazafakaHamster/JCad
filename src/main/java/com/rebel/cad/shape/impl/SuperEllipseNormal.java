package com.rebel.cad.shape.impl;

import com.rebel.cad.controllers.MainController;
import com.rebel.cad.shape.Dot;
import com.rebel.cad.shape.Line;
import com.rebel.cad.shape.Normal;
import com.rebel.cad.shape.SuperEllipse;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 08.12.2015.
 */
public class SuperEllipseNormal extends Line implements Normal {
    public SuperEllipseNormal(SuperEllipse ellipse, double x0, double y0) {

        List<Dot> dots = new ArrayList<>();
        for (double x = x0 - 50; x < x0 + 50; x += 0.5) {
            double y;
            if (MainController.toRealY(y0) < 0) {
                y = MainController.toRealY(-ellipse.function(x0)) - (x - x0) / ellipse.derivative(x0, y0);
            } else {
                y = MainController.toRealY(ellipse.function(x0)) - (x - x0) / ellipse.derivative(x0, y0);
            }
            dots.add(new Dot(x, y));
        }
        addPoints(dots);
    }
}
