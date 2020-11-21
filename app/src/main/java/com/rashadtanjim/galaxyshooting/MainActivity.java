package com.rashadtanjim.galaxyshooting;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class MainActivity extends AppCompatActivity {

//    private EditText editText;
//    private Button pressButton;

    // Write a message to the database
    FirebaseDatabase database = FirebaseDatabase.getInstance();
    DatabaseReference myRef = database.getReference("users");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

//        editText = findViewById(R.id.pressText);
//        pressButton = findViewById(R.id.buttonPressed);
//
//        pressButton.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                pressed();
//            }
//        });
    }

//    private void pressed() {
//        myRef.child("id").child("1").
//                setValue(editText.getText().toString());
//
//    }


}
