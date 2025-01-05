package com.example.app_work.Data;

public class FollowList {
    private String username;
    private String fans;
    private String follow;

    public FollowList(String username, String fans, String follow) {
        this.fans = fans;
        this.follow = follow;
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public String getFans() {
        return fans;
    }

    public String getFollow() {
        return follow;
    }
}
