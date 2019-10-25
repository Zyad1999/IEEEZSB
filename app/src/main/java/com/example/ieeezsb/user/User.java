package com.example.ieeezsb.user;

public class User {

    private static final String TAG = "User";

    private String name, community, phone, personalMail, email, photoUrl;
    private Roles roles;

    public User() {
    }

    public User(String name, String community, String phone, String personalMail, String email, Roles roles, String photoUrl) {
        this.name = name;
        this.community = community;
        this.phone = phone;
        this.personalMail = personalMail;
        this.email = email;
        this.roles = roles;
        this.photoUrl = photoUrl;
    }


    /**
     * @return User's Name.
     */
    public String getName() {
        return name;
    }


    /**
     * @return User's Community.
     */
    public String getCommunity() {
        return community;
    }


    /**
     * @return User's Phone Number.
     */
    public String getPhone() {
        return phone;
    }


    /**
     * @return User's personal Email.
     */
    public String getPersonalMail() {
        return personalMail;
    }


    /**
     * @return User's IEEE Account.
     */
    public String getEmail() {
        return email;
    }


    /**
     * @return User's Profile Picture URL.
     */
    public String getPhotoUrl() {
        return photoUrl;
    }


    public Roles getRoles() {
        return roles;
    }


    public static class Roles {

        private boolean isHead;
        private boolean isVice;

        public Roles(){

        }


        public Roles(boolean isHead, boolean isVice) {
            this.isHead = isHead;
            this.isVice = isVice;
        }


        public boolean isHead() {
            return isHead;
        }

        public boolean isVice() {
            return isVice;
        }
    }

}
