package com.rebel.cad.shape;

import com.rebel.cad.controllers.MainController;
import javafx.scene.text.Text;

import java.io.Serializable;

/**
 * Created by Slava on 18.11.2015.
 */

public class TText extends Text implements Transformable, Serializable {

    public TText(double x, double y, String text) {
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
        double x0 = MainController.toFakeX(getX());
        double y0 = MainController.toFakeY(getY());

        double W = w + wx * x0 + wy * y0;
        setX(MainController.toRealX((x0 + (x * w + xx * wx * x0 + xy * wy * y0) / W)));
        setY(MainController.toRealY((y0 + (y * w + yx * wx * x0 + yy * wy * y0) / W)));
    }

    public void affinis(double xx, double xy, double yx, double yy, double dx, double dy) {
        double x = MainController.toFakeX(getX());
        double y = MainController.toFakeY(getY());

        setX(MainController.toRealX(x * xx + y * xy + x + dx));
        setY(MainController.toRealY(x * yx + y * yy + y + dy));
    }
}
