package com.example.app_work.Adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Adapter.Interface.OnFollowListItemClickListener;
import com.example.app_work.Data.FollowList;
import com.example.app_work.R;

import java.util.List;

public class FollowListAdapter extends RecyclerView.Adapter<FollowListAdapter.FollowListViewHolder> {
    private List<FollowList> followLists;
    private OnFollowListItemClickListener onFollowListItemClickListener;

    public FollowListAdapter(List<FollowList> followLists) {
        this.followLists = followLists;
    }

    public void setOnFollowListItemClickListener(OnFollowListItemClickListener onFollowListItemClickListener) {
        this.onFollowListItemClickListener = onFollowListItemClickListener;
    }

    @NonNull
    @Override
    public FollowListAdapter.FollowListViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.follow_list, parent, false);
        return new FollowListViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull FollowListAdapter.FollowListViewHolder holder, int position) {
        FollowList followList = followLists.get(position);
        holder.usernameView.setText(followList.getUsername());
        holder.fansView.setText(followList.getFans());
        holder.followView.setText(followList.getFollow());

        holder.itemView.setOnClickListener(v -> {
            if (onFollowListItemClickListener != null) {
                onFollowListItemClickListener.onItemClick(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return followLists.size();
    }

    public static class FollowListViewHolder extends RecyclerView.ViewHolder {
        TextView usernameView, fansView, followView;
        public FollowListViewHolder(View view) {
            super(view);
            usernameView = view.findViewById(R.id.username);
            fansView = view.findViewById(R.id.fans);
            followView = view.findViewById(R.id.follow);
        }
    }
}
