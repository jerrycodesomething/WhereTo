package com.example.whereto;

public class FriendListModel {

    private String eMail;

    private FriendListModel(){

    }

    public FriendListModel(String eMail) {
        this.eMail = eMail;
    }

    public String geteMail() {
        return eMail;
    }

    public void seteMail(String eMail) {
        this.eMail = eMail;
    }
}
