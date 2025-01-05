package com.example.app_work.Adapter;

import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Data.Chat;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.R;

import java.util.List;

public class ChatAdapter extends RecyclerView.Adapter<ChatAdapter.ChatViewHolder> {
    private List<Chat> chatList;

    public ChatAdapter(List<Chat> chatList) {
        this.chatList = chatList;
    }

    @NonNull
    @Override
    public ChatViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.chat_item, parent, false);
        return new ChatViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ChatViewHolder holder, int position) {
        Chat chat = chatList.get(position);
        if (chat.getUsername().equals(GlobalData.getInstance().getUsername()))
            holder.chatView.setBackgroundColor(Color.parseColor("#7FFFAA"));
        else
            holder.chatView.setBackgroundColor(Color.parseColor("#FFC0CB"));
        holder.usernameView.setText(chat.getUsername());
        holder.chatView.setText(chat.getChat());
        holder.timeView.setText(chat.getTime());
    }

    @Override
    public int getItemCount() {
        return chatList.size();
    }


    public static class ChatViewHolder extends RecyclerView.ViewHolder {
        TextView usernameView, timeView, chatView;

        public ChatViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameView = itemView.findViewById(R.id.username);
            timeView = itemView.findViewById(R.id.time);
            chatView = itemView.findViewById(R.id.chat);
        }
    }
}
