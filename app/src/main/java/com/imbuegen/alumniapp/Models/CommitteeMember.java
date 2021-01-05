package com.imbuegen.alumniapp.Models;

public class CommitteeMember
{

    int photoId;
    String photoUrl;
    String name;
    String position;

    public CommitteeMember() {
    }


    public CommitteeMember(int photoId, String name, String position) {
        this.photoId = photoId;
        this.name = name;
        this.position = position;
    }

    public CommitteeMember(String photoUrl, String name, String position) {
        this.photoUrl = photoUrl;
        this.name = name;
        this.position = position;

    }


    public int getPhotoId() {
        return photoId;
    }

    public void setPhotoId(int photoId) {
        this.photoId = photoId;
    }

    public String getPhotoUrl() {
        return photoUrl;
    }

    public void setPhotoUrl(String photoUrl) {
        this.photoUrl = photoUrl;
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

}