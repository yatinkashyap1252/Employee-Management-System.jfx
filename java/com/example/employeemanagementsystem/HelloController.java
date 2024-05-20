package com.example.employeemanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;
import javafx.stage.StageStyle;

import javax.swing.*;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Objects;

public class HelloController {
    @FXML
    private Button login;

    @FXML
    private PasswordField password;

    @FXML
    private TextField username;

    public void loginBtn(ActionEvent ae){
        if(username.getText().isBlank()||password.getText().isBlank()){
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("please fill the blank fields!");
            alert.showAndWait();
        }else{
            validateLogin();
        }
    }
    public void validateLogin(){
        database database=new database();
        Connection connectDb= database.getConnection();
        String query="SELECT count(1) FROM admin WHERE username='"+username.getText()+"' and pwd='"+password.getText()+"'";
        try{
            Statement statement=connectDb.createStatement();
            ResultSet result=statement.executeQuery(query);

            while (result.next()){
                if(result.getInt(1)==1){
                    Alert alert=new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Login");
                    alert.setContentText("Login Successfully done");
                    alert.showAndWait();
                    login.getScene().getWindow().hide();
                    Parent root=FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
                    Stage stage=new Stage();
                    Scene scene=new Scene(root);
                    stage.setScene(scene);
                    stage.setTitle("Employee Management System");
                    stage.show();
                }else{
                    Alert alert=new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Invalid username or password,please try again later");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }
}