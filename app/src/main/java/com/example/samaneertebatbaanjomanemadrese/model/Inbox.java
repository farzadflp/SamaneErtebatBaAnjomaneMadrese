package com.example.samaneertebatbaanjomanemadrese.model;

import android.graphics.Bitmap;

import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

public class Inbox {
    private String username_two, topic, date_time_conv, category;
    private int id_two, id_conversation;


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
    public void convertToSolarCalendar(){
        try {
            DateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
            String dateTimeFormat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(dateFormat.parse(date_time_conv));
            String timeFormat = new SimpleDateFormat("HH:mm:ss").format(dateFormat.parse(date_time_conv));
            Generate generate = PersianDT
                    .Instance()
                    .generate(dateTimeFormat, "{DATE}").Separator("/");
            date_time_conv = generate.getWithFullDateInDigits()+"  "+ timeFormat;
        } catch (ParseException e) {
            e.printStackTrace();
        }

    }
}
