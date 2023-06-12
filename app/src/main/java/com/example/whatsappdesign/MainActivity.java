package com.example.whatsappdesign;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    ListView listView;
    UserAdapter adapter;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<User> users = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            users.add(new User("The pro", R.drawable.roundskunk,"Hi","10:00"));
        }
        users.add(new User("The shmo", R.drawable.boatinthewater2,"YO","10:01"));
        listView = findViewById(R.id.list_view);
        adapter = new UserAdapter(getApplicationContext(),users);
        listView.setAdapter(adapter);
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
    }
}