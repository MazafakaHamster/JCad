package com.rebel.cad.shape;

import com.rebel.cad.shape.wrappers.PolylineWrapper;
import com.rebel.cad.util.DoubleChangeListener;
import com.rebel.cad.util.DoubleProperty;
import javafx.scene.paint.Color;

import java.io.Serializable;
import java.util.List;

/**
 * Created by Slava on 07.10.2015.
 */
public class Line3D extends PolylineWrapper {

    private Point start = new Point();
    private Point end = new Point();
    private LocalChangeListener listener = new LocalChangeListener();

    public Line3D(double... points) {
        super(points);
    }

    public Line3D(List<Point> points) {
        super();
        addPoints(points);
    }

    public Line3D(double x1, double y1, double x2, double y2, double opacity) {
        this.start = new Point(x1, y1);
        this.end = new Point(x2, y2);
        start.getXProperty().addListener(listener);
        start.getYProperty().addListener(listener);
        end.getXProperty().addListener(listener);
        end.getYProperty().addListener(listener);
        setOpacity(opacity);
        build();
    }

    public Line3D(CurvePoint a, CurvePoint b) {
        this.start = new Point(a.getX(), a.getY());
        this.end = new Point(b.getX(), b.getY());
        a.getXProperty().bind(startXProperty());
        a.getYProperty().bind(startYProperty());
        b.getXProperty().bind(endXProperty());
        b.getYProperty().bind(endYProperty());
        start.getXProperty().addListener(listener);
        start.getYProperty().addListener(listener);
        end.getXProperty().addListener(listener);
        end.getYProperty().addListener(listener);
        build();
    }

    public DoubleProperty startXProperty() {
        return start.getXProperty();
    }
    public DoubleProperty startYProperty() {
        return start.getYProperty();
    }
    public DoubleProperty endXProperty() {
        return end.getXProperty();
    }
    public DoubleProperty endYProperty() {
        return end.getYProperty();
    }

    public Line3D(Point start, Point end) {
        super(start.getX(), start.getY(), end.getX(), end.getY());
    }

    public Line3D(Point start, Point end, Color color) {
        super(start.getX(), start.getY(), end.getX(), end.getY());
        setStroke(color);
    }

    private void build() {
        clear();
        addPoints(start, end);
    }

    private class LocalChangeListener implements DoubleChangeListener, Serializable {
        @Override
        public void changed(double oldValue, double newValue) {
            build();
        }
    }
}
