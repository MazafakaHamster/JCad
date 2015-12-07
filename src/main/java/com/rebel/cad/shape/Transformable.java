package com.rebel.cad.shape;

/**
 * Created by Slava on 19.11.2015.
 */
public interface Transformable {
    void rotate(double x, double y, double degrees);

    void move(double x, double y);

    void project(double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w);

    void affinis(double xx, double xy, double yx, double yy, double dx, double dy);
}