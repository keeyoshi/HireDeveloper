package com.theakatsuki.hiredevelopers.Model;

public class Events {
    String content;
    String eventImage;
    String userid;

    public Events() {
    }

    public Events( String content, String eventImage, String userid) {
        this.content = content;
        this.eventImage = eventImage;
        this.userid = userid;
    }

    public String getUserid() {
        return userid;
    }

    public void setUserid(String userid) {
        this.userid = userid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getEventImage() {
        return eventImage;
    }

    public void setEventImage(String eventImage) {
        this.eventImage = eventImage;
    }
}
