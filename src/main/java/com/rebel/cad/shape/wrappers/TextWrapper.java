package com.rebel.cad.shape.wrappers;

import com.rebel.cad.controllers.MainController2D;
import com.rebel.cad.shape.Transformable;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Created by Slava on 18.11.2015.
 */

public class TextWrapper extends Text implements Transformable, Serializable {

    public TextWrapper(double x, double y, String text) {
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
        double x0 = MainController2D.toFakeX(getX());
        double y0 = MainController2D.toFakeY(getY());

        double W = w + wx * x0 + wy * y0;
        setX(MainController2D.toRealX((x0 + (x * w + xx * wx * x0 + xy * wy * y0) / W)));
        setY(MainController2D.toRealY((y0 + (y * w + yx * wx * x0 + yy * wy * y0) / W)));
    }

    public void affinis(double xx, double xy, double yx, double yy, double dx, double dy) {
        double x = MainController2D.toFakeX(getX());
        double y = MainController2D.toFakeY(getY());

        setX(MainController2D.toRealX(x * xx + y * xy + x + dx));
        setY(MainController2D.toRealY(x * yx + y * yy + y + dy));
    }
}
