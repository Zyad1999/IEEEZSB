package com.example.ieeezsb.userdatabase;

public class User {

    private String name, phone, email, ieeeAccount, photoUrl;


    public User(String name, String phone, String email, String ieeeAccount, String photoUrl){
        this.name = name;
        this.phone = phone;
        this.email = email;
        this.ieeeAccount = ieeeAccount;
        this.photoUrl = photoUrl;
    }


    /**
     *
     * @return User's Name.
     */
    public String getname() {
        return name;
    }


    /**
     *
     * @return User's Phone Number.
     */
    public String getphone() {
        return phone;
    }



    /**
     *
     * @return User's personal Email.
     */
    public String getemail() {
        return email;
    }



    /**
     *
     * @return User's IEEE Account.
     */
    public String getieeeAccount() {
        return ieeeAccount;
    }


    /**
     *
     * @return User's Profile Picture URL.
     */
    public String getphotoUrl() {
        return photoUrl;
    }
}
