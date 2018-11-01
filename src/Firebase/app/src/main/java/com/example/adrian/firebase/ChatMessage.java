package com.example.adrian.firebase;

import java.util.Date;


//Object for Message.
public class ChatMessage {

    private String messageText;
    private String messageUser;
    private long messageTime;
    private String token;

    //Message Instance.
    public ChatMessage(String messageText, String messageUser,String token) {
        this.messageText = messageText;
        this.messageUser = messageUser;
        this.token = token;


        // Initialize to current time
        messageTime = new Date().getTime();
    }
	
    //Constructor
    public ChatMessage() {

    }
    
	//get message
    public String getMessageText() {
        return messageText;
    }
	
	//get token
    public String getToken() {
        return token;
    }
    public void setMessageText(String messageText) {
        this.messageText = messageText;
    }
    //get message
    public String getMessageUser() {
        return messageUser;
    }


    //set message 
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
    //get message time
    public long getMessageTime() {
        return messageTime;
    }
     // set message time
    public void setMessageTime(long messageTime) {
        this.messageTime = messageTime;
    }


}