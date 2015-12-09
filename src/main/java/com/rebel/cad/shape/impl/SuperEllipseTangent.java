package com.rebel.cad.shape.impl;

import com.rebel.cad.controllers.MainController;
import com.rebel.cad.shape.Dot;
import com.rebel.cad.shape.Line;
import com.rebel.cad.shape.SuperEllipse;
import com.rebel.cad.shape.Tangent;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 08.12.2015.
 */
public class SuperEllipseTangent extends Line implements Tangent {
    private double a;
    private double b;
    private double x0;
    private double y0;
    private double x;
    private double y;


    public SuperEllipseTangent(SuperEllipse ellipse, double x0, double y0, double l) {
        this.x = x0;
        this.y = y0;

        List<Dot> dots = new ArrayList<>();
        for (double i = x0-2; i < x0+2; i += 0.4) {
            double x = i;
            double y = ellipse.function(MainController.toFakeX(x0)) + ellipse.derivative(MainController.toFakeX(x0), MainController.toFakeY(y0)) * (MainController.toFakeX(x) - MainController.toFakeX(x0));
            dots.add(new Dot(x, MainController.toRealY(y)));
            System.out.println(MainController.toFakeX(x) + "  " + y);
        }
        addPoints(dots);
    }
}
