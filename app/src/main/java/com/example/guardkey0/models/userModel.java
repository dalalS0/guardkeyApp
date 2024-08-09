package com.example.guardkey0.models;

public class userModel {

    String userEmail;
    String username;
    String userPssword;

    public userModel() {
    }

    public userModel(String userEmail, String userPssword, String username) {
        this.userEmail = userEmail;
        this.userPssword = userPssword;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }

    public String getUserPssword() {
        return userPssword;
    }

    public void setUserPssword(String userPssword) {
        this.userPssword = userPssword;
    }
}
