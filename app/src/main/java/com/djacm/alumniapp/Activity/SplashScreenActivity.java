package com.djacm.alumniapp.Activity;

import android.content.Intent;
import android.graphics.drawable.Drawable;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.ImageView;

import com.djacm.alumniapp.R;

public class SplashScreenActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash_screen);

//
//        ImageView iv = findViewById(R.id.splash_screen_logo);
//        Picasso.get().load("https://goo.gl/iJfFmN").into(iv);


        ImageView img=(ImageView)findViewById(R.id.splash_screen_logo);
        Drawable myDrawable = getResources().getDrawable(R.drawable.ic_acm);
        img.setImageDrawable(myDrawable);

        new Handler().postDelayed(new Runnable() {
            @Override
            public void run() {
                Intent homeIntent = new Intent(SplashScreenActivity.this,MainActivity.class);
                startActivity(homeIntent);
                finish();
            }
        },3000);
    }
}
