package com.example.whatsappdesign;
import static com.example.whatsappdesign.ChatAdapter.formatDateToString;
import static com.example.whatsappdesign.ChatAdapter.parseDateString;
import static com.example.whatsappdesign.UsersActivity.setAsImage;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class UserAdapter extends ArrayAdapter<User> {
    LayoutInflater inflater;
    List<User> userList;
    private static String inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static String outputFormat = "HH:mm";
    public UserAdapter(Context ctx, List<User> userArrayList) {
        super(ctx, R.layout.custom_list_item, userArrayList);
        this.userList = userArrayList;
        this.inflater = LayoutInflater.from(ctx);
    }

    @NonNull
    @Override
    public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {

        User user = getItem(position);

        if (convertView == null) {
            convertView = inflater.inflate(R.layout.custom_list_item, parent, false);
        }

        ImageView imageView = convertView.findViewById(R.id.profile_image);
        TextView userName = convertView.findViewById(R.id.user_name);
        TextView lastMsg = convertView.findViewById(R.id.last_massage);
        TextView time = convertView.findViewById(R.id.time);
        if(user.getLastMessage()==null) {
            lastMsg.setText("");
            time.setText("");
        } else {
            lastMsg.setText(user.getLastMessage().getContent());
            Date date = parseDateString(user.getLastMessage().getCreated(), inputFormat);
            String formattedTime = formatDateToString(date, outputFormat);
            time.setText(formattedTime);
        }
        setAsImage(user.getUser().getProfilePic(),imageView);
        userName.setText(user.getUser().getDisplayName());

        return convertView;
    }


}