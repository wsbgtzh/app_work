package com.example.app_work.Data;

public class Post {
    private String postId;
    private String title;
    private String imageUrl;
    private String timestamp;
    private String author;
    private String createdDate;
    private String commentCount;
    private String likeCount;

    public Post(String postId, String title, String imageUrl, String timestamp, String author, String createdDate, String commentCount, String likeCount) {
        this.postId = postId;
        this.title = title;
        this.imageUrl = imageUrl;
        this.timestamp = timestamp;
        this.author = author;
        this.createdDate = createdDate;
        this.commentCount = commentCount;
        this.likeCount = likeCount;
    }

    public String getPostId() {
        return postId;
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

    public void setCommentCount(String commentCount) {
        this.commentCount = commentCount;
    }

    public String getLikeCount() {
        return likeCount;
    }

    public void setLikeCount(String likeCount) {
        this.likeCount = likeCount;
    }
}
