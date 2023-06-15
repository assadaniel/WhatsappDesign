package com.example.whatsappdesign;

import static com.example.whatsappdesign.SettingsActivity.applyDarkMode;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.ListView;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {
    ListView listView;
    UserAdapter adapter;
    ImageView settings;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);
        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("The pro", R.drawable.roundskunk,"Hi","10:00"));
        }
        users.add(new User("The shmo", R.drawable.boatinthewater2,"YO","10:01"));
        listView = findViewById(R.id.list_view);
        adapter = new UserAdapter(getApplicationContext(),users);
        listView.setAdapter(adapter);
        settings = findViewById(R.id.settingsButtton);
        listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                Intent intent = new Intent(getApplicationContext(),ChatActivity.class);
                User u = users.get(position);
                intent.putExtra("displayName", u.getUserName());
                intent.putExtra("profilePic",u.getPictureId());
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
    }
}
