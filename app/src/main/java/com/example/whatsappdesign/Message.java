package com.example.whatsappdesign;

public class Message {
    private String text;
    private String time;
    private boolean isSender;

    public Message(String text, String time, boolean isSender) {
        this.text = text;
        this.time = time;
        this.isSender = isSender;
    }

    public String getText() {
        return text;
    }

    public String getTime() {
        return time;
    }

    public boolean isSender() {
        return isSender;
    }
}
