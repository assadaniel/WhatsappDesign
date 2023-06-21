package com.example.whatsappdesign;

import static com.example.whatsappdesign.MainActivity.baseURL;
import static com.example.whatsappdesign.MainActivity.defaultPfp;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.TextUtils;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.regex.Pattern;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class SignupActivity extends AppCompatActivity {
    private static final int PERMISSION_REQUEST_CODE = 123;
    private EditText usernameEditText, passwordEditText, passwordValidationEditText, displayNameEditText;
    private TextView errors;
    private ImageView profilePictureImageView;
    private Uri selectedImageUri = null;
    private Bitmap bitmap;
    private ImageView settings;
    private final ActivityResultLauncher<String[]> imagePickerLauncher =
            registerForActivityResult(new ActivityResultContracts.OpenDocument(), result -> {
                if (result != null) {
                    selectedImageUri = result;
                    try {
                        bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
                        profilePictureImageView.setVisibility(View.VISIBLE);
                        profilePictureImageView.setImageBitmap(bitmap);
                    } catch (IOException e) {
                        Log.e("SignupActivity", "Error loading image: " + e.getMessage());
                    }
                }
            });
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        usernameEditText = findViewById(R.id.usernameEditText);
        passwordEditText = findViewById(R.id.passwordEditText);
        passwordValidationEditText = findViewById(R.id.passwordValidationEditText);
        displayNameEditText = findViewById(R.id.displayNameEditText);
        profilePictureImageView = findViewById(R.id.profilePictureImageView);
        errors = findViewById(R.id.errorMessage);
        Button profilePictureButton = findViewById(R.id.profilePictureButton);
        Button signUpButton = findViewById(R.id.signUpButton);
        Button alreadyRegistered = findViewById(R.id.alreadySignedIn);
        settings = findViewById(R.id.settingsButttonSignup);

//        imagePickerLauncher = registerForActivityResult(new ActivityResultContracts.GetContent(),
//                (ActivityResultCallback<Uri>) result -> {
//                    if (result != null) {
//                        selectedImageUri = result;
//                        try {
//                            Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), selectedImageUri);
//                            profilePictureImageView.setVisibility(View.VISIBLE);
//                            profilePictureImageView.setImageBitmap(bitmap);
//                        } catch (IOException e) {
//                            Log.e("MainActivity", "Error loading image: " + e.getMessage());
//                        }
//                    }
//                });



        profilePictureButton.setOnClickListener(v -> {
            selectProfilePicture();
        });

        signUpButton.setOnClickListener(v -> signUp());

        alreadyRegistered.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
            startActivity(intent);
        });

        settings.setOnClickListener(v -> {
            Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
            intent.putExtra("flag",1);
            startActivity(intent);
        });
    }
    private String bitmapToBase64(Bitmap bitmap) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        Bitmap bitmap1 = Bitmap.createScaledBitmap(bitmap,100,100,false);
        bitmap1.compress(Bitmap.CompressFormat.PNG, 0, byteArrayOutputStream);
        byte[] imageBytes = byteArrayOutputStream.toByteArray();
        return Base64.encodeToString(imageBytes, Base64.DEFAULT);
    }


    private void selectProfilePicture() {
        Intent intent = new Intent(Intent.ACTION_OPEN_DOCUMENT);
        intent.addCategory(Intent.CATEGORY_OPENABLE);
        intent.setType("image/*");
        imagePickerLauncher.launch(new String[]{"image/*"});
    }


    private void signUp(){
        String username = usernameEditText.getText().toString().trim();
        String password = passwordEditText.getText().toString().trim();
        String passwordValidation = passwordValidationEditText.getText().toString().trim();
        String displayName = displayNameEditText.getText().toString().trim();

        // Perform validation checks
        if (TextUtils.isEmpty(username)) {
            usernameEditText.setError("Please enter a username");
            return;
        }

        if (TextUtils.isEmpty(password)) {
            passwordEditText.setError("Please enter a password");
            return;
        }
        if(!checkPassword(password,passwordEditText)){
            return;
        }

        if (!password.equals(passwordValidation)) {
            passwordValidationEditText.setError("Password does not match");
            return;
        }

        if (TextUtils.isEmpty(displayName)) {
            displayNameEditText.setError("Please enter a display name");
            return;
        }
        if (displayName.length() > 10) {
            displayNameEditText.setError("Display name must be up to 10 characters");
            return;
        }
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        String pfp=defaultPfp;
        if(selectedImageUri!=null){
            pfp=bitmapToBase64(bitmap);
        }
        UserCreatePost userCreatePost = new UserCreatePost(username,password,displayName,pfp);
        Call<Void> call = webServiceAPI.createUser(userCreatePost);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                if(response.isSuccessful()){
                    Intent intent = new Intent(getApplicationContext(),LoginActivity.class);
                    startActivity(intent);
                } else {
                    errors.setText(R.string.username_already_taken_or_error);
                    errors.setVisibility(View.VISIBLE);
                }
            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {
                errors.setText(R.string.error_with_request_to_server);
                errors.setVisibility(View.VISIBLE);

            }
        });

    }
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);

        if (requestCode == PERMISSION_REQUEST_CODE) {
            if (grantResults.length > 0 && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                selectProfilePicture();
            } else {
                Log.d("MainActivity", "Permission denied");
            }
        }
    }

    public boolean checkPassword(String password, EditText passwordEditText) {
        if (password.length() < 6) {
            passwordEditText.setError("Password must be at least 6 characters long.");
            return false;
        }

        // to check space
        if (password.contains(" ")) {
            passwordEditText.setError("Password cannot contain spaces.");
            return false;
        }

        // for digits from 0 to 9
        boolean hasDigit = false;
        for (int i = 0; i <= 9; i++) {
            if (password.contains(Integer.toString(i))) {
                hasDigit = true;
                break;
            }
        }
        if (!hasDigit) {
            passwordEditText.setError("Password must contain at least one digit.");
            return false;
        }

        // for special characters
        if (!Pattern.compile("[^A-Za-z0-9]").matcher(password).find()) {
            passwordEditText.setError("Password must contain at least one special character.");
            return false;
        }

        // for capital letters
        boolean hasUpperCase = false;
        for (int i = 65; i <= 90; i++) {
            if (password.indexOf((char) i) != -1) {
                hasUpperCase = true;
                break;
            }
        }
        if (!hasUpperCase) {
            passwordEditText.setError("Password must contain at least one uppercase letter.");
            return false;
        }

        // for small letters
        boolean hasLowerCase = false;
        for (int i = 97; i <= 122; i++) {
            if (password.indexOf((char) i) != -1) {
                hasLowerCase = true;
                break;
            }
        }
        if (!hasLowerCase) {
            passwordEditText.setError("Password must contain at least one lowercase letter.");
            return false;
        }

        // if all conditions pass, password is good.
        return true;
    }
}