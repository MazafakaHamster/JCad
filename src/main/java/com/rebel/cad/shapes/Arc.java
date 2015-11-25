package com.rebel.cad.shapes;

import com.rebel.cad.collections.ShapeGroup;

/**
 * Created by Slava on 05.11.2015.
 */
public class Arc extends TPolyline {
    protected double centerX;
    protected double centerY;
    protected double radius;
    private int start;
    private int end;

    protected ShapeGroup axisGroup;

    public Arc(double x, double y, double r, int start, int end) {
        this.start = start;
        this.end = end;
        this.centerX = x;
        this.centerY = y;
        this.radius = r;

        double startRad = Math.toRadians(this.start);

        getPoints().addAll(x + r * Math.cos(startRad), y + r * Math.sin(startRad));
        if (start < end && (end - start) % 5 == 0) {
            for (int i = start; i <= end; i += 5) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
        } else if (start <= end) {
            for (int i = start; i <= end; i += 1) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
        } else {
            for (int i = start; i <= 360; i += 1) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
            for (int i = 0; i <= end; i += 1) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
        }
    }

    public Arc(double x, double y, double r, int start, int end, Axis axis) {
        this(x, y, r, start, end);
        buildAxis(axis, x, y, r);
    }

    protected void buildAxis(Axis axis, double x, double y, double r) {
        axisGroup = new ShapeGroup();
        if (Axis.Both.equals(axis)) {
            Line horAxis = new Line(x - 1.2 * r, y, x + 1.2 * r, y);
            Line vertAxis = new Line(x, y - 1.2 * r, x, y + 1.2 * r);
            axisGroup.getChildren().addAll(horAxis, vertAxis);
        } else if (Axis.Horizontal.equals(axis)) {
            Line horAxis = new Line(x - 1.2 * r, y, x + 1.2 * r, y);
            axisGroup.getChildren().addAll(horAxis);
        } else if (Axis.Vertical.equals(axis)) {
            Line vertAxis = new Line(x, y - 1.2 * r, x, y + 1.2 * r);
            axisGroup.getChildren().addAll(vertAxis);
        }
    }

    @Override
    public void rotate(double x, double y, double degrees) {
        super.rotate(x, y, degrees);
        if (axisGroup != null) {
            axisGroup.rotate(x, y, degrees);
        }
    }

    @Override
    public void move(double x, double y) {
        super.move(x, y);
        if (axisGroup != null) {
            axisGroup.move(x, y);
        }
    }

    public double getCenterX() {
        return centerX;
    }

    public double getCenterY() {
        return centerY;
    }

    public double getRadius() {
        return radius;
    }
}
