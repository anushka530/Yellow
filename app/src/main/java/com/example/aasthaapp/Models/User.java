package com.example.aasthaapp.Models;

public class User {
    String profilepic, username, mail, password, userId, lastMesaage , category;
    boolean isBlocked=false;

    public User(String profilepic, String username, String mail, String password, String userId, String lastMesaage, String category, String spinner, boolean isBlocked) {
        this.profilepic = profilepic;
        this.username = username;
        this.mail = mail;
        this.password = password;
        this.userId = userId;
        this.lastMesaage = lastMesaage;
        this.category = category;
        this.isBlocked = isBlocked;
    }

    // empty constructor
    public  User(){

    }


    //SignUp Constructor
    public User( String username, String mail, String password) {
        this.username = username;
        this.mail = mail;
        this.password = password;


    }
    public User(String username, String profilepic){
        this.username=username;
        this.profilepic=profilepic;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public boolean isBlocked() {
        return isBlocked;
    }

    public void setBlocked(boolean blocked) {
        isBlocked = blocked;
    }


    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getProfilepic() {
        return profilepic;
    }
    public void setProfilepic(String profilepic) {
        this.profilepic = profilepic;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getMail() {
        return mail;
    }

    public void setMail(String mail) {
        this.mail = mail;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }


    public String getLastMesaage() {
        return lastMesaage;
    }

    public void setLastMesaage(String lastMesaage) {
        this.lastMesaage = lastMesaage;
    }
}


