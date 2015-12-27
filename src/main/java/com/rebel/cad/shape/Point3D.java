package com.rebel.cad.shape;

import com.rebel.cad.util.DoubleProperty;

/**
 * Created by Slava on 27.12.2015.
 */
public class Point3D extends Point {
    private DoubleProperty z = new DoubleProperty();

    public Point3D() {
    }

    public Point3D(double x, double y, double z) {
        super(x, y);
        this.z.setValue(z);
    }

    public double getZ() {
        return z.getValue();
    }

    public DoubleProperty getZProperty() {
        return z;
    }

    public void setZ(double z) {
        this.z.setValue(z);
    }

    @Override
    public String toString() {
        return "Point3D{" +
                "x=" + getX() +
                "y=" + getY() +
                "z=" + z.getValue() +
                '}';
    }
}
