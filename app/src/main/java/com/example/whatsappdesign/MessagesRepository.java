package com.example.whatsappdesign;

import static com.example.whatsappdesign.UsersActivity.currentConnectedUsername;

import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;

public class MessagesRepository {
    private MessageListData messageListData;

    private MessageAPI api;

    private int id = -1;

    public MessagesRepository(int id){
        messageListData = new MessageListData();
        api = new MessageAPI(messageListData);
        this.id = id;
    }

    public void add(MessageToSend message) {
        api.add(id,message);
        // Get the current time
        Calendar currentTime = Calendar.getInstance();

        // Extract the hour and minute values
        int hour = currentTime.get(Calendar.HOUR_OF_DAY);
        int minute = currentTime.get(Calendar.MINUTE);

        // Format the hour and minute values as strings
        String formattedHour = String.format(Locale.getDefault(), "%02d", hour);
        String formattedMinute = String.format(Locale.getDefault(), "%02d", minute);

        // Construct the formatted time string
        String formattedTime = formattedHour + ":" + formattedMinute;
        Message realMessage = new Message(message.getMsg(),
                formattedTime,new OnlyUsername(currentConnectedUsername));
        List<Message> messageList1 = messageListData.getValue();
        messageList1.add(realMessage);
        messageListData.setValue(messageList1);
    }

    class MessageListData extends MutableLiveData<List<Message>> {
        public MessageListData(){
            super();
            setValue(new LinkedList<>());
        }

        @Override
        protected void onActive() {
            super.onActive();
            api.get(id);
        }
    }
    public void refresh() {
        Log.d("abcde_refresh", String.valueOf(id));
        api.get(id);
    }
    public LiveData<List<Message>> getAll() {return messageListData;}
}
