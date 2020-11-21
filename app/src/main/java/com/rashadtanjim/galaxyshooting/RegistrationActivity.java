package com.rashadtanjim.galaxyshooting;

import android.content.Intent;
import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import android.view.View;
import android.widget.Button;

public class RegistrationActivity extends AppCompatActivity {

    public void onSignUp(View view) {
        Button signUp = (Button) findViewById(R.id.reg_signup);

        Intent regSignUp = new Intent(RegistrationActivity.this, SignUpActivity.class);
        startActivity(regSignUp);
        finish();
    }

    public void onlogin(View view) {
        Button login = (Button) findViewById(R.id.reg_login);
        Intent regLogin = new Intent(RegistrationActivity.this, LoginHandler.class);
        startActivity(regLogin);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
    }


}
