package com.example.samaneertebatbaanjomanemadrese.model;

import android.os.Parcel;
import android.os.Parcelable;

    public class Parent extends User implements Parcelable {
    private String childName , stNoOfChild ,verified_by_m = "n";

    public Parent(User user,String childName, String stNoOfChild) {
        super(user.getId_user() , user.getFirstname(), user.getLastname(), user.getPhoneNo() , user.getGender());
        super.setUsername(user.getUsername());
        super.setPasssword(user.getPasssword());
        super.setPhoneNo(user.getPhoneNo());
        super.setRole("p");
        super.setSchool(user.getSchool());
        super.setVerified(user.getVerified());
        super.setId_school(user.getId_school());
        this.childName = childName;
        this.stNoOfChild = stNoOfChild;
    }


    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public String getStNoOfChild() {
        return stNoOfChild;
    }

    public void setStNoOfChild(String stNoOfChild) {
        this.stNoOfChild = stNoOfChild;
    }

    public String getVerified_by_m() {
        return verified_by_m;
    }

    public void setVerified_by_m(String verified_by_m) {
        this.verified_by_m = verified_by_m;
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
        dest.writeString(childName);
        dest.writeString(stNoOfChild);
        dest.writeString(verified_by_m);
    }
    public static final Parcelable.Creator<Parent> CREATOR = new Parcelable.Creator<Parent>() {
        public Parent createFromParcel(Parcel in) {
            return new Parent(in);
        }

        public Parent[] newArray(int size) {
            return new Parent[size];

        }
    };
    private Parent(Parcel in) {
        super(in.readString() , in.readString() , in.readString() , in.readInt(),in.readInt() ,in.readInt());
        super.setPhoneNo(in.readString());
        super.setRole(in.readString());
        super.setSchool(in.readString());
        super.setVerified(in.readInt());
        childName = in.readString();
        stNoOfChild = in.readString();
        verified_by_m = in.readString();
    }
}
