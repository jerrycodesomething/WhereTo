package com.example.whereto;

public class FriendListModel {


    private String eMail;
    private String userName;

    private FriendListModel(){

    }

    public FriendListModel(String eMail, String userName) {
        this.eMail = eMail;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }
}
