package com.rebel.cad.shape;

/**
 * Created by Slava on 27.12.2015.
 */
public class TorusHelper {
    public static double getX(double R1, double R2, double u, double v) {
        return (R1 + R2 * Math.cos(u)) * Math.abs(Math.cos(v));

    }

    public static double getY(double R1, double R2, double u, double v) {
        return (R1 + R2 * Math.cos(u)) * Math.abs(Math.sin(v));
    }

    public static double getZ(double R2, double u) {
        return R2 * Math.sin(u);
    }
}
