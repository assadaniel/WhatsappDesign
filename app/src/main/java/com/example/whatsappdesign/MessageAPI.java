package com.example.whatsappdesign;

import static com.example.whatsappdesign.MainActivity.baseURL;
import static com.example.whatsappdesign.UsersActivity.currentConnectedUsername;
import static com.example.whatsappdesign.UsersActivity.token;

import static java.util.Collections.reverse;

import android.util.Log;

import androidx.lifecycle.MutableLiveData;

import java.util.Calendar;
import java.util.List;
import java.util.Locale;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import com.example.whatsappdesign.MessageChatDao;
import com.example.whatsappdesign.MessageChat;

public class MessageAPI {
    private MutableLiveData<List<Message>> messageListData;
    Retrofit retrofit;
    WebServiceAPI webServiceAPI;

    private MessageChatDao messageChatDao;

    public MessageAPI(MutableLiveData<List<Message>> messageListData, MessageChatDao messageChatDao) {
        this.messageListData = messageListData;
        retrofit = new Retrofit.Builder().baseUrl(baseURL).
                addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
        this.messageChatDao = messageChatDao;
    }

    public void get(int id) {
        Call<List<Message>> call = webServiceAPI.getMessages(id,"Bearer "+token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                new Thread(()->{
                    List<Message> messages = response.body();
                    reverse(messages);
                    // Retrieve the existing MessageChat object with the given chatId
                    MessageChat messageChat = messageChatDao.get(id);

                    if (messageChat == null) {
                        // Create a new MessageChat object if it doesn't exist
                        messageChat = new MessageChat(id);
                        messageChat.setListMessage(messages);
                        Log.d("API", String.valueOf(id));
                        messageChatDao.insert(messageChat);
                    } else {
                        // Update the list of messages
                        messageChat.setListMessage(messages);

                        // Insert MessageChat object in the database
                        messageChatDao.update(messageChat);
                    }
                    messageListData.postValue(messages);
                }).start();

            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void add(int id,MessageToSend message, List<Message> messageList1) {
        Call<Void> call = webServiceAPI.postMessage(id,message,"Bearer "+token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {
                new Thread(()->{
//                    // Get the current time
//                    Calendar currentTime = Calendar.getInstance();
//
//                    // Extract the hour and minute values
//                    int hour = currentTime.get(Calendar.HOUR_OF_DAY);
//                    int minute = currentTime.get(Calendar.MINUTE);
//
//                    // Format the hour and minute values as strings
//                    String formattedHour = String.format(Locale.getDefault(), "%02d", hour);
//                    String formattedMinute = String.format(Locale.getDefault(), "%02d", minute);
//
//                    // Construct the formatted time string
//                    String formattedTime = formattedHour + ":" + formattedMinute;
//                    Message realMessage = new Message(message.getMsg(),
//                            formattedTime,new OnlyUsername(currentConnectedUsername));
//                    List<Message> messageList1 = messageListData.getValue();
//                    messageList1.add(realMessage);
                    MessageChat messageChat = messageChatDao.get(id);
                    messageChat.setListMessage(messageList1);
                    messageChatDao.update(messageChat);
//                    messageListData.postValue(messageList1);
                }).start();

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
