package com.example.whereto;

public class FriendListModel {

    private String eMail;
    private String fName;

    public FriendListModel() {

    }

    public FriendListModel(String eMail, String fName){
        this.eMail = eMail;
        this.fName = fName;
    }

    public String geteMail() {
        return eMail;
    }

    public String getfName() {
        return fName;
    }
}
