package com.rebel.cad.collections;

import com.rebel.cad.shape.Transformable;
import javafx.scene.Group;
import javafx.scene.Node;

/**
 * Created by Slava on 11.11.2015.
 */
public class ShapeGroup extends Group implements Transformable {

    public void rotate(double x, double y, double degrees) {
        for (Node node : getChildren()) {
            Transformable transformable = (Transformable) node;
            transformable.rotate(x, y, degrees);
        }
    }

    public void move(double x, double y) {
        for (Node node : getChildren()) {
            Transformable transformable = (Transformable) node;
            transformable.move(x, y);
        }
    }

    public void project(double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w) {
        for (Node node : getChildren()) {
            Transformable transformable = (Transformable) node;
            transformable.project(xx, xy, wx, yx, yy, wy, x, y, w);
        }
    }

    public void affinis(double xx, double xy, double yx, double yy, double dx, double dy) {
        for (Node node : getChildren()) {
            Transformable transformable = (Transformable) node;
            transformable.affinis(xx, xy, yx, yy, dx, dy);
        }
    }
}
