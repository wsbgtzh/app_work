package com.example.app_work.Data;

public class Post {
    private String title;
    private String imageUrl;
    private String timestamp;
    private String author;
    private String createdDate;
    private String commentCount;
    private String likeCount;

    public Post(String title, String imageUrl, String timestamp, String author, String createdDate, String commentCount, String likeCount) {
        this.title = title;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
        this.author = author;
        this.createdDate = createdDate;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }
    public String getTitle() {
        return title;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getTimestamp() {
        return timestamp;
    }

    public String getAuthor() {
        return author;
    }

    public String getCreatedDate() {
        return createdDate;
    }

    public String getCommentCount() {
        return commentCount;
    }

    public String getLikeCount() {
        return likeCount;
    }
}
