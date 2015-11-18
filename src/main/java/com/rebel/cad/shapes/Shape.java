package com.rebel.cad.shapes;

import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 05.11.2015.
 */
public class Shape extends Polyline {

    public Shape(double... points) {
        super(points);
    }

    public void addPoints(Dot... dots) {
        for (Dot dot : dots) {
            getPoints().addAll(dot.getX(), dot.getY());
        }
    }

    public void setDots(Dot... dots) {
        int count = 0;
        for (Dot dot : dots) {
            getPoints().set(count++, dot.getX());
            getPoints().set(count++, dot.getY());
        }
    }

    public void setDots(List<Dot> dots) {
        int count = 0;
        for (Dot dot : dots) {
            getPoints().set(count, dot.getX());
            getPoints().set(count + 1, dot.getY());
            count += 2;
        }
    }

    public List<Dot> getDots() {
        ArrayList<Dot> dots = new ArrayList<>();
        for (int i = 0; i < getPoints().size(); i += 2) {
            dots.add(new Dot(getPoints().get(i), getPoints().get(i + 1)));
        }
        return dots;
    }

    public void rotate(double x, double y, double degrees) {
        double radians = Math.toRadians(degrees);
        List<Dot> dots = getDots();
        for (Dot dot : dots) {
            double x1 = x + (dot.getX() - x) * Math.cos(radians) - (dot.getY() - y) * Math.sin(radians);
            double y1 = y + (dot.getX() - x) * Math.sin(radians) + (dot.getY() - y) * Math.cos(radians);
            dot.setX(x1);
            dot.setY(y1);
        }
        setDots(dots);
    }

    public void move(double x, double y) {
        List<Dot> dots = getDots();
        for (Dot dot : dots) {
            dot.setX(dot.getX() + x);
            dot.setY(dot.getY() + y);
        }
        setDots(dots);
    }


    public void project(double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w) {
        List<Dot> dots = getDots();
        for (Dot dot : dots) {
            double x0 = dot.getX();
            double y0 = dot.getY();

            dot.setX((x * w + xx * wx * x0 + xy * wy * y0) / (w + wx * x0 + wy * y0));
            dot.setY((y * w + yx * wx * x0 + yy * wy * y0) / (w + wx * x0 + wy * y0));
        }
        setDots(dots);
    }


    public void afinnis(double xx, double xy, double yx, double yy, double dx, double dy) {
        List<Dot> dots = getDots();
        for (Dot dot : dots) {

            double x = dot.getX();
            double y = dot.getY();

            dot.setX(x * xx + y * xy + x);
            dot.setY(x * yx + y * yy + y);

        }
        setDots(dots);
    }
}
