package com.djacm.alumniapp.Service;

import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.NotificationCompat;
import android.util.Log;

import com.djacm.alumniapp.Constants;
import com.djacm.alumniapp.R;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;
import com.djacm.alumniapp.Activity.MainActivity;

import java.util.Map;

public class FCMInstanceId extends FirebaseMessagingService {

    static String TAG = "Debug";

    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        // ...

        // TODO(developer): Handle FCM messages here.
        // Not getting messages here? See why this may be: https://goo.gl/39bRNJ
        Log.d(TAG, "From: " + remoteMessage.getFrom());


        RemoteMessage.Notification notification = remoteMessage.getNotification();
        Log.d(TAG, "onMessageReceived: "+notification.getBody() + " " + notification.getTitle());
        if(notification == null) {

            return;
        }
        Map<String, String> data = remoteMessage.getData();
        ShowNotification(notification, data);

    }

    private void ShowNotification(RemoteMessage.Notification notification, Map<String, String> data) {

        Intent intent = new Intent(this, MainActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        PendingIntent pendingIntent = PendingIntent.getActivity(this, 0, intent, PendingIntent.FLAG_ONE_SHOT);

        android.support.v4.app.NotificationCompat.Builder notificationBuilder = new NotificationCompat.Builder(this,Constants.CHANNEL_ID)
                .setContentTitle(notification.getTitle())
                .setContentText(notification.getBody())
                .setAutoCancel(true)
                .setContentIntent(pendingIntent)
                .setPriority(NotificationCompat.PRIORITY_DEFAULT)
                .setSmallIcon(R.mipmap.ic_launcher1);

        Log.d(TAG, "ShowNotification: 1");
        NotificationManager notificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        notificationManager.notify(0, notificationBuilder.build());


        MyNotifManager.getInstance(this).displayNotif(notification.getTitle(),notification.getBody());
        Log.d(TAG, "ShowNotification: 2");

    }
}
