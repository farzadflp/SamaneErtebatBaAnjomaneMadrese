package com.example.samaneertebatbaanjomanemadrese.model;

public class User {
    private String firstname, lastname , phoneNo, NIDNo, childName , stNoOfChild , username , password;
    private boolean gender;

    public User(String firstname, String lastname, String phoneNo, String username, String password, boolean gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNo = phoneNo;
        this.username = username;
        this.password = password;
        this.gender = gender;
    }

    public String
    getFirstname() {
        return firstname;
    }

    public void setFirstname(String firstname) {
        this.firstname = firstname;
    }

    public String getLastname() {
        return lastname;
    }

    public void setLastname(String lastname) {
        this.lastname = lastname;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

    public String getNIDNo() {
        return NIDNo;
    }

    public void setNIDNo(String NIDNo) {
        this.NIDNo = NIDNo;
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public boolean isGender() {
        return gender;
    }

    public void setGender(boolean gender) {
        this.gender = gender;
    }
}
