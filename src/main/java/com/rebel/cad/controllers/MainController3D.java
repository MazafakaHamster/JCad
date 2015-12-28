package com.rebel.cad.controllers;

import com.rebel.cad.MainApp3D;
import com.rebel.cad.collections.ShapeGroup;
import com.rebel.cad.shape.*;
import com.rebel.cad.shape.wrappers.TextWrapper;
import com.rebel.cad.util.Utils;
import com.sun.java.accessibility.util.java.awt.TextComponentTranslator;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.BorderPane;
import javafx.scene.layout.Pane;
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;

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

    @FXML
    private TextField aTorusText;
    @FXML
    private TextField bTorusText;

    private ShapeGroup axises;

    public static double toRealX(double x) {
        return x + width / 2;
    }

    public static double toFakeX(double x) {
        return x - width / 2;
    }

    public static double toRealY(double y) {
        return -y + height / 2 + 50;
    }

    public static double toFakeY(double y) {
        return -(y - height / 2 - 50);
    }

    public static ShapeGroup getFigure() {
        return staticFigure;
    }

    public static ArrayList<Curve> curves = new ArrayList<>();

    private Double alpha = Math.toRadians(0);
    private Double beta = Math.toRadians(83);
    private Double gamma = Math.toRadians(83);
    private boolean moveFigure = false;

    @FXML
    private void moveFig() {
        moveFigure = !moveFigure;
    }

    private double figX;
    private double figY;
    private double figZ;

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

        rootPane.setOnKeyPressed(event -> {
            if (moveFigure) {
                switch (event.getCode()) {
                    case NUMPAD4:
                        figX += 0.1;
                        break;
                    case NUMPAD6:
                        figX -= 0.1;
                        break;
                    case NUMPAD8:
                        figY += 0.1;
                        break;
                    case NUMPAD2:
                        figY -= 0.1;
                        break;
                }
                transform();
            } else {
                switch (event.getCode()) {
                    case NUMPAD7:
                        centerPoint.setX(centerPoint.getX() + 1);
                        figX += 1;
                        break;
                    case NUMPAD9:
                        centerPoint.setX(centerPoint.getX() - 1);
                        figX -= 1;
                        break;
                    case NUMPAD4:
                        centerPoint.setY(centerPoint.getY() + 1);
                        figY += 1;
                        break;
                    case NUMPAD6:
                        centerPoint.setY(centerPoint.getY() - 1);
                        figY -= 1;
                        break;
                    case NUMPAD8:
                        centerPoint.setZ(centerPoint.getZ() + 1);
                        figZ += 1;
                        break;
                    case NUMPAD2:
                        centerPoint.setZ(centerPoint.getZ() - 1);
                        figZ -= 1;
                        break;
                }
                transform();
            }
        });

        staticFigure = drawing;

        alphaText.setText(Long.toString(Math.round(Math.toDegrees(alpha))));
        betaText.setText(Long.toString(Math.round(Math.toDegrees(beta))));
        gammaText.setText(Long.toString(Math.round(Math.toDegrees(gamma))));

        aTorusText.setText("200");
        bTorusText.setText("100");
        load();
        buildTorus();
