package com.example.whatsappdesign;

import androidx.room.TypeConverter;
import com.google.gson.Gson;

public class MessageDetailsConverter {

    @TypeConverter
    public static User.MessageDetails fromString(String value) {
        return new Gson().fromJson(value, User.MessageDetails.class);
    }

    @TypeConverter
    public static String toString(User.MessageDetails messageDetails) {
        return new Gson().toJson(messageDetails);
    }
}

