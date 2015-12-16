package com.rebel.cad.util;

import com.rebel.cad.shape.Point;

/**
 * Created by Slava on 22.09.2015.
 */
public class Helper {
    public static Point getDotOnArc(double centerX, double centerY, double r, double pos) {
        Point point = new Point();
        double posRad = Math.toRadians(pos);
        point.setX(centerX + r * Math.cos(posRad));
        point.setY(centerY + r * Math.sin(posRad));
        return point;
    }
}
