package com.rebel.cad.shapes;

import javafx.scene.control.Tooltip;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;

/**
 * Created by Slava on 08.09.2015.
 */
public class HintedDot extends Circle {
    private int num;
    private Tooltip tooltip;

    public HintedDot(double x, double y, int num) {
        super(x, y, 2, Color.BLACK);
        this.num = num;
        setRadius(2);
        setFill(Color.BLACK);
        tooltip = new Tooltip();
        Tooltip.install(this, tooltip);
    }

    public void setTooltip(double x, double y) {
        tooltip.setText(num + ": " + x + "; " + y);
    }

    public int getNum() {
        return num;
    }

    public double distance(HintedDot dot) {
        return Math.sqrt((this.getCenterX() - dot.getCenterX()) * (this.getCenterX() - dot.getCenterX())
                + (this.getCenterY() - dot.getCenterY()) * (this.getCenterY() - dot.getCenterY()));
    }
}
