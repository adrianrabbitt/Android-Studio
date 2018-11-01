package com.example.adrian.firebase;

import java.util.Date;

//Object For Contact
public class Contact {


    private long messageTime;
    private String messageUser;

    private String ouid;
    private String name;

    public Contact(String messageUser, String uid) {
        this.messageUser = messageUser;
        this.ouid = uid;
        messageTime = new Date().getTime();
        // Initialize to current time

    }

    public Contact() {

    }


    public String getMessageUser() {
        return messageUser;
    }

    public long getMessagetime() {
        return messageTime;
    }

    public String getOuid() {
        return ouid;
    }


}

