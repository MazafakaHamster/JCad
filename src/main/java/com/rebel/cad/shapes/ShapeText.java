package com.rebel.cad.shapes;

import javafx.scene.text.Text;

/**
 * Created by Slava on 18.11.2015.
 */
public class ShapeText extends Text {

    public ShapeText(double x, double y, String text) {
        super(x, y, text);
    }

    public void rotate(double x, double y, double degrees) {
        double radians = Math.toRadians(degrees);
        double x0 = getX();
        double y0 = getY();
        setX(x + (x0 - x) * Math.cos(radians) - (y0 - y) * Math.sin(radians));
        setY(y + (x0 - x) * Math.sin(radians) + (y0 - y) * Math.cos(radians));
        setRotate(getRotate() + degrees);
    }

    public void move(double x, double y) {
        setX(getX() + x);
        setY(getY() + y);
    }


    public void project(double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w) {
        double x0 = getX();
        double y0 = getY();

        setX((x * w + xx * wx * x0 + xy * wy * y0) / (w + wx * x0 + wy * y0));
        setY((y * w + yx * wx * x0 + yy * wy * y0) / (w + wx * x0 + wy * y0));
    }

    public void afinnis(double xx, double xy, double yx, double yy, double dx, double dy) {
        double x = getX();
        double y = getY();

        setX(x * xx + y * xy + x);
        setY(x * yx + y * yy + y);
    }
}
