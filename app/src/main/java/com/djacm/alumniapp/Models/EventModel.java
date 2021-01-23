package com.djacm.alumniapp.Models;

public class EventModel {
    public String title;
    public String body;
    public String date;
    public String shortDesc;
    public String photoURL;
    public String image1;
    public String image2;
    public String image3;
    public String image4;
    public String id;

    public EventModel() {
    }

    public EventModel(String title, String body, String date,String shortDesc,String photoURL,String image1,String image2,String image3,String image4) {
        this.title = title;
        this.body = body;
        this.date = date;
        this.shortDesc=shortDesc;
        this.photoURL =photoURL;
        this.image1=image1;
        this.image2=image2;
        this.image3=image3;
        this.image4=image4;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getBody() {
        return body;
    }

    public void setBody(String body) {
        this.body = body;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getshortDesc() {
        return shortDesc;
    }

    public void setshortDesc(String shortDesc) {
        this.shortDesc = shortDesc;
    }

    public String getPhotoURL() { return photoURL; }

    public void setPhotoURL(String photoURL) {
        this.photoURL = photoURL;
    }

    public String getImage1() { return image1; }

    public void setImage1(String image1) {
        this.image1 = image1;
    }

    public String getImage2() { return image2; }

    public void setImage2(String image2) {
        this.image2 = image2;
    }

    public String getImage3() { return image3; }

    public void setImage3(String image3) {
        this.image3 = image3;
    }

    public String getImage4() { return image4; }

    public void setImage4(String image4) {
        this.image4 = image4;
    }
}
