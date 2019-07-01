package com.example.samaneertebatbaanjomanemadrese.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Manager extends User implements Parcelable {
    private String degree , course;

    public Manager(User user ,String degree, String course) {
        super(user.getFirstname() , user.getLastname() , user.getUsername() , user.getGender(),user.getId_user() , user.getId_school());
        super.setUsername(user.getUsername());
        super.setPhoneNo(user.getPhoneNo());
        super.setRole("p");
        super.setSchool(user.getSchool());
        super.setVerified(user.getVerified());
        super.setId_school(user.getId_school());
        this.degree = degree;
        this.course = course;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getCourse() {
        return course;
    }

    public void setCourse(String course) {
        this.course = course;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(super.getFirstname());
        dest.writeString(super.getLastname());
        dest.writeString(super.getUsername());
        dest.writeInt(super.getGender());
        dest.writeInt(super.getId_user());
        dest.writeInt(super.getId_school());
        dest.writeString(super.getPhoneNo());
        dest.writeString(super.getRole());
        dest.writeString(super.getSchool());
        dest.writeInt(super.getVerified());
        dest.writeString(degree);
        dest.writeString(course);
    }
    public static final Parcelable.Creator<Manager> CREATOR = new Parcelable.Creator<Manager>() {
        public Manager createFromParcel(Parcel in) {
            return new Manager(in);
        }

        public Manager[] newArray(int size) {
            return new Manager[size];

        }
    };
    private Manager(Parcel in) {
        super(in.readString() , in.readString() , in.readString() , in.readInt(),in.readInt() ,in.readInt());
        super.setPhoneNo(in.readString());
        super.setRole(in.readString());
        super.setSchool(in.readString());
        super.setVerified(in.readInt());
        degree = in.readString();
        course = in.readString();
    }
}
