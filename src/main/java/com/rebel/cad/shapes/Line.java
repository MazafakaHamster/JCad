package com.rebel.cad.shapes;

import javafx.scene.shape.Polyline;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Slava on 07.10.2015.
 */
public class Line extends Polyline {

    public Line(double... points) {
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
            getPoints().set(count+1, dot.getY());
            count += 2;
        }
    }

    public List<Dot> getDots() {
        ArrayList<Dot> dots = new ArrayList<>();
        for (int i = 0; i < getPoints().size(); i+=2) {
            dots.add(new Dot(getPoints().get(i), getPoints().get(i+1)));
        }
        return dots;
    }

    public void rotate(double x, double y, double degrees) {
        double radians = Math.toRadians(degrees);
        List<Dot> dots = getDots();
        for (Dot dot : dots) {
            dot.setX(x + (dot.getX() - x) * Math.cos(radians) - (dot.getY() - y) * Math.sin(radians));
            dot.setY(y + (dot.getX() - x) * Math.sin(radians) + (dot.getY() - y) * Math.cos(radians));
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
}
