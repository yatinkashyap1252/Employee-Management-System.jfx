package com.example.employeemanagementsystem;

import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.stage.Stage;

import java.io.IOException;
import java.sql.*;

import static com.example.employeemanagementsystem.database.connect;

public class Edit {
    @FXML
    private Button back;

    @FXML
    private Button edit_add;

    @FXML
    private Button edit_clear;

    @FXML
    private Button edit_delete;

    @FXML
    private TextField edit_employeeid;

    @FXML
    private CheckBox edit_female;

    @FXML
    private TextField edit_first;

    @FXML
    private TextField edit_last;

    @FXML
    private CheckBox edit_male;

    @FXML
    private TextField edit_middle;

    @FXML
    private TextField edit_phone;

    @FXML
    private TextField edit_salary;

    @FXML
    private Button edit_update;
    @FXML
    private TextField search;
    @FXML
    private RadioButton edit_gender;

    private Statement statement;
    private ResultSet result;

    public void Reset(){
        edit_employeeid.setText("");
        edit_first.setText("");
        edit_middle.setText("");
        edit_last.setText("");
        edit_phone.setText("");
        edit_salary.setText("");
    }

    public void addEmployeeAdd(){
        String sql="INSERT INTO employee"+"(employee_id,first,middle,last,phone,salary)"+"VALUES(?,?,?,?,?,?)";
        Connection connect = database.getConnection();
        try{
            if(edit_employeeid.getText().isEmpty()||edit_first.getText().isEmpty()||edit_middle.getText().isEmpty()||edit_last.getText().isEmpty()||edit_phone.getText().isEmpty()||edit_salary.getText().isEmpty()){
                Alert alert=new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("please fill the blank fields!");
                alert.showAndWait();
            }else {
                PreparedStatement prepare = connect.prepareStatement(sql);
                prepare.setString(1, edit_employeeid.getText());
                prepare.setString(2, edit_first.getText());
                prepare.setString(3, edit_middle.getText());
                prepare.setString(4, edit_last.getText());
                prepare.setString(5, edit_phone.getText());
                prepare.setString(6, edit_salary.getText());
                prepare.executeUpdate();
                Alert alert=new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Successful");
                alert.setContentText("details added successfully");
                alert.showAndWait();
                Dashboard d=new Dashboard();
                d.addEmployeeShowListData();
                Reset();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public void Update(){
        String sql="UPDATE employee SET first='"+edit_first.getText()+"',middle='"+edit_middle.getText()+"',last='"+edit_last.getText()+"',phone='"+edit_phone.getText()+"',salary='"+edit_salary.getText()+"'WHERE employee_id='"+edit_employeeid.getText()+"'";
        connect=database.getConnection();
        try{
            if(edit_employeeid.getText().isEmpty()||edit_first.getText().isEmpty()||edit_middle.getText().isEmpty()||edit_last.getText().isEmpty()||edit_phone.getText().isEmpty()||edit_salary.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("please fill the blank fields!");
                alert.showAndWait();
            }else{
                statement= connect.createStatement();
                statement.executeUpdate(sql);
                Alert alert = new Alert(Alert.AlertType.INFORMATION);
                alert.setTitle("Information");
                alert.setContentText("information updated");
                alert.showAndWait();
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void Delete(){
        String sql="DELETE FROM employee WHERE employee_id='"+edit_employeeid.getText()+"'";
        connect=database.getConnection();
        try{
            if(edit_employeeid.getText().isEmpty()) {
                Alert alert = new Alert(Alert.AlertType.ERROR);
                alert.setTitle("Error");
                alert.setContentText("please fill the blank fields!");
                alert.showAndWait();
            }else{
                String check="SELECT employee_id FROM employee WHERE employee_id='"+edit_employeeid.getText()+"'";
                statement=connect.createStatement();
                result=statement.executeQuery(check);
                if(result.next()) {
                    statement = connect.createStatement();
                    statement.executeUpdate(sql);
                    Alert alert = new Alert(Alert.AlertType.INFORMATION);
                    alert.setTitle("Information");
                    alert.setContentText("information Deleted");
                    alert.showAndWait();
                }
                else{
                    Alert alert = new Alert(Alert.AlertType.ERROR);
                    alert.setTitle("Error");
                    alert.setContentText("Employee id with this,does not exist");
                    alert.showAndWait();
                }
            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    public void back_btn(ActionEvent event) throws IOException {
        back.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("Dashboard.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.show();
    }
}
