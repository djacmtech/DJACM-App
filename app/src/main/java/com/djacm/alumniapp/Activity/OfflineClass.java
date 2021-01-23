package com.djacm.alumniapp.Activity;

import android.app.Application;

import com.google.firebase.database.FirebaseDatabase;

public class OfflineClass extends Application {

@Override
public void onCreate() {
        super.onCreate();

        FirebaseDatabase.getInstance().setPersistenceEnabled(true);
        }
        }
