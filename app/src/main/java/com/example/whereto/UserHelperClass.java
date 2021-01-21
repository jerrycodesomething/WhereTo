package com.example.whereto;

public class UserHelperClass {
    //add variables that want to be added into the firebase
    String fullname, username, email, password, phoneNo;

//    public UserHelperClass() { }

    public UserHelperClass(String fullname, String username, String email, String password, String phoneNo) {
        this.fullname = fullname;
        this.username = username;
        this.email = email;
        this.password = password;
        this.phoneNo = phoneNo;

    }

    public String getFullname() {
        return fullname;
    }

    public void setFullname(String fullname) {
        this.fullname = fullname;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNo() {
        return phoneNo;
    }

    public void setPhoneNo(String phoneNo) {
        this.phoneNo = phoneNo;
    }

}
