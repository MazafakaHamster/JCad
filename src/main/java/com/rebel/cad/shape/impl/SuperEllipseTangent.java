package com.rebel.cad.shape.impl;

import com.rebel.cad.shape.Line;
import com.rebel.cad.shape.SuperEllipse;
import com.rebel.cad.shape.Tangent;

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


    public SuperEllipseTangent(SuperEllipse ellipse, double x, double y, double l) {
        this.a = ellipse.getA();
        this.b = ellipse.getB();
        this.x0 = ellipse.getCenterX();
        this.y0 = ellipse.getCenterY();
        this.x = x;
        this.y = y;


    }
}
