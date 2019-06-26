package com.example.samaneertebatbaanjomanemadrese.model;

public class School {
    private String state , city , grade , name;
    private int zone , id_school;

    public School(String state) {
        this.state = state;
    }

    public School(String name, int id_school) {
        this.name = name;
        this.id_school = id_school;
    }

    public School(String state, String city, String grade, String name, int zone, int id_school) {
        this.state = state;
        this.city = city;
        this.grade = grade;
        this.name = name;
        this.zone = zone;
        this.id_school = id_school;
    }

    public String getState() {
        return state;
    }

    public void setState(String state) {
        this.state = state;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getZone() {
        return zone;
    }

    public void setZone(int zone) {
        this.zone = zone;
    }

    public int getId_school() {
        return id_school;
    }

    public void setId_school(int id_school) {
        this.id_school = id_school;
    }
}
