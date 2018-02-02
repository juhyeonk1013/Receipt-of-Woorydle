package com.flowerroad.receiptofwoorydle;

/**
 * Created by juhyun on 2018-01-25.
 */

public class User {
    private String userimage;
    private String userName;
    private String userEmail;
    private int userID;
    private String phone;

    public User(){
        userName=null;
        userEmail=null;
        phone="010-0000-0000";
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

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getUserimage() {
        return userimage;
    }

    public void setUserimage(String userimage) {
        this.userimage = userimage;
    }
}
