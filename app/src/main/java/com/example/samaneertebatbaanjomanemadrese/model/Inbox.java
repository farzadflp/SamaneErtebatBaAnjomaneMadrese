package com.example.samaneertebatbaanjomanemadrese.model;

import android.support.v7.widget.AppCompatImageView;
import android.support.v7.widget.AppCompatTextView;

public class Inbox {
    private String username;
    private AppCompatImageView avatar;

    public Inbox(String username, AppCompatImageView avatar) {
        this.username = username;
        this.avatar = avatar;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public AppCompatImageView getAvatar() {
        return avatar;
    }

    public void setAvatar(AppCompatImageView avatar) {
        this.avatar = avatar;
    }
}
