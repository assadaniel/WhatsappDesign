package com.example.whatsappdesign;

import static com.example.whatsappdesign.MainActivity.baseURL;
import static com.example.whatsappdesign.MainActivity.SIM;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {
    private EditText username, password;
    private TextView errors;
    private Button loginButton, signupButton;
    private ImageView settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        SharedPreferences sharedPreferences = getSharedPreferences("MyPrefs",
                Context.MODE_PRIVATE);

        username = findViewById(R.id.loginusername);
        password = findViewById(R.id.loginpassword);
        errors = findViewById(R.id.errorMessageLogin);
        loginButton = findViewById(R.id.loginButton);
        signupButton = findViewById(R.id.signupinlogin);
        settings = findViewById(R.id.settingsButttonLogin);
        loginButton.setOnClickListener(view -> {
            String usernameString = username.getText().toString().trim();
            String passwordString = password.getText().toString().trim();
            Gson gson = new GsonBuilder()
                    .setLenient()
                    .create();
            Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                    .addConverterFactory(GsonConverterFactory.create(gson)).build();
            WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
            UserCreateToken userCreateToken =
                    new UserCreateToken(usernameString,
                            passwordString);
            Call<String> call = webServiceAPI.getToken(userCreateToken);
            call.enqueue(new Callback<String>() {
                @Override
                public void onResponse(Call<String> call, Response<String> response) {
                    if(response.isSuccessful()){
                        Intent intent = new Intent(getApplicationContext(),UsersActivity.class);
                        String tokenNow = response.body();
                        intent.putExtra("Token",tokenNow);
                        intent.putExtra("Username",usernameString);
                        // Save the user information during login
                        SharedPreferences.Editor editor = sharedPreferences.edit();
                        editor.putString("username", usernameString); // Store the username
                        editor.putString("token",tokenNow); // Store the token
                        editor.apply();
                        SIM.logIn(usernameString);
                        startActivity(intent);
                    } else {
                        errors.setText(R.string.invalid_username_or_password);
                        errors.setVisibility(View.VISIBLE);
                    }
                }

                @Override
                public void onFailure(Call<String> call, Throwable t) {
                    errors.setText(R.string.request_to_server_failed);
                    errors.setVisibility(View.VISIBLE);
                }
            });

        });
        signupButton.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SignupActivity.class);
            startActivity(intent);
        });
        settings.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            startActivity(intent);
        });
    }
}