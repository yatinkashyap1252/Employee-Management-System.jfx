package com.example.employeemanagementsystem;

public class EmployeeData {
    private Integer employeeid;
    private String firstname;
    private String middlename;
    private String lastname;
    private String gender;
    private String phone;
    private String salary;

    public EmployeeData(Integer employeeid,String firstname,String middlename,String lastname,String phone,String gender,String salary){
        this.employeeid = employeeid;
        this.firstname = firstname;
        this.middlename = middlename;
        this.lastname = lastname;
        this.gender = gender;
        this.phone = phone;
        this.salary = salary;
    }

    public Integer getEmployeeid() {
        return employeeid;
    }

    public String getFirstname() {
        return firstname;
    }

    public String getMiddlename() {
        return middlename;
    }

    public String getLastname() {
        return lastname;
    }

    public String getGender() {
        return gender;
    }

    public String getPhone() {
        return phone;
    }

    public String getSalary() {
        return salary;
    }
}
