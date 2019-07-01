package com.example.samaneertebatbaanjomanemadrese.model;

public class Conversation {
    String category , accessibility , topic ,msg;

    public Conversation(String topic, String msg) {
        this.topic = topic;
        this.msg = msg;
    }

    public Conversation() {
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getAccessibility() {
        return accessibility;
    }

    public void setAccessibility(String accessibility) {
        this.accessibility = accessibility;
    }

    public String getTopic() {
        return topic;
    }

    public void setTopic(String topic) {
        this.topic = topic;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
