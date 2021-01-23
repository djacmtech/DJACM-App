package com.djacm.alumniapp.Service;

import android.content.SharedPreferences;

public class SFHandler {


    public static final String USER_KEY = "USER_KEY";

    public static String getString(SharedPreferences sharedPreferences,String key) {
        return sharedPreferences.getString(key,"");
    }
    public static boolean setString(SharedPreferences sharedPreferences,String key,String value) {
        return sharedPreferences.edit().putString(key,value).commit();
    }


}
