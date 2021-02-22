package com.djacm.alumniapp.Models;

import android.graphics.Bitmap;

public class CommitteeMember
{
    private String name;
    private String position;
    private int year;
    private Bitmap photo;
    private String picUrl;

    public CommitteeMember()
    {
        this("","",0,null, "");
    }

    public CommitteeMember(String name, String position, int year, String picUrl)
    {
        this(name,position,year,null,picUrl);
    }

    public CommitteeMember(String name, String position, int year,Bitmap photo, String picUrl)
    {
        this.name = name;
        this.position = position;
        this.year = year;
        this.photo = photo;
        this.picUrl = picUrl;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPosition() {
        return position;
    }

    public void setPosition(String position) {
        this.position = position;
    }

    public Bitmap getPhoto()
    {
        return photo;
    }

    public void setPhoto(Bitmap newPhoto)
    {
        this.photo = newPhoto;
    }

    public String getPicUrl()
    {
        return picUrl;
    }

    public void setPicUrl(String newUrl)
    {
        this.picUrl = newUrl;
    }

    public int getYear()
    {
        return this.year;
    }

    public void setYear(int newYear)
    {
        this.year = newYear;
    }

    @Override
    public int hashCode()
    {
        return (this.name.hashCode() + this.position.hashCode() + (Integer.valueOf(year)).hashCode());
    }
}