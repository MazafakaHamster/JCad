package com.rebel.cad.shape;

/**
 * Created by Slava on 07.10.2015.
 */
public class Line extends TPolyline {

    private Point start;
    private Point end;

    public Line(double... points) {
        super(points);
    }

    public Line(double x1, double y1, double x2, double y2, double opacity) {
        super(x1, y1, x2, y2);
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        setOpacity(opacity);
    }

    public Line(Point start, Point end) {
        super(start.getX(), start.getY(), end.getX(), end.getY());
    }
}
