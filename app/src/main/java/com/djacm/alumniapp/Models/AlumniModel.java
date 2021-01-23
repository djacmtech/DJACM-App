package com.djacm.alumniapp.Models;

import com.google.firebase.database.PropertyName;

import java.io.Serializable;
import java.util.ArrayList;

public class AlumniModel   implements Serializable {

    @PropertyName("Name")
    private String alumniName;

    @PropertyName("Position")
    private String position;

    @PropertyName("JoinDate")
    private int joinDate;

    @PropertyName("uid")
    private String uid;

    private String company;
    private String databaseReferencePath;


    public String getDatabaseReferencePath() {
        return databaseReferencePath;
    }

    public void setDatabaseReferencePath(String databaseReferencePath) {
        this.databaseReferencePath = databaseReferencePath;
    }

    @PropertyName("questions")
    private ArrayList<QuestionsModel> questionsList;



    

    public AlumniModel() { }



    public String getAlumniName() {
        return alumniName;
    }

    public void setAlumniName(String alumniName) {
        this.alumniName = alumniName;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public String getJoinDate() {
        return joinDate + "";
    }

    public String getCompany() {
        return company;
    }

    public void setCompany(String company) {
        this.company = company;
    }

    public ArrayList<QuestionsModel> getQuestionsList() {
        return questionsList;
    }

    public void setQuestionsList(ArrayList<QuestionsModel> questionsList) {
        this.questionsList = questionsList;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
