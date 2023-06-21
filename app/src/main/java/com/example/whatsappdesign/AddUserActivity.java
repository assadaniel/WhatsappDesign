package com.example.whatsappdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class AddUserActivity extends AppCompatActivity {
    private EditText username;
    private Button add;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_user);
        username=findViewById(R.id.adduserusername);
        add = findViewById(R.id.ButtonAddUser);
        add.setOnClickListener(v -> {
            Intent resultIntent = new Intent();
            resultIntent.putExtra("Username",username.getText().toString());
            setResult(Activity.RESULT_OK,resultIntent);
            finish();
        });

    }
}