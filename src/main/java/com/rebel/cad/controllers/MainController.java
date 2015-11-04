package com.rebel.cad.controllers;

import com.rebel.cad.MainApp;
import com.rebel.cad.shapes.Dot;
import com.rebel.cad.shapes.HintedDot;
import com.rebel.cad.shapes.Line;
import com.rebel.cad.util.Helper;
import com.rebel.cad.util.Shaper;
import javafx.beans.binding.DoubleExpression;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Group;
import javafx.scene.Node;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.control.Alert;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Polyline;
import javafx.scene.shape.Rectangle;

import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.ResourceBundle;

/**
 * Created by Slava on 04.09.2015.
 */
public class MainController extends Controller implements Initializable {

    private static int dotCount;

    private ArrayList<HintedDot> dotsList = new ArrayList<>();
    private HintedDot rotationPoint;
    @FXML
    private Group figure;


    @FXML
    private VBox rootBox;
    @FXML
    private Pane groupPane;
    @FXML
    private Label xCoord;
    @FXML
    private Label yCoord;
    @FXML
    private TextField stepField;
    @FXML
    private TextField rotateField;

    private Canvas grid;
    private Canvas axes;

    private int step = 20;

    private double width = MainApp.INITIAL_WIDTH;
    private double height = MainApp.INITIAL_HEIGHT;

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        grid = createCanvasGrid(width, height - 40, step);
        axes = createCanvasAxes(width, height - 40);
        groupPane.getChildren().addAll(grid, axes);

        groupPane.setOnMouseMoved(event -> {
            xCoord.setText(Double.toString(toFakeX(event.getX())));
            yCoord.setText(Double.toString(toFakeY(event.getY())));
        });

        rootBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.intValue();
            resize();
        });
        rootBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.intValue();
            resize();
        });

        figure.getChildren().addAll(new Line(toRealX(-50), toRealY(50), toRealX(-50), toRealY(-50), toRealX(50), toRealY(-50), toRealX(50), toRealY(50), toRealX(-50), toRealY(50)));
    }

    private double getWidthCenter() {
        return width / 2 - (width / 2 % step);
    }

    private double getHeightCenter() {
        return height / 2 - (height / 2 % step);
    }

    private Canvas createCanvasGrid(double width, double height, int step) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);
        canvas.setOpacity(0.4);

        double x1;
        for (double x = 0; x < width; x += step) {
            x1 = x + 0.5;
            gc.strokeLine(x1, 0, x1, height);
        }

        double y1;
        for (double y = 0; y < height; y += step) {
            y1 = y + 0.5;
            gc.strokeLine(0, y1, width, y1);
        }

        gc.fillText(Integer.toString(step), getWidthCenter() - 20, getHeightCenter() - step - step / 10);

        return canvas;
    }

    private Canvas createCanvasAxes(double width, double height) {
        Canvas canvas = new Canvas(width, height);
        GraphicsContext gc = canvas.getGraphicsContext2D();
        gc.setLineWidth(1);

        gc.moveTo(getWidthCenter(), 0);
        gc.lineTo(getWidthCenter(), height);
        gc.stroke();

        gc.moveTo(0, getHeightCenter());
        gc.lineTo(width, getHeightCenter());
        gc.stroke();

        gc.fillText("X", width - 20, getHeightCenter() + 20);
        gc.fillText("Y", getWidthCenter() - 20, 20);

        return canvas;
    }

    @FXML
    private void erase() {
        dotsList.add(rotationPoint);
        groupPane.getChildren().removeAll(dotsList);
        dotsList.clear();
        dotCount = 0;
        figure.getChildren().removeAll(figure.getChildren());
        figure.getChildren().clear();
    }

    private double toRealX(double x) {
        return x + getWidthCenter();
    }

    private double toFakeX(double x) {
        return x - getWidthCenter();
    }

    private double toRealY(double y) {
        return -y + getHeightCenter() - 20;
    }

    private double toFakeY(double y) {
        return -(y - getHeightCenter());
    }

    private void drawFigure(List<HintedDot> dots) {
        HintedDot centerDot = dots.get(dotCount - 1);
        double figureHeight = askParam("Enter height");
        double radius = askParam("Enter radius for bottom circles, less than: " + Math.round(figureHeight / 12));
        if (radius > figureHeight / 12) {
            showError("Radius is too big");
            erase();
            return;
        }
        double y = centerDot.getCenterY() + figureHeight / 2 - radius;
        double x = centerDot.getCenterX() - figureHeight / 3;
        Dot dotOnFirstCircle = Helper.getDotOnArc(x, y, radius, 300);
        Dot dotOnLastCircle = Helper.getDotOnArc(x + figureHeight / 1.5, y, radius, 250);
        for (int i = 0; i < 5; i++) {
            figure.getChildren().add(Shaper.drawCirlce(x, y, radius, Shaper.Axis.Both));
            x += figureHeight / 6;
        }

        Dot leftSecond = new Dot(centerDot.getCenterX() - figureHeight / 6 - radius, y);
        Dot rightSecond = new Dot(centerDot.getCenterX() - figureHeight / 6 + radius, y);
        Dot leftThird = new Dot(centerDot.getCenterX() - radius, y);
        Dot rightThird = new Dot(centerDot.getCenterX() + radius, y);
        Dot leftFourth = new Dot(centerDot.getCenterX() + figureHeight / 6 - radius, y);
        Dot rightFourth = new Dot(centerDot.getCenterX() + figureHeight / 6 + radius, y);

        x = centerDot.getCenterX();
        y = centerDot.getCenterY() - figureHeight / 2 + figureHeight / 14;
        figure.getChildren().add(Shaper.drawArc(x, y, figureHeight / 14, 190, 350, Shaper.Axis.Both));
        Dot topStart = Helper.getDotOnArc(x, y, figureHeight / 14, 190);
        Dot topEnd = Helper.getDotOnArc(x, y, figureHeight / 14, 350);

        double radius2 = askParam("Enter radius for central circles");
        y = centerDot.getCenterY();
        x = centerDot.getCenterX() - figureHeight / 6;
        figure.getChildren().add(new Line(x, y, topStart.getX(), topStart.getY()));
        figure.getChildren().add(new Line(x, y, dotOnFirstCircle.getX(), dotOnFirstCircle.getY()));
        figure.getChildren().add(new Line(x, y, leftSecond.getX(), leftSecond.getY()));
        figure.getChildren().add(new Line(x, y, rightSecond.getX(), rightSecond.getY()));
        figure.getChildren().add(Shaper.drawArc(x, y, radius2, 111, 282, Shaper.Axis.Horizontal));

        x = centerDot.getCenterX() + figureHeight / 6;
        figure.getChildren().add(new Line(x, y, topEnd.getX(), topEnd.getY()));
        figure.getChildren().add(new Line(x, y, dotOnLastCircle.getX(), dotOnLastCircle.getY()));
        figure.getChildren().add(new Line(x, y, leftFourth.getX(), leftFourth.getY()));
        figure.getChildren().add(new Line(x, y, rightFourth.getX(), rightFourth.getY()));
        figure.getChildren().add(Shaper.drawArc(x, y, radius2, 258, 68, Shaper.Axis.Horizontal));

        figure.getChildren().add(new Line(centerDot.getCenterX(), centerDot.getCenterY(), leftThird.getX(), leftThird.getY()));
        figure.getChildren().add(new Line(centerDot.getCenterX(), centerDot.getCenterY(), rightThird.getX(), rightThird.getY()));

        Line horAx = new Line(centerDot.getCenterX() - figureHeight / 4, centerDot.getCenterY(), centerDot.getCenterX() + figureHeight / 4, centerDot.getCenterY());
        horAx.getStrokeDashArray().addAll(10d);
        figure.getChildren().add(horAx);
        Line verAx = new Line(centerDot.getCenterX(), centerDot.getCenterY() - figureHeight / 1.8, centerDot.getCenterX(), centerDot.getCenterY() + figureHeight / 1.8);
        verAx.getStrokeDashArray().addAll(10d);
        figure.getChildren().add(verAx);
        figure.setClip(new Rectangle(0, 50, width, height));
    }

    @FXML
    private void draw() {
        showInform("Center point", "Place center point on the plane");
        createDot();
    }

    private void createDot() {
        groupPane.setOnMouseClicked(event -> {
            double fx = toFakeX(event.getX());
            double fy = toFakeY(event.getY());
            HintedDot dot = new HintedDot(event.getX(), event.getY(), dotCount++);
            dot.setTooltip(fx, fy);
            dotsList.add(dot);
            groupPane.getChildren().add(dot);
            drawFigure(dotsList);
            groupPane.setOnMouseClicked(null);
        });
    }

    private double getPointOnLine(HintedDot a, HintedDot b, double x) {
        return (x - a.getCenterX()) * (b.getCenterY() - a.getCenterY()) / (b.getCenterX() - a.getCenterX()) + a.getCenterY();
    }

    @FXML
    private void rebuild() {
        int prevStep = step;
        step = Integer.parseInt(stepField.getText());
        groupPane.getChildren().removeAll(axes, grid);
        grid = createCanvasGrid(width, height, step);
        axes = createCanvasAxes(width, height);
        groupPane.getChildren().add(axes);
        axes.toBack();
        groupPane.getChildren().add(grid);
        grid.toBack();
        figure.setScaleX(step / prevStep);
        figure.setScaleY(step / prevStep);
    }

    private void resize() {
        groupPane.getChildren().removeAll(axes, grid);
        grid = createCanvasGrid(width, height, step);
        axes = createCanvasAxes(width, height);
        groupPane.getChildren().add(axes);
        axes.toBack();
        groupPane.getChildren().add(grid);
        grid.toBack();
    }

    @FXML
    private void rotateWithAxis() {
        groupPane.getChildren().remove(rotationPoint);
        showInform("Set param", "Set rotation point");
        groupPane.setOnMouseClicked(event -> {
            double fx = toFakeX(event.getX());
            double fy = toFakeY(event.getY());
            rotationPoint = new HintedDot(event.getX(), event.getY(), dotCount++);
            rotationPoint.setTooltip(fx, fy);
            groupPane.getChildren().add(rotationPoint);
            double rotate = Double.parseDouble(rotateField.getText());
            rotate(figure, rotate);
            groupPane.setOnMouseClicked(null);
        });
    }

    @FXML
    private void rotate() {
        groupPane.getChildren().remove(rotationPoint);
        rotationPoint = new HintedDot(getWidthCenter(), getHeightCenter(), dotCount++);
        rotate(figure, Double.parseDouble(rotateField.getText()));
    }

    private void rotate(Node node, double degrees) {
        if (node instanceof Line) {
            Line line = (Line) node;
            line.rotate(rotationPoint.getCenterX(), rotationPoint.getCenterY(), degrees);
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (Node child : group.getChildren()) {
                rotate(child, degrees);
            }
        } else {
            System.err.println("Unknonw node type: " + node);
        }
    }

    private void move(Node node, double x, double y) {
        if (node instanceof Line) {
            Line line = (Line) node;
            List<Double> dots = line.getPoints();
            for (int i = 0; i < dots.size(); i += 2) {
                dots.set(i, dots.get(i) + x);
                dots.set(i, dots.get(i + 1) + y);
            }
        } else if (node instanceof Group) {
            Group group = (Group) node;
            for (Node child : group.getChildren()) {
                move(child, x, y);
            }
        } else {
            System.err.println("Unknonw node type: " + node);
        }
    }
}