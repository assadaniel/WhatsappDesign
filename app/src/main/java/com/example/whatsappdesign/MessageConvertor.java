package com.example.whatsappdesign;

import androidx.room.TypeConverter;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import java.lang.reflect.Type;
import java.util.List;

public class MessageConvertor {
    @TypeConverter
    public String fromMessageList(List<Message> messageList) {
        if (messageList == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {}.getType();
        return gson.toJson(messageList, type);
    }

    @TypeConverter
    public List<Message> toMessageList(String messageListString) {
        if (messageListString == null) {
            return null;
        }

        Gson gson = new Gson();
        Type type = new TypeToken<List<Message>>() {}.getType();
        return gson.fromJson(messageListString, type);
    }
}

