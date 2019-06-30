package com.example.samaneertebatbaanjomanemadrese.model;

import android.graphics.Bitmap;
import android.os.Parcel;
import android.os.Parcelable;

import com.example.samaneertebatbaanjomanemadrese.R;
import com.hosseini.persian.dt.PersianDT;
import com.hosseini.persian.dt.PersianDate.Generate;

import java.io.Serializable;
import java.sql.Time;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.time.ZonedDateTime;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import static com.hosseini.persian.dt.Example.generate.CustomDate.format;

public class Message implements Parcelable {
    private String username , msg , dateTimeMsg;
    private int idMsg , idUser, idConversation;


    public Message(int idMsg, int idUser, int idConversation) {
        this.idMsg = idMsg;
        this.idUser = idUser;
        this.idConversation = idConversation;
    }

    public Message(String msg, int idConversation) {
        this.msg = msg;
        this.idConversation = idConversation;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getDateTimeMsg() {
        return dateTimeMsg;
    }

    public void setDateTimeMsg(String dateTimeMsg) {
        this.dateTimeMsg = dateTimeMsg;
    }

    public int getIdMsg() {
        return idMsg;
    }

    public void setIdMsg(int idMsg) {
        this.idMsg = idMsg;
    }

    public int getIdUser() {
        return idUser;
    }

    public void setIdUser(int idUser) {
        this.idUser = idUser;
    }

    public int getIdConversation() {
        return idConversation;
    }

    public void setIdConversation(int idConversation) {
        this.idConversation = idConversation;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(msg);
        dest.writeString(dateTimeMsg);
        dest.writeInt(idMsg);
        dest.writeInt(idUser);
        dest.writeInt(idConversation);
    }
    public static final Parcelable.Creator<Message> CREATOR = new Parcelable.Creator<Message>() {
        public Message createFromParcel(Parcel in) {
            return new Message(in);
        }

        public Message[] newArray(int size) {
            return new Message[size];

        }
    };
    private Message(Parcel in) {
        username = in.readString();
        msg = in.readString();
        dateTimeMsg = in.readString();
        idUser = in.readInt();
        idMsg = in.readInt();
        idConversation = in.readInt();

    }

    /**
    private String msg ,time , date , username , avatarPath;
    //private Bitmap bitmap;

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
     **/
}
