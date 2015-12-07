package com.rebel.cad.shape;

/**
 * Created by Slava on 07.10.2015.
 */
public class Line extends TPolyline {

    public Line(double... points) {
        super(points);
    }

    public Line(double x1, double y1, double x2, double y2, double opacity) {
        super(x1, y1, x2, y2);
        setOpacity(opacity);
    }
}
