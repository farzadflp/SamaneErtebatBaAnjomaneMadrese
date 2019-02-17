package com.example.samaneertebatbaanjomanemadrese.model;

import android.graphics.Bitmap;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.hosseini.persian.dt.Example.generate.CustomDate.format;

public class Message {
    private String msg ,time , date , username , avatarPath;
    private Bitmap bitmap;

    public Message(String msg, String username) {
        this(msg , "R.drawable.user", username );
    }

    public Message(String msg, String avatarPath , String username) {
        setDateAndTime();
        this.msg = msg;
        this.username = username;
        this.avatarPath = avatarPath;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getAvatarPath() {
        return avatarPath;
    }

    public void setAvatarPath(String avatarPath) {
        this.avatarPath = avatarPath;
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

    public Bitmap getBitmap() {
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap) {
        this.bitmap = bitmap;
    }
    private void setDateAndTime(){
        Locale  locale = new Locale("fa" , "IRAN");
        TimeZone timeZone = TimeZone.getTimeZone("GMT+3:30");
        GregorianCalendar calendar = (GregorianCalendar) Calendar.getInstance(timeZone, locale);
        DateFormat dateFormat = SimpleDateFormat.getTimeInstance();
        String format = format(Calendar.DATE, 0);
        Generate generate = PersianDT
                .Instance()
                .generate(format, "{DATE}").Separator("/");
        this.time = dateFormat.format(calendar.getTime());
        this.date = generate.getWithFullDateInDigits();
    }
}
