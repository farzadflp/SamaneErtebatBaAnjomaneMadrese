package com.example.samaneertebatbaanjomanemadrese.model;

import android.support.v7.widget.AppCompatImageView;

public class Message {
    private String msg ;
    private AppCompatImageView avatar;

    public Message(String msg, AppCompatImageView avatar) {
        this.msg = msg;
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
}
