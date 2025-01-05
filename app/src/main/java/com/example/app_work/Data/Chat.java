package com.example.app_work.Data;

public class Chat {
    private String username;
    private String chat_user;
    private String chat;
    private String time;

    public Chat(String username, String chat_user, String chat, String time) {
        this.chat = chat;
        this.chat_user = chat_user;
        this.username = username;
        this.time = time;
    }

    public String getUsername() {
        return username;
    }

    public String getChat() {
        return chat;
    }

    public String getChat_user() {
        return chat_user;
    }

    public String getTime() {
        return time;
    }
}
