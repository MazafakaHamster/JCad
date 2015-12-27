package com.rebel.cad.util;

import com.rebel.cad.shape.Point;

/**
 * Created by Slava on 22.09.2015.
 */
public class Utils {
    public static Point getDotOnArc(double centerX, double centerY, double r, double pos) {
        Point point = new Point();
        double posRad = Math.toRadians(pos);
        point.setX(centerX + r * Math.cos(posRad));
        point.setY(centerY + r * Math.sin(posRad));
        return point;
    }

    public static double[][] multiply(double[][] a, double[][] b) {
        int aRows = a.length;
        int aColumns = a[0].length;
        int bRows = b.length;
        int bColumns = b[0].length;

        if (aColumns != bRows) {
            throw new IllegalArgumentException("A:Rows: " + aColumns + " did not match B:Columns " + bRows + ".");
        }

        double[][] c = new double[aRows][bColumns];
        for (int i = 0; i < c.length; i++) {
            for (int j = 0; j < c[i].length; j++) {
                c[i][j] = 0.00000;
            }
        }

        for (int i = 0; i < aRows; i++) { // aRow
            for (int j = 0; j < bColumns; j++) { // bColumn
                for (int k = 0; k < aColumns; k++) { // aColumn
                    c[i][j] += a[i][k] * b[k][j];
                }
            }
        }

        return c;
    }

    public static double[] multiply(double[][] a, double[] b) {
        double[] res = new double[3];
        for (int i = 0; i < 3; i++) {
            res[i] = 0;
        }
        for (int i = 0; i < 3; i++) {
            for (int j = 0; j < 3; j++) {
                res[i] += b[j] * a[j][i];
            }
        }
        return res;
    }
}
