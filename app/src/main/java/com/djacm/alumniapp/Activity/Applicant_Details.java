package com.djacm.alumniapp.Activity;

public class Applicant_Details {

    private String name, email, department, year,sap;
    private int amount,count;

    public Applicant_Details(String name, String email, String department, String year,String sap,int amount ,int count) {
        this.name = name;
        this.email = email;
        this.department = department;
        this.year = year;
        this.amount=amount;
        this.sap=sap;
        this.count=count;

    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getSap() {
        return sap;
    }

    public void setSap(String sap) {
        this.sap = sap;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }

    public Applicant_Details() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getDepartment() {
        return department;
    }

    public void setDepartment(String department) {
        this.department = department;
    }

    public String getYear() {
        return year;
    }

    public void setYear(String year) {
        this.year = year;
    }
}

