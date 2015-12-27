package com.rebel.cad.controllers;

import com.rebel.cad.MainApp3D;
import com.rebel.cad.collections.ShapeGroup;
import com.rebel.cad.shape.*;
import com.rebel.cad.shape.wrappers.TextWrapper;
import com.rebel.cad.util.Utils;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import sun.plugin2.util.ColorUtil;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController3D extends Controller implements Initializable {

    private static int dotCount;
    private static double width = MainApp3D.INITIAL_WIDTH;
    private static double height = MainApp3D.INITIAL_HEIGHT;
    private ArrayList<HintedDot> dotsList = new ArrayList<>();
    private HintedDot rotationPoint;

    public static ShapeGroup staticFigure;

    @FXML
    private ShapeGroup drawing;
    @FXML
    private BorderPane rootPane;
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
    @FXML
    private TextField deltaX;
    @FXML
    private TextField deltaY;
    @FXML
    private TextField alphaText;
    @FXML
    private TextField betaText;
    @FXML
    private TextField gammaText;
    @FXML
    private Label xyAngle;
    @FXML
    private Label xzAngle;
    @FXML
    private Label yzAngle;


    private ShapeGroup axises;

    public static double toRealX(double x) {
        return x + width / 2 + 50;
    }

    public static double toFakeX(double x) {
        return x - width / 2 - 50;
    }

    public static double toRealY(double y) {
        return -y + height / 2 + 100;
    }

    public static double toFakeY(double y) {
        return -(y - height / 2 - 100);
    }

    public static ShapeGroup getFigure() {
        return staticFigure;
    }

    public static ArrayList<Curve> curves = new ArrayList<>();

    private Double alpha = Math.toRadians(0);
    private Double beta = Math.toRadians(83);
    private Double gamma = Math.toRadians(83);

    private List<Point3D> points3D = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        axises = createAxis(400);

        groupPane.getChildren().addAll(axises);

        groupPane.setOnMouseMoved(event -> {
            xCoord.setText(Double.toString(toFakeX(event.getX())));
            yCoord.setText(Double.toString(toFakeY(event.getY())));
        });

        rootPane.setPrefWidth(800);
        rootPane.setPrefHeight(600);

        rootPane.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.doubleValue();
            double delta = width - oldValue.doubleValue();
            if (oldValue.intValue() != 0)
                drawing.move(delta / 2, 0);
            resize();
        });
        rootPane.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.doubleValue();
            double delta = height - oldValue.doubleValue();
            if (oldValue.intValue() != 0)
                drawing.move(0, delta / 2);
            resize();
        });
        staticFigure = drawing;

        alphaText.setText(Long.toString(Math.round(Math.toDegrees(alpha))));
        betaText.setText(Long.toString(Math.round(Math.toDegrees(beta))));
        gammaText.setText(Long.toString(Math.round(Math.toDegrees(gamma))));
    }

    private Line xAxis;
    private Line yAxis;
    private Line zAxis;

    private ShapeGroup createAxis(double length) {
        ShapeGroup axis = new ShapeGroup();

        xAxis = new Line(pointR(0, 0, 0), pointR(length, 0, 0), Color.RED);
        axis.getChildren().add(xAxis);
        yAxis = new Line(pointR(0, 0, 0), pointR(0, length, 0), Color.GREEN);
        axis.getChildren().add(yAxis);
        zAxis = new Line(pointR(0, 0, 0), pointR(0, 0, length), Color.BLUE);
        axis.getChildren().add(zAxis);

        axis.getChildren().add(new TextWrapper(pointR(length - 50, 30, 0), "X"));
        axis.getChildren().add(new TextWrapper(pointR(0, length - 50, 20), "Y"));
        axis.getChildren().add(new TextWrapper(pointR(0, 20, length - 50), "Z"));

        return axis;
    }

    private Point pointR(double x, double y, double z) {
        double[][] projectionMatrix = Utils.multiply(
                Utils.multiply(
                        new double[][]{
                                {1, 0, 0},
                                {0, Math.cos(beta), -Math.sin(beta)},
                                {0, Math.sin(beta), Math.cos(beta)}
                        },
                        new double[][]{
                                {Math.cos(alpha), 0, Math.sin(alpha)},
                                {0, 1, 0},
                                {-Math.sin(alpha), 0, Math.cos(alpha)}
                        }),

                new double[][]{
                        {Math.cos(gamma), -Math.sin(gamma), 0},
                        {Math.sin(gamma), Math.cos(gamma), 0},
                        {0, 0, 1}
                });

        double[][] resultMatrix = Utils.multiply(
                new double[][]{
                        {1, 0, 0},
                        {0, 1, 0},
                        {0, 0, 1}},

                Utils.multiply(projectionMatrix,
                        new double[][]{
                                {x},
                                {y},
                                {z},
                        })
        );
        return new Point(toRealX(resultMatrix[0][0]), toRealY(-resultMatrix[1][0]));
    }

    private void draw() {
        Point a1 = pointR(0, 0, 0);
        Point a2 = pointR(200, 0, 0);
        Point a3 = pointR(200, 200, 0);
        Point a4 = pointR(0, 200, 0);

        Point a5 = pointR(0, 0, 200);
        Point a6 = pointR(200, 0, 200);
        Point a7 = pointR(200, 200, 200);
        Point a8 = pointR(0, 200, 200);


        drawing.getChildren().addAll(
                new Line(a1, a2),
                new Line(a2, a3),
                new Line(a3, a4),
                new Line(a4, a1),

                new Line(a1, a5),
                new Line(a2, a6),
                new Line(a3, a7),
                new Line(a4, a8),

                new Line(a5, a6),
                new Line(a6, a7),
                new Line(a7, a8),
                new Line(a8, a5)
        );
    }

    @FXML
    private void erase() {
        groupPane.getChildren().remove(axises);
        drawing.getChildren().removeAll(drawing.getChildren());
        axises = createAxis(400);
        groupPane.getChildren().add(axises);
        drawing.getChildren().clear();
        curves.clear();
    }


    private void clip() {
        drawing.setClip(new Rectangle(0, 0, width - 180, height));
        axises.setClip(new Rectangle(0, 0, width - 180, height));

    }

    private void resize() {
        groupPane.getChildren().removeAll(axises);
        axises = createAxis(400);
        groupPane.getChildren().add(axises);
        axises.toBack();
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
            double degrees = Double.parseDouble(rotateField.getText());
            drawing.rotate(rotationPoint.getCenterX(), rotationPoint.getCenterY(), degrees);
            groupPane.setOnMouseClicked(null);
        });
    }

    @FXML
    private void rotate() {
        groupPane.getChildren().remove(rotationPoint);
        rotationPoint = new HintedDot(toRealX(0), toRealY(0), dotCount++);
        double degrees = Double.parseDouble(rotateField.getText());
        drawing.rotate(rotationPoint.getCenterX(), rotationPoint.getCenterY(), degrees);
    }

    private void angles() {

        double l1 = Math.sqrt(Math.pow(xAxis.getEnd().getX() - toRealX(0), 2) + Math.pow(xAxis.getEnd().getY() - toRealY(0), 2));
        double l2 = Math.sqrt(Math.pow(yAxis.getEnd().getX() - toRealX(0), 2) + Math.pow(yAxis.getEnd().getY() - toRealY(0), 2));
        double l3 = Math.sqrt(Math.pow(zAxis.getEnd().getX() - toRealX(0), 2) + Math.pow(zAxis.getEnd().getY() - toRealY(0), 2));

        double l12 = Math.sqrt((Math.pow((xAxis.getEnd().getX() - yAxis.getEnd().getX()), 2) + Math.pow((xAxis.getEnd().getY() - yAxis.getEnd().getY()), 2)));
        double l13 = Math.sqrt((Math.pow((zAxis.getEnd().getX() - xAxis.getEnd().getX()), 2) + Math.pow((zAxis.getEnd().getY() - xAxis.getEnd().getY()), 2)));
        double l23 = Math.sqrt((Math.pow((zAxis.getEnd().getX() - yAxis.getEnd().getX()), 2) + Math.pow((zAxis.getEnd().getY() - yAxis.getEnd().getY()), 2)));

        Double angle1 = Math.acos((l1 * l1 + l2 * l2 - l12 * l12) / (2 * l1 * l2)) * 180 / Math.PI;
        Double angle2 = Math.acos((l1 * l1 + l3 * l3 - l13 * l13) / (2 * l1 * l3)) * 180 / Math.PI;
        Double angle3 = Math.acos((l2 * l2 + l3 * l3 - l23 * l23) / (2 * l2 * l3)) * 180 / Math.PI;

        xyAngle.setText(angle1.toString());
        xzAngle.setText(angle2.toString());
        yzAngle.setText(angle3.toString());

    }

    @FXML
    private void move() {
        double x;
        double y;
        if (deltaX.getText().isEmpty()) {
            x = 0;
        } else {
            x = Double.parseDouble(deltaX.getText());
        }
        if (deltaY.getText().isEmpty()) {
            y = 0;
        } else {
            y = Double.parseDouble(deltaY.getText());
        }
        drawing.move(x, y);
    }

    private void loadFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            ArrayList<Curve> newCurves = (ArrayList<Curve>) inputStream.readObject();

            for (Curve newCurve : newCurves) {
                CurvePoint a = new CurvePoint(newCurve.getA().getX(), newCurve.getA().getY(), newCurve.getA().getWeight());
                CurvePoint b = new CurvePoint(newCurve.getB().getX(), newCurve.getB().getY(), newCurve.getB().getWeight());
                CurvePoint c = new CurvePoint(newCurve.getC().getX(), newCurve.getC().getY(), newCurve.getC().getWeight());
                CurvePoint d = new CurvePoint(newCurve.getD().getX(), newCurve.getD().getY(), newCurve.getD().getWeight());
                Curve curve = new Curve(a, b, c, d);
                curves.add(curve);
                drawing.getChildren().add(curve);
                loadConnect();
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
        }
    }

    private void loadConnect() {
        for (Curve curveA : curves) {
            curves.stream().filter(curveB -> curveA != curveB).forEach(curveB -> {
                for (CurvePoint pointA : curveA.getPoints()) {
                    curveB.getPoints().stream().filter(pointB -> pointA != pointB).filter(pointA::equals).forEach(pointB -> {
                        curveA.connect(pointB, pointA);
                    });
                }
            });
        }
    }

    @FXML
    private void load() {
        FileChooser fileChooser = new FileChooser();
        fileChooser.setTitle("Open Drawing");
        FileChooser.ExtensionFilter extFilter = new FileChooser.ExtensionFilter("DRW files (*.drw)", "*.drw");
        fileChooser.getExtensionFilters().add(extFilter);

        File object = fileChooser.showOpenDialog(MainApp3D.getMainStage());
        loadFile(object);
    }

    @FXML
    private void transform() {
        alpha = Math.toRadians(Double.parseDouble(alphaText.getText()));
        beta = Math.toRadians(Double.parseDouble(betaText.getText()));
        gamma = Math.toRadians(Double.parseDouble(gammaText.getText()));
        erase();
        draw();
        clip();
        angles();
    }

}