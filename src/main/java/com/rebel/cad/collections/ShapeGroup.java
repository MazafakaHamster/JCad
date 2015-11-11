package com.rebel.cad.collections;

import com.rebel.cad.shapes.Shape;
import javafx.scene.Group;
import javafx.scene.Node;

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
            throw new IllegalArgumentException("Unknonw node type: " + node);
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
            throw new IllegalArgumentException("Unknonw node type: " + node);
        }
    }
}
