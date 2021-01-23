package com.djacm.alumniapp.Models;


    public class EventMember {
        int photoId;
        String photoUrl;

        public EventMember() {

        }


        public EventMember(int photoId) {
            this.photoId = photoId;
        }

        public EventMember(String photoUrl) {
            this.photoUrl = photoUrl;
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


    }



