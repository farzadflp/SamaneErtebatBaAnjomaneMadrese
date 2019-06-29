package com.example.samaneertebatbaanjomanemadrese.model;

public class Community extends User {
    private String post , degree , course , address , tel , addressWork , telWork;
    private int id_community ;

    public Community(User user , String post, int id_community) {
        super(user.getFirstname() , user.getLastname() , user.getUsername() , user.getGender(),user.getId_user() , user.getId_school());
        super.setRole("c");
        this.id_community = id_community;

    }
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getTel() {
        return tel;
    }

    public void setTel(String tel) {
        this.tel = tel;
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

    public int getId_community() {
        return id_community;
    }

    public void setId_community(int id_community) {
        this.id_community = id_community;
    }


}
