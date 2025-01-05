package com.example.app_work.Data;

public class Comment {
    private String viewer;
    private String content;
    private String create_time;

    public Comment(String viewer, String content, String create_time) {
        this.create_time = create_time;
        this.viewer = viewer;
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public String getCreate_time() {
        return create_time;
    }

    public String getViewer() {
        return viewer;
    }
}
