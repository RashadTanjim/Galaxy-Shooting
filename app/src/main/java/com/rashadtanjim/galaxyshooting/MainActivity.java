package com.rashadtanjim.galaxyshooting;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import androidx.appcompat.app.AppCompatActivity;

public class MainActivity extends AppCompatActivity {

    FirebaseAuth auth;
    public Button pressButton, showScoreButton, changeInfo;
    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        auth = FirebaseAuth.getInstance();

        pressButton = findViewById(R.id.buttonPressed);
        showScoreButton = findViewById(R.id.buttonScored);
        changeInfo = (Button) findViewById(R.id.Change_info);

        changeInfo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, LoginActivity.class));
                finish();
            }
        });

        pressButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                playGame();
            }
        });
        showScoreButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                showGameScore();
            }
        });
    }



    public void playGame() {
        Intent intent = new Intent(this, GameActivity.class);
//      intent.putExtra("name", editText.getText().toString());
        startActivity(intent);
    }

    public void showGameScore() {
        Intent intent = new Intent(this, ScoreActivity.class);
        startActivity(intent);
    }

}
