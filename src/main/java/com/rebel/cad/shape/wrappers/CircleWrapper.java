package com.rebel.cad.shape.wrappers;

import javafx.scene.paint.Paint;
import javafx.scene.shape.Circle;

import java.io.Serializable;

/**
 * Created by Slava on 22.12.2015.
 */
public class CircleWrapper extends Circle implements Serializable {
    public CircleWrapper(double centerX, double centerY, double radius, Paint fill) {
        super(centerX, centerY, radius, fill);
    }
}