//        load();
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


    private Point pointR(double x, double y, double z, Point3D center) {
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
                                {x + center.getX()},
                                {y + center.getY()},
                                {z + center.getZ()},
                        })
        );
        return new Point(toRealX(resultMatrix[0][0]), toRealY(-resultMatrix[1][0]));
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

    Point3D centerPoint = new Point3D(0, 0, 0);

    private void draw() {
        Point bottom1 = pointR(0, 0, 0, centerPoint);
        Point bottom2 = pointR(200, 0, 0, centerPoint);
        Point bottom3 = pointR(200, 200, 0, centerPoint);
        Point bottom4 = pointR(0, 200, 0, centerPoint);

        Point top1 = pointR(0, 0, 200, centerPoint);
        Point top2 = pointR(200, 0, 200, centerPoint);
        Point top3 = pointR(200, 200, 200, centerPoint);
        Point top4 = pointR(0, 200, 200, centerPoint);

        Point roof1 = pointR(100, 100, 250, centerPoint);


        drawing.getChildren().addAll(
                new Line(bottom1, bottom2),
                new Line(bottom2, bottom3),
                new Line(bottom3, bottom4),
                new Line(bottom4, bottom1),

                new Line(bottom1, top1),
                new Line(bottom2, top2),
                new Line(bottom3, top3),
                new Line(bottom4, top4),

                new Line(top1, top2),
                new Line(top2, top3),
                new Line(top3, top4),
                new Line(top4, top1),

                new Line(top1, roof1),
                new Line(top2, roof1),
                new Line(top3, roof1),
                new Line(top4, roof1)
        );
    }

    private Point pointR(Point3D point3D) {
        double x = point3D.getX();
        double y = point3D.getY();
        double z = point3D.getZ();

        if (!moveFigure) {
            x += figX;
            y += figY;
            z += figZ;
        }

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

    @FXML
    private void buildTorus() {
        erase();
        double R1 = Double.parseDouble(aTorusText.getText());
        double R2 = Double.parseDouble(bTorusText.getText());
        double stepU = 90;
        double stepV = 2;
        double maxV = 360;
        double maxU = 225;
        double opacity = 0.1;

        List<Point> points = new ArrayList<>();

        for (int v = 0; v <= maxV; v += stepV) {
            for (int u = -135; u <= maxU; u += stepU) {
                points.add(pointR(
                        TorusHelper.getX(R1, R2, Math.toRadians(v), Math.toRadians(u)),
                        TorusHelper.getY(R1, R2, Math.toRadians(v), Math.toRadians(u)),
                        TorusHelper.getZ(R2, Math.toRadians(v)), centerPoint));
            }
        }

        for (int i = 0; i < points.size() - 1; i++) {
            if ((maxU / stepU - i - 1) % maxU == 0) {
                drawing.getChildren().add(new Line(points.get(i), points.get(i - (int) (maxU / stepU) + 1), opacity));
            } else {
                drawing.getChildren().add(new Line(points.get(i), points.get(i + 1), opacity));
            }
        }
        rebuildCurves();
        clip();
    }

    private void pointToTorus(CurvePoint point) {
        Point temp = pointR(XYtoUV(point));
        point.setX(temp.getX());
        point.setY(temp.getY());
    }

    private Point3D XYtoUV(CurvePoint point) {
        double v = (point.getX() / Double.parseDouble(bTorusText.getText())) * (5 * Math.PI / Double.parseDouble(bTorusText.getText()));
        double u = (point.getY() / Double.parseDouble(bTorusText.getText())) * (5 * Math.PI / Double.parseDouble(bTorusText.getText()));
        if (moveFigure)
            return UVtoXYZ(u + figY, v + figX);
        else return UVtoXYZ(u, v);
    }

    private Point3D UVtoXYZ(double u, double v) {
        Point3D xyz = new Point3D();
        xyz.setX(TorusHelper.getX(Double.parseDouble(aTorusText.getText()), Double.parseDouble(bTorusText.getText()), u, v));
        xyz.setY(TorusHelper.getY(Double.parseDouble(aTorusText.getText()), Double.parseDouble(bTorusText.getText()), u, v));
        xyz.setZ(TorusHelper.getZ(Double.parseDouble(bTorusText.getText()), u));
        return xyz;
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

    private ArrayList<Curve> etalonCurves;

    private void rebuildCurves() {
        drawing.getChildren().removeAll(curves);
        curves.clear();
        for (Curve newCurve : etalonCurves) {
            CurvePoint a = new CurvePoint(newCurve.getA().getX(), newCurve.getA().getY(), newCurve.getA().getWeight());
            CurvePoint b = new CurvePoint(newCurve.getB().getX(), newCurve.getB().getY(), newCurve.getB().getWeight());
            CurvePoint c = new CurvePoint(newCurve.getC().getX(), newCurve.getC().getY(), newCurve.getC().getWeight());
            CurvePoint d = new CurvePoint(newCurve.getD().getX(), newCurve.getD().getY(), newCurve.getD().getWeight());
            pointToTorus(a);
            pointToTorus(c);
            pointToTorus(d);
            pointToTorus(b);
            Curve curve = new Curve(a, b, c, d, false);
            drawing.getChildren().add(curve);
        }
    }

    private void loadFile(File file) {
        try (ObjectInputStream inputStream = new ObjectInputStream(new FileInputStream(file))) {
            etalonCurves = (ArrayList<Curve>) inputStream.readObject();

            for (Curve newCurve : etalonCurves) {
                CurvePoint a = new CurvePoint(newCurve.getA().getX(), newCurve.getA().getY(), newCurve.getA().getWeight());
                CurvePoint b = new CurvePoint(newCurve.getB().getX(), newCurve.getB().getY(), newCurve.getB().getWeight());
                CurvePoint c = new CurvePoint(newCurve.getC().getX(), newCurve.getC().getY(), newCurve.getC().getWeight());
                CurvePoint d = new CurvePoint(newCurve.getD().getX(), newCurve.getD().getY(), newCurve.getD().getWeight());
                pointToTorus(a);
                pointToTorus(c);
                pointToTorus(d);
                pointToTorus(b);
                Curve curve = new Curve(a, b, c, d, false);
                curves.add(curve);
                drawing.getChildren().add(curve);
            }
        } catch (ClassNotFoundException | IOException ex) {
            ex.printStackTrace();
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
    private void xPlus() {
        alphaText.setText(Long.toString(Math.round(Math.toDegrees(alpha) + 1)));
        transform();

    }

    @FXML
    private void xMinus() {
        alphaText.setText(Long.toString(Math.round(Math.toDegrees(alpha) - 1)));
        transform();
    }

    @FXML
    private void yPlus() {
        betaText.setText(Long.toString(Math.round(Math.toDegrees(beta) + 1)));
        transform();
    }

    @FXML
    private void yMinus() {
        betaText.setText(Long.toString(Math.round(Math.toDegrees(beta) - 1)));
        transform();
    }

    @FXML
    private void zPlus() {
        gammaText.setText(Long.toString(Math.round(Math.toDegrees(gamma) + 1)));
        transform();
    }

    @FXML
    private void zMinus() {
        gammaText.setText(Long.toString(Math.round(Math.toDegrees(gamma) - 1)));
        transform();
    }

    @FXML
    private void transform() {
        alpha = Math.toRadians(Double.parseDouble(alphaText.getText()));
        beta = Math.toRadians(Double.parseDouble(betaText.getText()));
        gamma = Math.toRadians(Double.parseDouble(gammaText.getText()));
        erase();
        buildTorus();
//        draw();
        clip();
        angles();
    }

}