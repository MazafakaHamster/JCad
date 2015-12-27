package com.rebel.cad.controllers;

import com.rebel.cad.MainApp2D;
import com.rebel.cad.collections.ShapeGroup;
import com.rebel.cad.shape.*;
import com.rebel.cad.shape.impl.SuperEllipseNormal;
import com.rebel.cad.shape.impl.SuperEllipseTangent;
import com.rebel.cad.shape.wrappers.TextWrapper;
import com.rebel.cad.util.Helper;
import javafx.fxml.FXML;
import javafx.fxml.Initializable;
import javafx.scene.Scene;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.layout.Pane;
import javafx.scene.layout.VBox;
import javafx.scene.shape.Rectangle;
import javafx.stage.FileChooser;
import javafx.stage.Modality;
import javafx.stage.Stage;

import java.io.*;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ResourceBundle;

public class MainController3D extends Controller implements Initializable {

    private static int dotCount;
    private static double width = MainApp2D.INITIAL_WIDTH;
    private static double height = MainApp2D.INITIAL_HEIGHT;
    private ArrayList<HintedDot> dotsList = new ArrayList<>();
    private HintedDot rotationPoint;

    public static ShapeGroup staticFigure;

    @FXML
    private ShapeGroup drawing;
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
    @FXML
    private TextField deltaX;
    @FXML
    private TextField deltaY;

    private ShapeGroup axises;

    public static double toRealX(double x) {
        return x + width / 2 - 90;
    }

    public static double toFakeX(double x) {
        return x - width / 2 + 90;
    }

    public static double toRealY(double y) {
        return -y + height / 2;
    }

    public static double toFakeY(double y) {
        return -(y - height / 2);
    }

    public static ShapeGroup getFigure() {
        return staticFigure;
    }

    public static ArrayList<Curve> curves = new ArrayList<>();

    @Override
    public void initialize(URL location, ResourceBundle resources) {
        axises = createAxis(width, height - 40);

        groupPane.getChildren().addAll(axises);

        groupPane.setOnMouseMoved(event -> {
            xCoord.setText(Double.toString(toFakeX(event.getX())));
            yCoord.setText(Double.toString(toFakeY(event.getY())));
        });

        rootBox.setPrefWidth(800);
        rootBox.setPrefHeight(600);

        rootBox.widthProperty().addListener((observable, oldValue, newValue) -> {
            width = newValue.doubleValue();
            double delta = width - oldValue.doubleValue();
            if (oldValue.intValue() != 0)
                drawing.move(delta / 2, 0);
            resize();
        });
        rootBox.heightProperty().addListener((observable, oldValue, newValue) -> {
            height = newValue.doubleValue();
            double delta = height - oldValue.doubleValue();
            if (oldValue.intValue() != 0)
                drawing.move(0, delta / 2);
            resize();
        });
        staticFigure = drawing;
    }

    private ShapeGroup createAxis(double width, double height) {
        ShapeGroup axis = new ShapeGroup();

        axis.getChildren().add(new Line(width / 2, 0, width / 2, height));
        axis.getChildren().add(new Line(0, height / 2, width, height / 2));

        axis.getChildren().add(new TextWrapper(width - 20, height / 2 + 20, "X"));
        axis.getChildren().add(new TextWrapper(width / 2 - 20, 20, "Y"));

        return axis;
    }

    @FXML
    private void erase() {
        dotsList.add(rotationPoint);
        groupPane.getChildren().removeAll(dotsList);
        dotsList.clear();
        dotCount = 0;
        drawing.getChildren().removeAll(drawing.getChildren());
        drawing.getChildren().clear();
        curves.clear();
    }


    private void clip() {
        drawing.setClip(new Rectangle(0, 0, width - 180, height));
        axises.setClip(new Rectangle(0, 0, width - 180, height));

    }

    private void resize() {
        groupPane.getChildren().removeAll(axises);
        axises = createAxis(width - 180, height);
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

        File object = fileChooser.showOpenDialog(MainApp2D.getMainStage());
        loadFile(object);
    }

}