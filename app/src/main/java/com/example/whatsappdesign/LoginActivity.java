package com.example.whatsappdesign;

import static com.example.whatsappdesign.MainActivity.baseURL;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
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
    private Button loginButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        username = findViewById(R.id.loginusername);
        password = findViewById(R.id.loginpassword);
        errors = findViewById(R.id.errorMessageLogin);
        loginButton = findViewById(R.id.loginButton);
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
                        intent.putExtra("Token",response.body());
                        intent.putExtra("Username",usernameString);
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
    }
}