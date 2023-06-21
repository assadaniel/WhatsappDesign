package com.example.whatsappdesign;

import androidx.room.Entity;
import androidx.room.Ignore;
import androidx.room.PrimaryKey;
import androidx.room.TypeConverters;

import java.util.LinkedList;
import java.util.List;

@Entity(tableName = "messageChat")
@TypeConverters(MessageConvertor.class)
public class MessageChat {
    @PrimaryKey(autoGenerate = false)
    private int chatId;
    private List<Message> listMessage;
    @Ignore
    public MessageChat(int chatId, List<Message> listMessage) {
        this.chatId = chatId;
        this.listMessage = listMessage;
    }
    public MessageChat(int chatId) {
        this.chatId = chatId;
        this.listMessage = new LinkedList<>();
    }

    public int getChatId() {
        return chatId;
    }

    public void setChatId(int chatId) {
        this.chatId = chatId;
    }

    public List<Message> getListMessage() {
        return listMessage;
    }

    public void setListMessage(List<Message> listMessage) {
        this.listMessage = listMessage;
    }
}
