package com.example.firelogin;

public class User {

    String userId, userName, userMail, userContact, userPassword;

    public User(String userId, String userName, String userMail, String userContact, String userPassword) {
        this.userId = userId;
        this.userName = userName;
        this.userMail = userMail;
        this.userContact = userContact;
        this.userPassword = userPassword;
    }

    public User(){}
    //signup constructor
    public User(String userName, String userMail, String userContact, String userPassword) {
        this.userName = userName;
        this.userMail = userMail;
        this.userContact = userContact;
        this.userPassword = userPassword;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserMail() {
        return userMail;
    }

    public void setUserMail(String userMail) {
        this.userMail = userMail;
    }

    public String getUserContact() {
        return userContact;
    }

    public void setUserContact(String userContact) {
        this.userContact = userContact;
    }

    public String getUserPassword() {
        return userPassword;
    }

    public void setUserPassword(String userPassword) {
        this.userPassword = userPassword;
    }
}
