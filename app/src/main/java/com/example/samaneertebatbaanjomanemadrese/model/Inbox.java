package com.example.samaneertebatbaanjomanemadrese.model;

import android.graphics.Bitmap;

public class Inbox {
    private String username_two, topic, date_time_conv, category;
    private int id_two, id_conversation;
    //private Bitmap bitmap;



    public Inbox(int id_conversation, int id_two) {
        this.id_conversation = id_conversation;
        this.id_two = id_two;
    }

    public String getUsername_two() {
        return username_two;
    }

    public void setUsername_two(String username_two) {
        this.username_two = username_two;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getDate_time_conv() {
        return date_time_conv;
    }

    public void setDate_time_conv(String date_time_conv) {
        this.date_time_conv = date_time_conv;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public int getId_two() {
        return id_two;
    }

    public void setId_two(int id_two) {
        this.id_two = id_two;
    }

    public int getId_conversation() {
        return id_conversation;
    }

    public void setId_conversation(int id_conversation) {
        this.id_conversation = id_conversation;
    }
    /*
    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
 */
}
