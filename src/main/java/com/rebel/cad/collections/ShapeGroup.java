package com.rebel.cad.collections;

import com.rebel.cad.shapes.Shape;
import com.rebel.cad.shapes.ShapeText;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.text.Text;

/**
 * Created by Slava on 11.11.2015.
 */
public class ShapeGroup extends Group {

    public void rotate(double x, double y, double degrees) {
        rotate(this, x, y, degrees);
    }

    private void rotate(Node node, double x, double y, double degrees) {
        if (node instanceof Shape) {
            Shape shape = (Shape) node;
            shape.rotate(x, y, degrees);
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (Node child : group.getChildren()) {
                rotate(child, x, y, degrees);
            }
        } else {
            if (node instanceof ShapeText) {
                ShapeText text = (ShapeText) node;
                text.rotate(x, y, degrees);
                return;
            }
            System.err.println("Unknonw node type: " + node);
        }
    }

    public void move(double x, double y) {
        move(this, x, y);
    }

    private void move(Node node, double x, double y) {
        if (node instanceof Shape) {
            Shape shape = (Shape) node;
            shape.move(x, y);
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (Node child : group.getChildren()) {
                move(child, x, y);
            }
        } else {
            if (node instanceof ShapeText) {
                ShapeText text = (ShapeText) node;
                text.move(x, y);
                return;
            }
            System.err.println("Unknonw node type: " + node);
        }
    }

    public void project(double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w) {
        project(this, xx, xy, wx, yx, yy, wy, x, y, w);
    }

    private void project(Node node, double xx, double xy, double wx, double yx, double yy, double wy, double x, double y, double w) {
        if (node instanceof Shape) {
            Shape shape = (Shape) node;
            shape.project(xx, xy, wx, yx, yy, wy, x, y, w);
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (Node child : group.getChildren()) {
                project(child, xx, xy, wx, yx, yy, wy, x, y, w);
            }
        } else {
            if (node instanceof ShapeText) {
                ShapeText text = (ShapeText) node;
                text.project(xx, xy, wx, yx, yy, wy, x, y, w);
                return;
            }
            System.err.println("Unknonw node type: " + node);
        }
    }

    public void afinnis(double xx, double xy, double yx, double yy, double dx, double dy) {
        afinnis(this, xx, xy, yx, yy, dx, dy);
    }

    private void afinnis(Node node, double xx, double xy, double yx, double yy, double dx, double dy) {
        if (node instanceof Shape) {
            Shape shape = (Shape) node;
            shape.afinnis(xx, xy, yx, yy, dx, dy);
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (Node child : group.getChildren()) {
                afinnis(child, xx, xy, yx, yy, dx, dy);
            }
        } else {
            if (node instanceof ShapeText) {
                ShapeText text = (ShapeText) node;
                text.afinnis(xx, xy, yx, yy, dx, dy);
                return;
            }
            System.err.println("Unknonw node type: " + node);
        }
    }
}
