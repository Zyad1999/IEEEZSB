package com.example.ieeezsb.Models;

public class MessageModel {

    private String senderId, key, message;

    public MessageModel(String senderId, String key, String message) {
        this.senderId = senderId;
        this.key = key;
        this.message = message;
    }

    public MessageModel(String senderId, String message) {
        this.senderId = senderId;
        this.message = message;
    }

    public MessageModel() {
    }

    public String getSenderId() {
        return senderId;
    }

    public void setSenderId(String senderId) {
        this.senderId = senderId;
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
                "senderId='" + senderId + '\'' +
                ", key='" + key + '\'' +
                ", message='" + message + '\'' +
                '}';
    }
}
