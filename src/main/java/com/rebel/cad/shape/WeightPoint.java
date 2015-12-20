package com.rebel.cad.shape;

import com.rebel.cad.util.DoubleProp;
import javafx.beans.property.DoubleProperty;

/**
 * Created by Slava on 15.12.2015.
 */
public class WeightPoint extends Point {
    private DoubleProp weight = new DoubleProp();

    public WeightPoint(double x, double y, double weight) {
        super(x, y);
        this.weight.set(weight);
    }

    public double getWeight() {
        return weight.doubleValue();
    }

    public DoubleProperty getWeightProperty() {
        return weight;
    }

    public void setWeight(double weight) {
        this.weight.set(weight);
    }

    @Override
    public String toString() {
        return "WeightPoint{" +
                super.toString() +
                "weight=" + weight +
                '}';
    }
}
