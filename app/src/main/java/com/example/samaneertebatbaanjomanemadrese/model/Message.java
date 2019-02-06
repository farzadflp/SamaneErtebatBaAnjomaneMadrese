package com.example.samaneertebatbaanjomanemadrese.model;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

public class Message {
    private String msg ,time , date , username;
    private AppCompatImageView avatar;

    public Message(String msg, AppCompatImageView avatar) {
        this.msg = msg;
        this.avatar = avatar;

    }

    public Message(String msg, String time, String date, String username, AppCompatImageView avatar) {
        this.msg = msg;
        this.time = time;
        this.date = date;
        this.username = username;
        this.avatar = avatar;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public AppCompatImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(AppCompatImageView avatar) {
        this.avatar = avatar;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }
}
