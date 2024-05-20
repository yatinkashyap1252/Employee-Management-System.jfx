package com.example.employeemanagementsystem;

import java.sql.Connection;
import java.sql.DriverManager;

public class database {
    public static Connection connect;
    public static Connection getConnection(){
        try{
            Class.forName("com.mysql.cj.jdbc.Driver");
            connect=DriverManager.getConnection("jdbc:mysql://localhost/empmanagementsystem","root","Yatin@1252");
        }catch (Exception e){
            e.printStackTrace();
        }
        return connect;
    }
}
