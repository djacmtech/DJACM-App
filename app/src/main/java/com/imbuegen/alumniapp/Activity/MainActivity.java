package com.imbuegen.alumniapp.Activity;

import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Intent;
import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.messaging.FirebaseMessaging;
import com.imbuegen.alumniapp.Constants;
import com.imbuegen.alumniapp.Service.SFHandler;import com.imbuegen.alumniapp.R;

public class MainActivity extends BaseActivity {

    private FirebaseUser currentUser;
    private FirebaseAuth mAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

      //  startActivity(new Intent(MainActivity.this,InternshipCompany.class));

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        initNotification();

        Intent intent1 = new Intent(MainActivity.this, BaseActivity.class);
        startActivity(intent1);
        //Intent intent2 = new Intent(MainActivity.this, RegistrationActivity.class);


//        intent1.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
//        intent2.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        /*boolean isAlumni = SFHandler.getString(getSharedPreferences("Auth",MODE_PRIVATE),SFHandler.USER_KEY).equals("Alumni");

        if(currentUser!=null && isAlumni)
            startActivity(intent2);
        else if(currentUser != null)
            startActivity(intent1);
        else  {startActivity(new Intent(MainActivity.this,LoginActivity.class));}
        finish();*/
    }

    private void initNotification() {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationManager notificationManager  = (NotificationManager) getSystemService(NOTIFICATION_SERVICE);
            NotificationChannel notificationChannel = new NotificationChannel(Constants.CHANNEL_ID, Constants.CHANNEL_NAME, NotificationManager.IMPORTANCE_HIGH);
            notificationChannel.setDescription(Constants.CHANNEL_DESCRIPTION);
            notificationChannel.enableLights(true);
            notificationChannel.enableVibration(true);
            notificationManager.createNotificationChannel(notificationChannel);
        }
        FirebaseMessaging.getInstance().subscribeToTopic("all");
    }
}
