package com.example.app_work.Adapter;

import android.content.Context;
import android.content.res.Resources;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_work.Data.Post;
import com.example.app_work.R;

import java.util.List;

public class PostAdapter extends RecyclerView.Adapter<PostAdapter.PostViewHolder> {
    private List<Post> postList;
    private Context context;

    public PostAdapter(Context context, List<Post> postList) {
        this.context = context;
        this.postList = postList;
    }

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_post, parent, false);
        return new PostViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        Post post = postList.get(position);

        holder.titleTextView.setText(post.getTitle());
        holder.timestampTextView.setText(post.getTimestamp());
        holder.authorTextView.setText(post.getAuthor());
        holder.createdDateTextView.setText(post.getCreatedDate());
        holder.likeCountTextView.setText(post.getLikeCount());
        holder.commentCountTextView.setText(post.getCommentCount());
        String imageName = post.getImageUrl();
        Glide.with(context)
                .load("http://192.168.100.105:8080/api/posts/image/" + imageName)
                .fitCenter()
                .into(holder.imageView);
//        Resources resources = holder.itemView.getContext().getResources();
//        int drawableId = resources.getIdentifier(post.getImageUrl(), "drawable", holder.itemView.getContext().getPackageName());
//        holder.imageView.setImageResource(drawableId);
        holder.imageView.setScaleType(ImageView.ScaleType.FIT_CENTER);
    }

    @Override
    public int getItemCount() {
        return postList.size();
    }


    public static class PostViewHolder extends RecyclerView.ViewHolder {
        TextView titleTextView, timestampTextView, authorTextView, createdDateTextView, commentCountTextView, likeCountTextView;
        ImageView imageView;

        public PostViewHolder(View itemView) {
            super(itemView);
            titleTextView = itemView.findViewById(R.id.post_title);
            imageView = itemView.findViewById(R.id.post_image);
            timestampTextView = itemView.findViewById(R.id.post_timestamp);
            authorTextView = itemView.findViewById(R.id.post_author);
            createdDateTextView = itemView.findViewById(R.id.post_created_date);
            commentCountTextView = itemView.findViewById(R.id.post_comment_count);
            likeCountTextView = itemView.findViewById(R.id.post_like_count);
        }
    }
}
