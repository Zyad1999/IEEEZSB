package com.example.ieeezsb.Models;

public class MessageModel {

    private String name, key, message;

    public MessageModel(String name, String key, String message) {
        this.name = name;
        this.key = key;
        this.message = message;
    }



    public MessageModel(String name, String message) {
        this.name = name;
        this.message = message;
    }

    public MessageModel() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
    @Override
    public String toString() {
        return "MessageModel{" +
                "name='" + name + '\'' +
                ", key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
