package com.example.app_work.Data;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class GlobalData {
    private static GlobalData instance;
    private String username;
    private Integer fans;
    private String follow;
    private List<Map<String, String>> posts = new ArrayList<>();

    private GlobalData() {
        this.username = null;
    }

    public static GlobalData getInstance() {
        if (instance == null) {
            instance = new GlobalData();
        }
        return instance;
    }

    public List<Map<String, String>> getPosts() {
        return posts;
    }

    public void setPosts(List<Map<String, String>> posts) {
        this.posts = posts;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getUsername() {
        return username;
    }

    public void setFans(Integer fans) {
        this.fans = fans;
    }

    public Integer getFans() {
        return fans;
    }

    public void setFollow(String follow) {
        this.follow = follow;
    }

    public String getFollow() {
        return follow;
    }
}
