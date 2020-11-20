package com.rashadtanjim.galaxyshooting;

        import android.content.Intent;
        import android.os.Bundle;
        import androidx.appcompat.app.AppCompatActivity;
        import androidx.appcompat.widget.Toolbar;
        import android.view.View;
        import android.widget.Button;

public class Registration extends AppCompatActivity {

    public void onsignup(View view) {
        Button signup = (Button) findViewById(R.id.reg_signup);

        Intent regsignup = new Intent(Registration.this, Signup.class);
        startActivity(regsignup);
        finish();
    }
    public void onlogin(View view) {
        Button login = (Button) findViewById(R.id.reg_login);
        Intent reglogin = new Intent(Registration.this, Login.class);
        startActivity(reglogin);
        finish();
    }


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_registration);
//        Toolbar toolbar = findViewById(R.id.toolbar);
//        setSupportActionBar(toolbar);


    }



}
