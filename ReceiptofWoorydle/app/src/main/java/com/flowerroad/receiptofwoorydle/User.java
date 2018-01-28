package com.flowerroad.receiptofwoorydle;

/**
 * Created by juhyun on 2018-01-25.
 */

public class User {
    private String userName;
    private String userEmail;
    private int userID;

    public User(){
        userName=null;
        userEmail=null;
    }

    public int getUserID() {
        return userID;
    }

    public void setUserID(int userID) {
        this.userID = userID;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getUserEmail() {
        return userEmail;
    }

    public void setUserEmail(String userEmail) {
        this.userEmail = userEmail;
    }
}
