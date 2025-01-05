package com.example.app_work.index.pages.post;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Client.PostRepository;
import com.example.app_work.Client.UserRepository;
import com.example.app_work.R;
import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.textfield.TextInputEditText;

import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class PostActivity extends AppCompatActivity {
    private BottomSheetDialog bottomSheetDialog;
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_post);
        String post_id = getIntent().getStringExtra("post_id");
        String username = getIntent().getStringExtra("username");
        PostRepository postRepository = new PostRepository(this);
        View postView = findViewById(R.id.post_view);
        postRepository.getPost(post_id, postView);
        recyclerView = findViewById(R.id.comment_recycler_view);
        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        postRepository.getComment(recyclerView, post_id);
        FloatingActionButton fabAddPost = findViewById(R.id.post_comment);
        fabAddPost.setOnClickListener(v -> showButtonSheetDialog(username, post_id));
    }

    private void showButtonSheetDialog(String username, String post_id) {
        bottomSheetDialog = new BottomSheetDialog(this);
        View dialogView = getLayoutInflater().inflate(R.layout.post_comment, null);
        bottomSheetDialog.setContentView(dialogView);

        TextInputEditText editText = dialogView.findViewById(R.id.editTextPost);
        Button saveButton = bottomSheetDialog.findViewById(R.id.btnSavePost);
        Map<String, String> map = new HashMap<>();
        map.put("username", username);
        map.put("post_id", post_id);
        saveButton.setOnClickListener(v -> {
            String content = editText.getText().toString();
            PostRepository postRepository = new PostRepository(this);
            if (!content.isEmpty()) {
                map.put("content", content);
                postRepository.commentPost(map, recyclerView, post_id);
            }
            bottomSheetDialog.dismiss();
        });
        bottomSheetDialog.show();
    }
}
