package com.djacm.alumniapp.Service;

import android.app.NotificationManager;
import android.content.Context;
import android.support.v4.app.NotificationCompat;
import com.djacm.alumniapp.R;

import com.djacm.alumniapp.Constants;

public class MyNotifManager {
    private Context context;
    private static MyNotifManager instance;

    private MyNotifManager(Context context){
        this.context = context;
    }

    public static synchronized MyNotifManager getInstance(Context context){
        if(instance==null)
            instance = new MyNotifManager(context);
        return instance;
    }

    public void displayNotif(String title, String descript){
        NotificationCompat.Builder builder = new NotificationCompat.Builder(context, Constants.CHANNEL_ID)
                .setContentTitle(title)
                .setContentText(descript)
                .setSmallIcon(R.mipmap.ic_launcher1);

        NotificationManager notificationManager = (NotificationManager)context.getSystemService(Context.NOTIFICATION_SERVICE);
        //set intent and redirect to notifications page

        if(notificationManager != null){
            notificationManager.notify(1, builder.build());
        }
    }

}
