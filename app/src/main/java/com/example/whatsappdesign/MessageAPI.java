package com.example.whatsappdesign;

import static com.example.whatsappdesign.MainActivity.baseURL;
import static com.example.whatsappdesign.UsersActivity.token;

import static java.util.Collections.reverse;

import androidx.lifecycle.MutableLiveData;

import java.util.List;

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

    //////////////////////////
    private MessageChatDao messageChatDao;

    public MessageAPI(MutableLiveData<List<Message>> messageListData, MessageChatDao messageChatDao) {
        this.messageListData = messageListData;
        retrofit = new Retrofit.Builder().baseUrl(baseURL).
                addConverterFactory(GsonConverterFactory.create()).build();
        webServiceAPI = retrofit.create(WebServiceAPI.class);
//////////////////////
        this.messageChatDao = messageChatDao;
    }

    public void get(int id) {
        Call<List<Message>> call = webServiceAPI.getMessages(id,"Bearer "+token);
        call.enqueue(new Callback<List<Message>>() {
            @Override
            public void onResponse(Call<List<Message>> call, Response<List<Message>> response) {
                List<Message> messages = response.body();
                reverse(messages);
                messageListData.setValue(messages);
            }

            @Override
            public void onFailure(Call<List<Message>> call, Throwable t) {

            }
        });
    }

    public void add(int id,MessageToSend message) {
        Call<Void> call = webServiceAPI.postMessage(id,message,"Bearer "+token);
        call.enqueue(new Callback<Void>() {
            @Override
            public void onResponse(Call<Void> call, Response<Void> response) {

            }

            @Override
            public void onFailure(Call<Void> call, Throwable t) {

            }
        });
    }
}
