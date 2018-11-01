package com.example.adrian.firebase;

import java.util.Date;

public class Ratingc {


    private long messageTime;
    private String messageUser;

    private String ouid;
    private String sName;
    float rating;

    public Ratingc(String messageUser,String uid,String sname,float rating) {
        this.messageUser = messageUser;
        this.ouid = uid;
        this.sName =sname;
        messageTime = new Date().getTime();
        this.rating = rating;
        // Initialize to current time

    }

    public Ratingc(){

    }





    public String getMessageUser() {
        return messageUser;
    }
    public void setMessageUser(String messageUser) {
        this.messageUser = messageUser;
    }


    public long getMessagetime(){return messageTime;}
    public float getRating(){return rating;}
    public String getsName(){
        return sName;
    }
    public String getOuid(){return ouid;}


}

