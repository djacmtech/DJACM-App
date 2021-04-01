package com.djacm.alumniapp.Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class InternshipCompanyModel
{

    private String skills,cmpDescription,jobDescription,name,url,perks;
    private Bitmap logoBmp;
    private int stipend;

    public InternshipCompanyModel(){

    }

    public InternshipCompanyModel(String skills, String cmpDescription, String name, String url, String jobDescription, String perks, int stipend) {
        this.skills = skills;
        this.cmpDescription = cmpDescription;
        this.name = name;
        this.url = url;
        this.jobDescription = jobDescription;
        this.perks = perks;
        this.stipend = stipend;
    }

    public InternshipCompanyModel(String skills, String name) {
        this.skills = skills;
        this.name = name;
    }

    protected InternshipCompanyModel(Parcel in) {
        skills = in.readString();
        cmpDescription = in.readString();
        jobDescription = in.readString();
        name = in.readString();
        url = in.readString();
        perks = in.readString();
        logoBmp = in.readParcelable(Bitmap.class.getClassLoader());
        stipend = in.readInt();
    }

    public String getSkills() {
        return skills;
    }

    public void setSkills(String skills) {
        this.skills = skills;
    }

    public String getCompanyDescription() {
        return cmpDescription;
    }

    public void setCompanyDescription(String description) {
        this.cmpDescription = description;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public Bitmap getLogoBmp()
    {
        return logoBmp;
    }

    public void setLogoBmp(Bitmap logoBmp) {
        this.logoBmp = logoBmp;
    }

    public String getJobDescription() {
        return jobDescription;
    }

    public void setJobDescription(String jobDescription) {
        this.jobDescription = jobDescription;
    }

    public String getPerks() {
        return perks;
    }

    public void setPerks(String perks) {
        this.perks = perks;
    }

    public int getStipend() {
        return stipend;
    }

    public void setStipend(int stipend) {
        this.stipend = stipend;
    }
}
