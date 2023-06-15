package com.example.whatsappdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginusername);
        password = findViewById(R.id.loginpassword);
        loginButton = findViewById(R.id.loginButton);
        loginButton.setOnClickListener(view -> {
            Intent intent = new Intent(getApplicationContext(), UsersActivity.class);
            startActivity(intent);
        });
    }
}