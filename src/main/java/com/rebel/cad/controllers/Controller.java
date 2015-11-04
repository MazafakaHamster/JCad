package com.rebel.cad.controllers;

import javafx.scene.control.Alert;
import javafx.scene.control.TextInputDialog;

import java.util.Optional;

/**
 * Created by Slava on 05.11.2015.
 */
public class Controller {
    protected void showInform(String title, String content) {
        Alert alert = new Alert(Alert.AlertType.INFORMATION);
        alert.setTitle(title);
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }

    protected double askParam(String content) {
        TextInputDialog dialog = new TextInputDialog();
        dialog.setTitle("Set parameter");
        dialog.setHeaderText(null);
        dialog.setContentText(content);

        while (true) {
            Optional<String> result = dialog.showAndWait();
            if (result.isPresent() && !result.get().isEmpty()) {
                return Double.parseDouble(result.get());
            } else {
                showError("Field is empty");
            }
        }
    }

    protected void showError(String content) {
        Alert alert = new Alert(Alert.AlertType.ERROR);
        alert.setTitle("Error");
        alert.setHeaderText(null);
        alert.setContentText(content);
        alert.showAndWait();
    }
}
