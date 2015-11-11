package com.rebel.cad.util;

import com.rebel.cad.shapes.Dot;

import java.util.Arrays;

/**
 * Created by Slava on 22.09.2015.
 */
public class Helper {
    public static Dot getDotOnArc(double centerX, double centerY, double r, double pos) {
        Dot dot = new Dot();
        double posRad = Math.toRadians(pos);
        dot.setX(centerX + r * Math.cos(posRad));
        dot.setY(centerY + r * Math.sin(posRad));
        return dot;
    }
}
