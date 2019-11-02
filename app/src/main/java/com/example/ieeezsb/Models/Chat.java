package com.example.ieeezsb.Models;

public class Chat {
    private String sender, key, message;

    public Chat(String name, String key, String message) {
        this.sender = name;
        this.key = key;
        this.message = message;
    }


    public Chat(String name, String message) {
        this.sender = name;
        this.message = message;
    }

    public Chat() {
    }

    public String getSender() {
        return sender;
    }

    public void setSender(String name) {
        this.sender = name;
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
                "name='" + sender + '\'' +
                ", key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
