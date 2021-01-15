package com.imbuegen.alumniapp.Models;

import android.graphics.Bitmap;

public class CommitteeMember
{
    private String name;
    private String position;
    private Bitmap photo;

    public CommitteeMember()
    {
        this("","",null);
    }

    public CommitteeMember(String name, String position)
    {
        this(name,position,null);
    }

    public CommitteeMember(String name, String position, Bitmap photo)
    {
        this.name = name;
        this.position = position;
        this.photo = photo;
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

    @Override
    public int hashCode()
    {
        return (this.name.hashCode() + this.position.hashCode());
    }
}