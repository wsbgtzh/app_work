package com.example.app_work.Model;

public class User {
    private String username;
    private String password;
    private String confirm_password;

    public User( String username, String password, String confirm_password) {
        this.username = username;
        this.password = password;
        this.confirm_password = confirm_password;
    }

//    public Integer getId() {
//        return id;
//    }

//    public void setId(Integer id) {
//        this.id = id;
//    }


    public String getConfirm_password() {
        return confirm_password;
    }

    public void setConfirm_password(String confirm_password) {
        this.confirm_password = confirm_password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
