package com.example.samaneertebatbaanjomanemadrese.model;

public class User {
    private String firstname, lastname , phoneNo, username , passsword  , role , school  ;
    private int gender = 0 , id_user = -1 , id_school = -1 , verified = 0;


    public User(String firstname, String lastname, String username, int gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
        this.gender = gender;
    }
    public User(String firstname, String lastname, String username, int gender , int id_user , int id_school) {
        this(firstname , lastname , username , gender);
        this.id_school = id_school;
        this.id_user =  id_user;
    }

    public User(String firstname, String lastname, int gender) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.gender = gender;
    }

    public User(int id_user , String firstname, String lastname , String phoneNo , int gender) {
        this.id_user = id_user;
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNo = phoneNo;
        this.gender = gender;
    }

    public User(String firstname, String lastname, String username) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.username = username;
    }

    public User(String firstname, String lastname, String phoneNo, String username, String passsword, String role) {
        this.firstname = firstname;
        this.lastname = lastname;
        this.phoneNo = phoneNo;
        this.username = username;
        this.passsword = passsword;
        this.role = role;
    }

    public String getFirstname() {
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

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPasssword() {
        return passsword;
    }

    public void setPasssword(String passsword) {
        this.passsword = passsword;
    }

    public int getGender() {
        return gender;
    }

    public void setGender(int gender) {
        this.gender = gender;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public int getId_user() {
        return id_user;
    }

    public void setId_user(int id_user) {
        this.id_user = id_user;
    }

    public String getSchool() {
        return school;
    }

    public void setSchool(String school) {
        this.school = school;
    }

    public int getId_school() {
        return id_school;
    }

    public void setId_school(int id_school) {
        this.id_school = id_school;
    }


    public int getVerified() {
        return verified;
    }

    public void setVerified(int verified) {
        this.verified = verified;
    }
}
