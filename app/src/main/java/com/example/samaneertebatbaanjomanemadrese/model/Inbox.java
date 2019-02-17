package com.example.samaneertebatbaanjomanemadrese.model;

import android.graphics.Bitmap;

public class Inbox {
    private String username , avatarPath;
    private Bitmap bitmap;


    public Inbox() {
        this("","");
    }

    public Inbox(String username, String avatarPath) {
        this.username = username;
        this.avatarPath = avatarPath;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
    }

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
}
