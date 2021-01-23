package com.djacm.alumniapp.Activity;

public class Application_Details {
    public String name,department,year,email;

    public Application_Details() {
    }

    public Application_Details(String name, String department, String year, String email) {
        this.name = name;
        this.department = department;
        this.year = year;
        this.email = email;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }
}
