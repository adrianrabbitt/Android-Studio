package com.example.adrian.firebase;

import java.util.Date;

//Object For Contact
public class Contact {


    private long messageTime;
    private String messageUser;

    private String ouid;
    private String name;
    //Constructor for Contact Object
    public Contact(String messageUser, String uid) {
        this.messageUser = messageUser;
        this.ouid = uid;
        messageTime = new Date().getTime();
        

    }

    public Contact() {

    }

    //get Message
    public String getMessageUser() {
        return messageUser;
    }
    //set Message
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }
    //get Message
    public long getMessagetime() {
        return messageTime;
    }
    //get uid
    public String getOuid() {
        return ouid;
    }


}

