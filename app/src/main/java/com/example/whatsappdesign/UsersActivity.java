package com.example.whatsappdesign;

import static com.example.whatsappdesign.MainActivity.baseURL;
import static com.example.whatsappdesign.MainActivity.defaultPfp;
import static com.example.whatsappdesign.SettingsActivity.applyDarkMode;

import androidx.activity.result.ActivityResultLauncher;
import androidx.activity.result.contract.ActivityResultContracts;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;

import android.app.Activity;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Bundle;
import android.util.Base64;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.Objects;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UsersActivity extends AppCompatActivity {
    ListView listView;
    UserAdapter adapter;
    ImageView settings;
    public static String token;
    String currentConnectedUsername;
    ImageView pfpCurrentLoggedIn;
    TextView displayNameCurrentLoggedIn;
    private UsersViewModel viewModel;
    private FloatingActionButton addButton;
    private ActivityResultLauncher<Intent> launcher = registerForActivityResult(
            new ActivityResultContracts.StartActivityForResult(), result -> {
                if(result.getResultCode()== Activity.RESULT_OK) {
                    Intent data = result.getData();
                    String username = data.getStringExtra("Username");
                    viewModel.add(username,getApplicationContext());
//                    Toast.makeText(getApplicationContext(),errorData.getErrorString(),
//                            Toast.LENGTH_SHORT).show();

                }
            }
    );
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        displayNameCurrentLoggedIn = findViewById(R.id.textViewCurrentLoggedIn);
        pfpCurrentLoggedIn = findViewById(R.id.imageViewCurrentLoggedIn);
        Intent activityIntent = getIntent();
        if(activityIntent!=null){
            token = activityIntent.getStringExtra("Token");
            currentConnectedUsername = activityIntent.getStringExtra("Username");
            getDisplayNameAndProfilePicture();
        }
//        ArrayList<User> users = new ArrayList<>();
//        for (int i = 0; i < 10; i++) {
//            users.add(new User("The pro", R.drawable.roundskunk,"Hi","10:00"));
//        }
//        users.add(new User("The shmo", R.drawable.boatinthewater2,"YO","10:01"));
        viewModel = new ViewModelProvider(this).get(UsersViewModel.class);
        listView = findViewById(R.id.list_view);
        adapter = new UserAdapter(getApplicationContext(),viewModel.get().getValue());
        listView.setAdapter(adapter);
        settings = findViewById(R.id.settingsButtton);
        addButton = findViewById(R.id.btnAdd);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                User u = viewModel.get(position);
                intent.putExtra("displayName", u.getUser().getUsername());
                intent.putExtra("profilePic",u.getUser().getProfilePic());
                startActivity(intent);
            }

        });
        settings.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), SettingsActivity.class);
                startActivity(intent);
            }
        });

        viewModel.get().observe(this, users -> {
            adapter = new UserAdapter(getApplicationContext(),users);
            listView.setAdapter(adapter);
        });
        addButton.setOnClickListener(v -> {

            Intent intent = new Intent(getApplicationContext(), AddUserActivity.class);
            launcher.launch(intent);
        });

    }

    private void getDisplayNameAndProfilePicture() {
        Retrofit retrofit = new Retrofit.Builder().baseUrl(baseURL)
                .addConverterFactory(GsonConverterFactory.create()).build();
        WebServiceAPI webServiceAPI = retrofit.create(WebServiceAPI.class);
        Call<UserCreatePost> call = webServiceAPI.getUser(currentConnectedUsername,
                "Bearer "+token);
        call.enqueue(new Callback<UserCreatePost>() {
            @Override
            public void onResponse(Call<UserCreatePost> call, Response<UserCreatePost> response) {
                if(response.isSuccessful()) {
                    UserCreatePost user = response.body();
                    setAsImage(user.getProfilePic(), pfpCurrentLoggedIn);
                    displayNameCurrentLoggedIn.setText(user.getDisplayName());
                }
            }

            @Override
            public void onFailure(Call<UserCreatePost> call, Throwable t) {

            }
        });
    }

    public static void setAsImage(String strBase64, ImageView imageView) {
        if(strBase64.equals(defaultPfp)){
            imageView.setImageResource(R.drawable.defaultprofilepic);
        } else {
            byte[] decodedString = Base64.decode(strBase64, Base64.DEFAULT);
            Bitmap decodedByte = BitmapFactory.decodeByteArray(decodedString, 0, decodedString.length);
            imageView.setImageBitmap(decodedByte);
        }
    }
}
