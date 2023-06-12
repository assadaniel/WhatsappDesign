package com.example.whatsappdesign;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.util.ArrayList;
import java.util.List;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<Message> messageList;
    private ImageView imageView;
    private TextView textView;
    private ImageView backButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Accessing views from current_loggedin.xml layout
        imageView = findViewById(R.id.imageViewtalkingTo);
        textView = findViewById(R.id.textViewTalkingTo);
        backButton = findViewById(R.id.backButton);
        Intent activityIntent = getIntent();
        if(activityIntent!=null) {
            String displayName = activityIntent.getStringExtra("displayName");
            System.out.println(displayName);
            Log.d("ChatActivity", "DisplayName: " + displayName);
            int profilePic = activityIntent.getIntExtra("profilePic", R.drawable.ic_launcher_foreground);
            textView.setText(displayName);
           imageView.setImageResource(profilePic);

        }
        // Initialize the message list with some example data
        messageList = new ArrayList<>();
        messageList.add(new Message("Hello", "10:00 AM", true));
        messageList.add(new Message("Hi", "10:01 AM", false));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(messageList);
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                // For example, you can navigate back to the previous screen
                finish();
            }
        });
    }
}