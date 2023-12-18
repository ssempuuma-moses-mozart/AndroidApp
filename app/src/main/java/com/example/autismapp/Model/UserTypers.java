package com.example.autismapp.Model;

public class UserTypers {
    private  String Uid;
    private String UserName;
    private  String Email;
    private  String Password;
    private  int Usertype;


    public UserTypers(){

    }

    public UserTypers(String uid, String username, String email, String password, int usertype) {
        Uid = uid;
        UserName = username;
        Email = email;
        Password = password;
        Usertype = usertype;

    }

    public String getUid() {
        return Uid;
    }

    public void setUid(String uid) {
        Uid = uid;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
        UserName = userName;
    }

    public int getUsertype() {
        return Usertype;
    }

    public void setUsertype(int usertype) {
        Usertype = usertype;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPassword() {
        return Password;
    }

    public void setPassword(String password) {
        Password = password;
    }


}
