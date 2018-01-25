package com.flowerroad.receiptofwoorydle;

/**
 * Created by juhyun on 2018-01-25.
 */

public class User {
    private String userName;
    private String userEmail;

    public User(){
        userName=null;
        userEmail=null;
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
