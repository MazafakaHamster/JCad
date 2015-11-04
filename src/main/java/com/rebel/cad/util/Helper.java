package com.rebel.cad.util;

import com.rebel.cad.shapes.Dot;

import java.util.Arrays;

/**
 * Created by Slava on 22.09.2015.
 */
public class Helper {
    public static Dot getDotOnArc(double centerX, double centerY, double r, double pos) {
        Dot dot = new Dot();
        dot.setX(centerX + r * Math.cos(pos * Math.PI / 180));
        dot.setY(centerY + r * Math.sin(pos * Math.PI / 180));
        return dot;
    }

    public static double[] rotate(double x, double y, double degrees, double px, double py) {
        x -= px;
        y -= py;
        x = x * Math.cos(degrees) - y * Math.sin(degrees);
        y = x * Math.sin(degrees) + y * Math.cos(degrees);
        x += px;
        y += py;
        return new double[] {x, y};
    }
}
