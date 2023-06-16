package com.example.whatsappdesign;

import static com.example.whatsappdesign.UsersActivity.currentConnectedUsername;
import static com.example.whatsappdesign.UsersActivity.setAsImage;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Locale;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    private ChatAdapter adapter;
    private List<Message> messageList;
    private ImageView imageView;
    private TextView textView;
    private ImageView backButton;

    private MessagesViewModel viewModel;

    private Button sendButton;

    private EditText messageBox;

    private int id;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_chat);
        // Accessing views from current_loggedin.xml layout
        imageView = findViewById(R.id.imageViewtalkingTo);
        textView = findViewById(R.id.textViewTalkingTo);
        backButton = findViewById(R.id.backButton);
        sendButton = findViewById(R.id.sendButton);
        messageBox = findViewById(R.id.editTextChat);
        Intent activityIntent = getIntent();
        if(activityIntent!=null) {
            String displayName = activityIntent.getStringExtra("displayName");
            id = activityIntent.getIntExtra("id",0);
            String profilePic = activityIntent.getStringExtra("profilePic");
            textView.setText(displayName);
           setAsImage(profilePic,imageView);

        }
        viewModel = new ViewModelProvider(this).get(MessagesViewModel.class);
        viewModel.init(id);
        // Initialize the message list with some example data
//        messageList = new ArrayList<>();
//        messageList.add(new Message("Hello", "10:00 AM", true));
//        messageList.add(new Message("Hi", "10:01 AM", false));

        recyclerView = findViewById(R.id.recyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new ChatAdapter(viewModel.get().getValue());
        recyclerView.setAdapter(adapter);

        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // Handle the click event here
                // For example, you can navigate back to the previous screen
                finish();
            }
        });
        viewModel.get().observe(this,messageList->{
            adapter.setMessages(messageList);
            recyclerView.postDelayed(() -> {
                if (messageList.size() > 0) {
                    recyclerView.smoothScrollToPosition(messageList.size() - 1);
                }
            }, 100); // Delay of 100 milliseconds (adjust as needed)
        });

        sendButton.setOnClickListener(v -> {
//            // Get the current time
//            Calendar currentTime = Calendar.getInstance();
//
//            // Extract the hour and minute values
//            int hour = currentTime.get(Calendar.HOUR_OF_DAY);
//            int minute = currentTime.get(Calendar.MINUTE);
//
//            // Format the hour and minute values as strings
//            String formattedHour = String.format(Locale.getDefault(), "%02d", hour);
//            String formattedMinute = String.format(Locale.getDefault(), "%02d", minute);
//
//            // Construct the formatted time string
//            String formattedTime = formattedHour + ":" + formattedMinute;
//            Message message = new Message(messageBox.getText().toString(),
//                    formattedTime,new OnlyUsername(currentConnectedUsername));
            viewModel.add(new MessageToSend(messageBox.getText().toString()));
            messageBox.setText("");
            recyclerView.smoothScrollToPosition(viewModel.get().getValue().size()-1);

        });
    }
}