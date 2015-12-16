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
        circle = new Circle(x, y, 3, Color.BLUE);
        circle.setOnMouseClicked(event -> {
            circle.setFill(Color.RED);
            setX(getX() + 5);
            setY(getY() + 5);
        });
        MainController.staticFigure.getChildren().add(circle);
    }

    public double getWeight() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight = weight;
        if (listener != null)
            listener.valueChanged(this);
    }
}
