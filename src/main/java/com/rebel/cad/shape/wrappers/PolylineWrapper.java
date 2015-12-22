package com.rebel.cad.shape.wrappers;

import com.rebel.cad.controllers.MainController;
import com.rebel.cad.shape.Point;
import com.rebel.cad.shape.Transformable;
import javafx.scene.shape.Polyline;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 05.11.2015.
 */
public class PolylineWrapper extends Polyline implements Transformable, Serializable {

    public PolylineWrapper(double... points) {
        super(points);
    }

    public List<Point> getDots() {
        ArrayList<Point> points = new ArrayList<>();
        for (int i = 0; i < getPoints().size(); i += 2) {
            points.add(new Point(getPoints().get(i), getPoints().get(i + 1)));
        }
        return points;
    }

    public void clear() {
        getPoints().clear();
    }

    public void addPoints(List<Point> points) {
        for (Point point : points) {
            getPoints().add(point.getX());
            getPoints().add(point.getY());
        }
    }

    public void addPoints(Point... points) {
        for (Point point : points) {
            getPoints().add(point.getX());
            getPoints().add(point.getY());
        }
    }

    public void setPoints(List<Point> points) {
        int count = 0;
        for (Point point : points) {
            getPoints().set(count, point.getX());
            getPoints().set(count + 1, point.getY());
            count += 2;
        }
    }

    public void rotate(double x, double y, double degrees) {
        double radians = Math.toRadians(degrees);
        List<Point> points = getDots();
        for (Point point : points) {
            double x1 = x + (point.getX() - x) * Math.cos(radians) - (point.getY() - y) * Math.sin(radians);
            double y1 = y + (point.getX() - x) * Math.sin(radians) + (point.getY() - y) * Math.cos(radians);
            point.setX(x1);
            point.setY(y1);
        }
        setPoints(points);
    }

    public void move(double x, double y) {
        List<Point> points = getDots();
        for (Point point : points) {
            point.setX(point.getX() + x);
            point.setY(point.getY() + y);
        }
        setPoints(points);
    }


    public void project(double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w) {
        List<Point> points = getDots();
        for (Point point : points) {
            double x0 = MainController.toFakeX(point.getX());
            double y0 = MainController.toFakeY(point.getY());

            double W = w + wx * x0 + wy * y0;

            point.setX(MainController.toRealX((x * w + xx * wx * x0 + xy * wy * y0) / W));
            point.setY(MainController.toRealY((y * w + yx * wx * x0 + yy * wy * y0) / W));
        }
        setPoints(points);
        System.out.println(this);
    }


    public void affinis(double xx, double xy, double yx, double yy, double dx, double dy) {
        List<Point> points = getDots();
        for (Point point : points) {

            double x = MainController.toFakeX(point.getX());
            double y = MainController.toFakeY(point.getY());

            point.setX(MainController.toRealX(x * xx + y * xy + x + dx));
            point.setY(MainController.toRealY(x * yx + y * yy + y + dy));
        }
        setPoints(points);
    }
}
