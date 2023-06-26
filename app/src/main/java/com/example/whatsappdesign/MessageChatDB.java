package com.example.whatsappdesign;

import androidx.room.Database;
import androidx.room.RoomDatabase;

@Database(entities = {MessageChat.class}, version = 1)
public abstract class MessageChatDB extends RoomDatabase {
    public abstract MessageChatDao messageChatDao();
}

