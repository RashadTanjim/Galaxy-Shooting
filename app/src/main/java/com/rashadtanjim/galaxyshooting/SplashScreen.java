package com.rashadtanjim.galaxyshooting;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

public class SplashScreen extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);

        Thread background = new Thread() {
            public void run() {

                try {
                    sleep(2 * 1000); // Thread will sleep for 2 seconds

                    // After 5 seconds redirect to another intent
                    Intent intent = new Intent(SplashScreen.this, RegistrationActivity.class);
                    startActivity(intent);
                    finish();   //Remove activity

                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        };
        // start thread
        background.start();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}