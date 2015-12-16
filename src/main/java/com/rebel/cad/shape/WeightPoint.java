package com.rebel.cad.shape;

import com.rebel.cad.controllers.MainController;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Slava on 15.12.2015.
 */
public class WeightPoint extends Point {
    private double weight;
    private javafx.scene.shape.Circle circle;

    public WeightPoint(double x, double y, double weight) {
        super(x, y);
        this.weight = weight;
        circle = new Circle(x, y, 7, Color.BLUE);
        circle.setOpacity(0.6);
        MainController.staticFigure.getChildren().add(circle);
    }

    protected Circle getCircle() {
        return circle;
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
    }
}
