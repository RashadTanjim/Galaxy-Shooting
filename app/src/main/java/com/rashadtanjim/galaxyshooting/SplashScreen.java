package com.rashadtanjim.galaxyshooting;


import android.content.Intent;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.google.firebase.auth.FirebaseAuth;

public class SplashScreen extends AppCompatActivity {
    FirebaseAuth auth;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_splash);
        auth = FirebaseAuth.getInstance();

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
        //check if the user has already signed in or not
        if(auth.getCurrentUser()!=null){
            Intent intent = new Intent(SplashScreen.this, MainActivity.class);
            startActivity(intent);
            finish();   //Remove activity
        }else{
            background.start();   // start thread
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
    }
}