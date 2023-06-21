package com.example.whatsappdesign;

import static com.example.whatsappdesign.UsersActivity.currentConnectedUsername;

import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Locale;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.MessageViewHolder> {
    private List<Message> messageList;
    private static String inputFormat = "yyyy-MM-dd'T'HH:mm:ss.SSS'Z'";
    private static String outputFormat = "HH:mm";


    public ChatAdapter(List<Message> messageList) {
        this.messageList = messageList;
    }

    @NonNull
    @Override
    public MessageViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext())
                .inflate(R.layout.item_message, parent, false);
        return new MessageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageViewHolder holder, int position) {
        Message message = messageList.get(position);
        holder.textMessage.setText(message.getContent());
        if(message.getCreated().length()>5) {
            Date date = parseDateString(message.getCreated(), inputFormat);
            String formattedTime = formatDateToString(date, outputFormat);

            holder.textTime.setText(formattedTime);
        } else {
            holder.textTime.setText(message.getCreated());
        }


        // Set the message background color and alignment based on sender/receiver
        boolean sender = message.getSender().getUsername().equals(currentConnectedUsername);
        if (sender) {
            holder.textMessage.setBackgroundResource(R.drawable.bg_message_sender);
            LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            messageParams.gravity = Gravity.END; // Set gravity to end for sender
            holder.textMessage.setLayoutParams(messageParams);

            LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            timeParams.gravity = Gravity.END; // Set gravity to end for sender
            holder.textTime.setLayoutParams(timeParams);
        } else {
            holder.textMessage.setBackgroundResource(R.drawable.bg_message_receiver);
            LinearLayout.LayoutParams messageParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            messageParams.gravity = Gravity.START; // Set gravity to start for receiver
            holder.textMessage.setLayoutParams(messageParams);

            LinearLayout.LayoutParams timeParams = new LinearLayout.LayoutParams(
                    LinearLayout.LayoutParams.WRAP_CONTENT,
                    LinearLayout.LayoutParams.WRAP_CONTENT);
            timeParams.gravity = Gravity.START; // Set gravity to start for receiver
            holder.textTime.setLayoutParams(timeParams);
        }


    }
    public static Date parseDateString(String dateString, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        try {
            return dateFormat.parse(dateString);
        } catch (ParseException e) {
            e.printStackTrace();
            return null;
        }
    }

    public static String formatDateToString(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format, Locale.getDefault());
        return dateFormat.format(date);
    }

    @Override
    public int getItemCount() {
        return messageList.size();
    }

    public void setMessages(List<Message> messageList) {
        this.messageList = messageList;
        notifyDataSetChanged();
    }

    public static class MessageViewHolder extends RecyclerView.ViewHolder {
        TextView textMessage, textTime;

        public MessageViewHolder(@NonNull View itemView) {
            super(itemView);
            textMessage = itemView.findViewById(R.id.textMessage);
            textTime = itemView.findViewById(R.id.textTime);
        }
    }
}

