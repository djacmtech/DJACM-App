package com.djacm.alumniapp;

import android.app.Application;

public class OfflineClass extends Application {

    @Override
    public void onCreate() {
        super.onCreate();

        //FirebaseDatabase.getInstance().setPersistenceEnabled(true);
    }
}
