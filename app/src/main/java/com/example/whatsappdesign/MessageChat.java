package com.example.whatsappdesign;

import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.LinkedList;
import java.util.List;

@Entity
public class MessageChat {
    @PrimaryKey(autoGenerate = false)
    private int chatId;
    private List<Message> listMessage;

    public MessageChat(int chatId, List<Message> listMessage) {
        this.chatId = chatId;
        this.listMessage = listMessage;
    }
    public MessageChat(int chatId) {
        this.chatId = chatId;
        this.listMessage = new LinkedList<>();
    }
}
