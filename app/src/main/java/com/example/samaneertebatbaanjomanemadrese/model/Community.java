package com.example.samaneertebatbaanjomanemadrese.model;

import android.os.Parcel;
import android.os.Parcelable;

public class Community extends User  implements Parcelable {
    private String post , degree , course , addressWork , telWork;

    public Community(User user , String post, String degree , String course) {
        super(user.getFirstname() , user.getLastname() , user.getUsername() , user.getGender(),user.getId_user() , user.getId_school());
        super.setRole("c");
        this.post = post;
        this.degree = degree;
        this.course = course;
    }

    public Community(User user, String post) {
        super(user.getFirstname(), user.getLastname(), user.getUsername());
        super.setRole("c");
        this.post = post;
    }

    public Community(User user ,String degree, String course , String post, String telWork, String addressWork) {
        super(user.getFirstname(),user.getLastname(),user.getPhoneNo(),user.getUsername(),user.getPasssword(),user.getRole());
        super.setGender(user.getGender());
        super.setId_school(user.getId_school());
        this.degree = degree;
        this.course = course;
        this.addressWork = addressWork;
        this.telWork = telWork;
        this.post = post;
    }

    public String getPost() {
        return post;
    }

    public void setPost(String post) {
        this.post = post;
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

    public String getAddressWork() {
        return addressWork;
    }

    public void setAddressWork(String addressWork) {
        this.addressWork = addressWork;
    }

    public String getTelWork() {
        return telWork;
    }

    public void setTelWork(String telWork) {
        this.telWork = telWork;
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
        dest.writeString(post);
        dest.writeString(telWork);
        dest.writeString(addressWork);
    }
    public static final Parcelable.Creator<Community> CREATOR = new Parcelable.Creator<Community>() {
        public Community createFromParcel(Parcel in) {
            return new Community(in);
        }

        public Community[] newArray(int size) {
            return new Community[size];

        }
    };
    private Community(Parcel in) {
        super(in.readString() , in.readString() , in.readString() , in.readInt(),in.readInt() ,in.readInt());
        super.setPhoneNo(in.readString());
        super.setRole(in.readString());
        super.setSchool(in.readString());
        super.setVerified(in.readInt());
        degree = in.readString();
        course = in.readString();
        post = in.readString();
        telWork = in.readString();
        addressWork = in.readString();
    }


}
