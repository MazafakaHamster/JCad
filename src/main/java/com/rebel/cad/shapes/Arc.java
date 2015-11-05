package com.rebel.cad.shapes;

/**
 * Created by Slava on 05.11.2015.
 */
public class Arc extends Shape {

    public Arc(double x, double y, double r, int start, int end) {
        double startRad = Math.toRadians(start);
        getPoints().addAll(x + r * Math.cos(startRad), y + r * Math.sin(startRad));
        if (start > end) {
            for (int i = start; i <= 360; i += 1) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
            for (int i = 0; i <= end; i += 1) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
        } else {
            for (int i = start; i <= end; i += 1) {
                double iRad = Math.toRadians(i);
                getPoints().addAll(x + r * Math.cos(iRad), y + r * Math.sin(iRad));
            }
        }
    }
}
