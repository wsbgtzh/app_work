package com.example.app_work.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Adapter.Interface.OnCommentItemClickListener;
import com.example.app_work.Data.Comment;
import com.example.app_work.R;

import java.util.List;

public class CommentAdapter extends RecyclerView.Adapter<CommentAdapter.CommentViewHolder> {
    private List<Comment> commentList;
    private Context context;
    private OnCommentItemClickListener onCommentItemClickListener;

    public CommentAdapter(List<Comment> commentList, Context context) {
        this.commentList = commentList;
        this.context = context;
    }

    public void setOnCommentItemClickListener(OnCommentItemClickListener listener) {
        this.onCommentItemClickListener = listener;
    }

    @NonNull
    @Override
    public CommentViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_comment, parent, false);
        return new CommentViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull CommentViewHolder holder, int position) {
        Comment comment = commentList.get(position);
        holder.contentView.setText(comment.getContent());
        holder.viewerView.setText(comment.getViewer());
        holder.createTimeView.setText(comment.getCreate_time());

        holder.itemView.setOnClickListener(v -> {
            if (onCommentItemClickListener != null) {
                onCommentItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return commentList.size();
    }

    public static class CommentViewHolder extends RecyclerView.ViewHolder {
        TextView contentView, viewerView, createTimeView;
        public CommentViewHolder(@NonNull View itemView) {
            super(itemView);
            contentView = itemView.findViewById(R.id.comment_content);
            viewerView = itemView.findViewById(R.id.post_viewer);
            createTimeView = itemView.findViewById(R.id.comment_created_date);
        }
    }
}
