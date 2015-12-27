package com.rebel.cad;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.image.Image;
import javafx.stage.Stage;

/**
 * Created by Slava on 04.09.2015.
 */
public class MainApp3D extends Application {

    public final static int INITIAL_WIDTH = 800;
    public final static int INITIAL_HEIGHT = 600;
    private static Stage mainStage;

    @Override
    public void start(Stage primaryStage) throws Exception {
        Parent root = FXMLLoader.load(getClass().getResource("/fxml/main3D.fxml"));
        mainStage = primaryStage;
        primaryStage.setScene(new Scene(root));
        primaryStage.setTitle("CAD");
        primaryStage.getIcons().add(new Image(getClass().getResourceAsStream("/icon.png")));
        primaryStage.show();
    }

    public static void main(String[] args) {
        launch(args);
    }

    public static Stage getMainStage() {
        return mainStage;
    }
}
