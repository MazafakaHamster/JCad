package com.rebel.cad.collections;

import com.rebel.cad.shape.Transformable;
import javafx.scene.Group;
import javafx.scene.Node;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

/**
 * Created by Slava on 11.11.2015.
 */
public class ShapeGroup extends Group implements Transformable, Serializable {
    private ArrayList<Node> children = new ArrayList<>();

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

    public void add(Node child) {
        children.add(child);
        getChildren().add(child);
    }


    public void addAll(Node... child) {
        Collections.addAll(children, child);
        getChildren().addAll(child);
    }

    public void addAll(List<? extends Node> child) {
        children.addAll(child);
        getChildren().addAll(child);
    }

    public void clear() {
        children.clear();
        getChildren().clear();
    }

    public void remove(Node child) {
        children.remove(child);
        getChildren().remove(child);
    }

    public List<Node> getChilds() {
        return children;
    }
}
