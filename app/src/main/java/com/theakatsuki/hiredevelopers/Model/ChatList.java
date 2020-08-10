package com.theakatsuki.hiredevelopers.Model;

public class ChatList {
    String id ;
    String receiver;

    public ChatList(String id, String receiver) {
        this.id = id;
        this.receiver = receiver;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getReceiver() {
        return receiver;
    }

    public void setReceiver(String receiver) {
        this.receiver = receiver;
    }

    public ChatList() {
    }
}
