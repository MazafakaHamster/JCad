package com.rebel.cad.shape;

import javafx.beans.property.DoubleProperty;
import javafx.scene.paint.Color;
import javafx.scene.shape.StrokeLineCap;

/**
 * Created by Slava on 16.12.2015.
 */
public class BoundLine extends javafx.scene.shape.Line {
    public BoundLine(DoubleProperty startX, DoubleProperty startY, DoubleProperty endX, DoubleProperty endY) {
        startXProperty().bind(startX);
        startYProperty().bind(startY);
        endXProperty().bind(endX);
        endYProperty().bind(endY);
        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
    }

    public BoundLine(CurvePoint a, CurvePoint b) {
        startXProperty().bind(a.getXProperty());
        startYProperty().bind(a.getYProperty());
        endXProperty().bind(b.getXProperty());
        endYProperty().bind(b.getYProperty());
        setStrokeWidth(2);
        setStroke(Color.GRAY.deriveColor(0, 1, 1, 0.5));
        setStrokeLineCap(StrokeLineCap.BUTT);
        getStrokeDashArray().setAll(10.0, 5.0);
    }
}
