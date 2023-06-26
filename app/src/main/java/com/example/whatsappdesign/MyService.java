package com.example.whatsappdesign;

import static com.google.firebase.messaging.Constants.TAG;


import android.app.Service;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.IBinder;
import android.util.Log;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.app.NotificationCompat;
import androidx.core.app.NotificationManagerCompat;

import com.google.firebase.messaging.FirebaseMessagingService;
import com.google.firebase.messaging.RemoteMessage;

public class MyService extends FirebaseMessagingService {

    public MyService() {
    }


    private int notificationIdCounter = 0;

    @RequiresApi(api = Build.VERSION_CODES.TIRAMISU)
    @Override
    public void onMessageReceived(RemoteMessage remoteMessage) {
        Log.d("message_received_qwerty", "hello");
        // Check if the message contains a notification payload
        if (remoteMessage.getNotification() != null) {
            // Handle the notification message
            String title = remoteMessage.getNotification().getTitle();
            String body = remoteMessage.getNotification().getBody();

            // Create and display a notification using the NotificationCompat.Builder
            NotificationCompat.Builder builder = new NotificationCompat.Builder(this, "1")
                    .setContentTitle(title)
                    .setContentText(body)
                    .setSmallIcon(R.drawable.defaultprofilepic)
                    .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
                    .setAutoCancel(true);

            // Check if the app has the required permission to post notifications
            if (ActivityCompat.checkSelfPermission(this, android.Manifest.permission.POST_NOTIFICATIONS) != PackageManager.PERMISSION_GRANTED) {
                return;
            }

            // Show the notification
            Log.d("abcdf", "before_show_notification");

            NotificationManagerCompat notificationManager = NotificationManagerCompat.from(this);
            int notificationId = notificationIdCounter++;
            notificationManager.notify(notificationId, builder.build());
            Log.d("abcdf", "notification");
            int a = 1;
        }
    }

}
