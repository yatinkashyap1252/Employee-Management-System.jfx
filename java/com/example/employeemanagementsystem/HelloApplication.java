package com.example.employeemanagementsystem;

import javafx.application.Application;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.stage.Stage;
import javafx.stage.StageStyle;
import javafx.scene.input.MouseEvent;

import java.io.IOException;

public class HelloApplication extends Application {
    private double x=0;
    private double y=0;
    @Override
    public void start(Stage stage) throws IOException {
        Parent root=FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Scene scene = new Scene(root);
        root.setOnMousePressed((MouseEvent event)->{
            x=event.getSceneX();
            y= event.getSceneY();
        });
        stage.initStyle(StageStyle.TRANSPARENT);
        stage.setTitle("Employee Management System");
        stage.setScene(scene);
        stage.show();
    }

    public static void main(String[] args) {
        launch();
    }
}