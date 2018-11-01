package com.example.adrian.firebase;

import java.util.Date;


//Object for Message.
public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private String token;


    public ChatMessage(String messageText, String messageUser, String token) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.token = token;


        // Initialize to current time
        messageTime = new Date().getTime();
    }

    public ChatMessage() {

    }

    public String getMessageText() {
        return messageText;
    }


    public String getMessageUser() {
        return messageUser;
    }





}