package com.rebel.cad.shape;

/**
 * Created by Slava on 27.12.2015.
 */
public class TorusHelper {
    public static double getX(double a, double b, double u, double v) {
        return Math.cos(v) * (a + Math.cos(u) * b);
//        return (R1 + R2 * Math.cos(u)) * Math.abs(Math.cos(v));

    }

    public static double getY(double a, double b, double u, double v) {
        return Math.sin(v) * (a + Math.cos(u) * b);
//        return (R1 + R2 * Math.cos(u)) * Math.abs(Math.sin(v));
    }

    public static double getZ(double b, double u) {
        return b * Math.sin(u);
    }
}
