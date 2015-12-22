package com.rebel.cad.shape;

import com.rebel.cad.util.DoubleProperty;

/**
 * Created by Slava on 15.12.2015.
 */
public class WeightPoint extends Point {
    private DoubleProperty weight = new DoubleProperty();

    public WeightPoint(double x, double y, double weight) {
        super(x, y);
        this.weight.setValue(weight);
    }

    public double getWeight() {
        return weight.getValue();
    }

    public DoubleProperty getWeightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.setValue(weight);
    }

    @Override
    public String toString() {
        return "WeightPoint{" +
                super.toString() +
                "weight=" + weight +
                '}';
    }
}
