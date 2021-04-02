package com.djacm.alumniapp.Models;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

public class InternshipCompanyModel
{

    private String skills,cmpDescription,jobDescription,name,url,perks,websiteUrl,domain,stipend;
    private Bitmap logoBmp;

    public InternshipCompanyModel(){

    }

    public InternshipCompanyModel(String skills, String cmpDescription, String name, String url, String jobDescription, String perks, String stipend,
                                  String websiteUrl,String domain)
    {
        this.skills = skills;
        this.cmpDescription = cmpDescription;
        this.name = name;
        this.url = url;
        this.jobDescription = jobDescription;
        this.perks = perks;
        this.stipend = stipend;
        this.websiteUrl = websiteUrl;
        this.domain = domain;
    }

    public InternshipCompanyModel(String skills, String name) {
        this.skills = skills;
        this.name = name;
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

    public String getStipend() {
        return stipend;
    }

    public void setStipend(String stipend) {
        this.stipend = stipend;
    }

    public String getWebsiteUrl() {
        return websiteUrl;
    }

    public void setWebsiteUrl(String websiteUrl) {
        this.websiteUrl = websiteUrl;
    }

    public String getDomain() {
        return domain;
    }

    public void setDomain(String domain) {
        this.domain = domain;
    }
}
