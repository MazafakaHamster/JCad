//package com.rebel.cad.util;
//
//import com.rebel.cad.shapes.Line;
//import javafx.scene.Group;
//import javafx.scene.Node;
//import javafx.scene.paint.Color;
//import javafx.scene.shape.Polyline;
//
///**
// * Created by Slava on 05.09.2015.
// */
//public class Shaper {
//    public enum Axis {
//        Horizontal, Vertical, Both, None
//    }
//
//    public static Node drawCirlce(double x, double y, double r, Axis axis) {
//        Line polyline = new Line();
//        polyline.getPoints().addAll(x + r, y);
//        for (int i = 0; i <= 360; i += 6) {
//            polyline.getPoints().addAll(x + r * Math.cos(i * Math.PI / 180), y + r * Math.sin(i * Math.PI / 180));
//        }
//        if (!Axis.None.equals(axis)) {
//            Group group = buildAxis(axis, x, y, r);
//            group.getChildren().addAll(polyline);
//            return group;
//        } else {
//            return polyline;
//        }
//    }
//
//    public static Node drawArc(double x, double y, double r, int start, int end, Axis axis) {
//        Line polyline = new Line();
//        polyline.getPoints().addAll(x + r * Math.cos(start * Math.PI / 180), y + r * Math.sin(start * Math.PI / 180));
//        if (start > end) {
//            for (int i = start; i <= 360; i += 1) {
//                polyline.getPoints().addAll(x + r * Math.cos(i * Math.PI / 180), y + r * Math.sin(i * Math.PI / 180));
//            }
//            for (int i = 0; i <= end; i += 1) {
//                polyline.getPoints().addAll(x + r * Math.cos(i * Math.PI / 180), y + r * Math.sin(i * Math.PI / 180));
//            }
//        } else {
//            for (int i = start; i <= end; i += 1) {
//                polyline.getPoints().addAll(x + r * Math.cos(i * Math.PI / 180), y + r * Math.sin(i * Math.PI / 180));
//            }
//        }
//
//        if (!Axis.None.equals(axis)) {
//            Group group = buildAxis(axis, x, y, r);
//            group.getChildren().addAll(polyline);
//            return group;
//        } else {
//            return polyline;
//        }
//    }
//
//    private static Group buildAxis(Axis axis, double x, double y, double r) {
//        Group group = new Group();
//        if (Axis.Both.equals(axis)) {
//            Line horAxis = new Line(x - 1.2 * r, y, x + 1.2 * r, y);
//            Line vertAxis = new Line(x, y - 1.2 * r, x, y + 1.2 * r);
//            group.getChildren().addAll(horAxis, vertAxis);
//        } else if (Axis.Horizontal.equals(axis)) {
//            Line horAxis = new Line(x - 1.2 * r, y, x + 1.2 * r, y);
//            group.getChildren().addAll(horAxis);
//        } else if (Axis.Vertical.equals(axis)) {
//            Line vertAxis = new Line(x, y - 1.2 * r, x, y + 1.2 * r);
//            group.getChildren().addAll(vertAxis);
//        }
//        return group;
//    }
//}
