package com.example.employeemanagementsystem;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import javafx.collections.transformation.FilteredList;
import javafx.collections.transformation.SortedList;
import javafx.event.ActionEvent;
import javafx.fxml.FXML;
import javafx.fxml.FXMLLoader;
import javafx.fxml.Initializable;
import javafx.scene.Parent;
import javafx.scene.Scene;
import javafx.scene.control.*;
import javafx.scene.control.cell.PropertyValueFactory;
import javafx.stage.Stage;

import java.io.IOException;
import java.net.URL;
import java.sql.*;
import java.util.ResourceBundle;

public class Dashboard implements Initializable {
    @FXML
    private Button add;

    @FXML
    private Button delete;

    @FXML
    private Button logout;

    @FXML
    private Button update;

    @FXML
    private Label absent;

    @FXML
    private Label present;

    @FXML
    private TextField search;

    @FXML
    private TableColumn<EmployeeData, String> search_employeeid;

    @FXML
    private TableColumn<EmployeeData, String> search_first;

    @FXML
    private TableColumn<EmployeeData, String> search_gender;

    @FXML
    private TableColumn<EmployeeData, String> search_last;

    @FXML
    private TableColumn<EmployeeData, String> search_middle;

    @FXML
    private TableColumn<EmployeeData, String> search_phone;

    @FXML
    private TableColumn<EmployeeData, String> search_salary;

    @FXML
    private TableView<EmployeeData> search_table;

    @FXML
    private Label total;

    private final Connection connect = database.getConnection();
    private PreparedStatement prepare;
    private ResultSet result;

    public ObservableList<EmployeeData>addEmployeeListData(){
        ObservableList<EmployeeData>listData= FXCollections.observableArrayList();
        String sql="SELECT * FROM employee";

        try{
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            EmployeeData employeeD;

            while (result.next()){
                employeeD=new EmployeeData(result.getInt("employee_id"),result.getString("first"),result.getString("last"),result.getString("middle"),result.getString("phone"),result.getString("gender"),result.getString("salary"));  //used for fetching data from database by using constructor emplyeeData class's aur fir wahan se allot hogayi variable ko value
                listData.add(employeeD);
            }
        }catch (Exception e){
            e.printStackTrace();
        }
        return listData;
    }

    public void Search() throws SQLException, InterruptedException {
        String check="SELECT employee_id FROM employee WHERE employee_id='"+search.getText()+"'";
        Statement statement = connect.createStatement();
        result= statement.executeQuery(check);
        if(result.next()){
            FilteredList<EmployeeData>filter=new FilteredList<>(addEmployeeList,e->true);
            search.textProperty().addListener((observable,oldValue,newValue)->{
                filter.setPredicate(predicateEmployeeData->{
                    if(newValue==null||newValue.isEmpty()){return true;}
                    String searchKey=newValue.toLowerCase();
                    if(predicateEmployeeData.getEmployeeid().toString().contains(searchKey)){
                        return true;
                    } else if (predicateEmployeeData.getFirstname().contains(searchKey)) {
                        return true;
                    } else if (predicateEmployeeData.getLastname().contains(searchKey)) {
                        return true;
                    } else if (predicateEmployeeData.getMiddlename().contains(searchKey)) {
                        return true;
                    } else if (predicateEmployeeData.getPhone().contains(searchKey)) {
                        return true;
                    } else return predicateEmployeeData.getSalary().contains(searchKey);
                });
            });

            SortedList<EmployeeData>sortedList=new SortedList<>(filter);
            sortedList.comparatorProperty().bind(search_table.comparatorProperty());
            search_table.setItems(sortedList);
            Alert alert=new Alert(Alert.AlertType.INFORMATION);
            alert.setTitle("Successful");
            alert.setContentText("details searched succefully");
            alert.showAndWait();
        }else{
            Alert alert=new Alert(Alert.AlertType.ERROR);
            alert.setTitle("Error");
            alert.setContentText("No employee with this id is exist!");
            alert.showAndWait();
        }
    }

    private ObservableList<EmployeeData>addEmployeeList;
    public void addEmployeeShowListData(){
        addEmployeeList=addEmployeeListData();
        search_employeeid.setCellValueFactory(new PropertyValueFactory<>("employeeid"));
        search_first.setCellValueFactory(new PropertyValueFactory<>("firstname"));
        search_middle.setCellValueFactory(new PropertyValueFactory<>("middlename"));
        search_last.setCellValueFactory(new PropertyValueFactory<>("lastname"));
        search_phone.setCellValueFactory(new PropertyValueFactory<>("phone"));
        search_salary.setCellValueFactory(new PropertyValueFactory<>("salary"));

        search_table.setItems(addEmployeeList);
    }

    public void Total(){
        String sql="SELECT COUNT(employee_id) FROM employee";
        Connection connect=database.getConnection();
        int count=0;
        try{
            prepare=connect.prepareStatement(sql);
            result=prepare.executeQuery();
            while (result.next()){
                count=result.getInt("COUNT(employee_id)");
            }
            total.setText(String.valueOf(count));
        } catch (Exception ae){ae.printStackTrace();}
    }
    private Statement statement;
    public void Absent(){
        String sql="SELECT COUNT(employee_id) FROM employee WHERE salary='0'";
        Connection connect=database.getConnection();
        int Count=0;
        try{
            statement=connect.createStatement();
            result=statement.executeQuery(sql);
            while(result.next()){
                Count=result.getInt("COUNT(employee_id)");
            }
            absent.setText(String.valueOf(Count));
        }catch (Exception ae){ae.printStackTrace();}
    }

    public void Present(){
        String sql="SELECT COUNT(employee_id) FROM employee WHERE salary>'0'";
        Connection connect=database.getConnection();
        int Count=0;
        try{
            statement=connect.createStatement();
            result=statement.executeQuery(sql);
            while(result.next()){
                Count=result.getInt("COUNT(employee_id)");
            }
            present.setText(String.valueOf(Count));
        }catch (Exception ae){ae.printStackTrace();}
    }

    public void logoutBtn(javafx.event.ActionEvent event) throws IOException {
        logout.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("hello-view.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Employee Management System");
        stage.show();
    }


    public void deleteBtn(ActionEvent event) throws IOException {
        delete.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("Edit.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Employee Management System");
        stage.show();
    }

    public void updateBtn(ActionEvent event)throws Exception {
        update.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("Edit.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Employee Management System");
        stage.show();
    }

    public void addBtn(ActionEvent event) throws IOException{
        add.getScene().getWindow().hide();
        Parent root= FXMLLoader.load(getClass().getResource("Edit.fxml"));
        Stage stage=new Stage();
        Scene scene=new Scene(root);
        stage.setScene(scene);
        stage.setTitle("Employee Management System");
        stage.show();
    }

    @Override
    public void initialize(URL url, ResourceBundle resourceBundle) {
        addEmployeeShowListData();
        Present();
        Absent();
        Total();
    }

}
