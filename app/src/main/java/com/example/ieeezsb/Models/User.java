package com.example.ieeezsb.Models;

public class User {
    private String name, about, email, personalMail, phone, community, chatStatus, profileImage, messagingToken, securityLevel, id;

    public User(String name, String about, String email, String personalMail, String phone, String community, String chatStatus, String profileImage, String messagingToken, String securityLevel, String id) {
        this.name = name;
        this.about = about;
        this.email = email;
        this.personalMail = personalMail;
        this.phone = phone;
        this.community = community;
        this.chatStatus = chatStatus;
        this.profileImage = profileImage;
        this.messagingToken = messagingToken;
        this.securityLevel = securityLevel;
        this.id = id;
    }

    public User() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAbout() {
        return about;
    }

    public void setAbout(String about) {
        this.about = about;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPersonalMail() {
        return personalMail;
    }

    public void setPersonalMail(String personalMail) {
        this.personalMail = personalMail;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCommunity() {
        return community;
    }

    public void setCommunity(String community) {
        this.community = community;
    }

    public String getChatStatus() {
        return chatStatus;
    }

    public void setChatStatus(String chatStatus) {
        this.chatStatus = chatStatus;
    }

    public String getProfileImage() {
        return profileImage;
    }

    public void setProfileImage(String profileImage) {
        this.profileImage = profileImage;
    }

    public String getMessagingToken() {
        return messagingToken;
    }

    public void setMessagingToken(String messagingToken) {
        this.messagingToken = messagingToken;
    }

    public String getSecurityLevel() {
        return securityLevel;
    }

    public void setSecurityLevel(String securityLevel) {
        this.securityLevel = securityLevel;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "User{" +
                "name='" + name + '\'' +
                ", about='" + about + '\'' +
                ", email='" + email + '\'' +
                ", personalMail='" + personalMail + '\'' +
                ", phone='" + phone + '\'' +
                ", community='" + community + '\'' +
                ", chatStatus='" + chatStatus + '\'' +
                ", profileImage='" + profileImage + '\'' +
                ", messagingToken='" + messagingToken + '\'' +
                ", securityLevel='" + securityLevel + '\'' +
                ", id='" + id + '\'' +
                '}';
    }
}
