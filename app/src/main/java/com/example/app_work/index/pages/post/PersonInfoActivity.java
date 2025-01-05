package com.example.app_work.index.pages.post;

import android.content.Intent;
import android.os.Bundle;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_work.Client.PostRepository;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.R;
import com.example.app_work.index.pages.ChatActivity;

import java.util.HashMap;
import java.util.Map;

public class PersonInfoActivity extends AppCompatActivity {
    private String follow;
    private String fans;
    private PostRepository postRepository = new PostRepository(this);

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.person_info);

        TextView usernameView = findViewById(R.id.username);
        TextView fansView = findViewById(R.id.fans);
        TextView followView = findViewById(R.id.follow);
        ImageButton followBtn = findViewById(R.id.follow_btn);
        ImageButton chat_btn = findViewById(R.id.chat_btn);

        String username = getIntent().getStringExtra("username");
        fans = getIntent().getStringExtra("fans");
        follow = getIntent().getStringExtra("follow");
        usernameView.setText(username);
        fansView.setText(fans);

        if (follow.equals("true")) {
            followView.setText("已关注");
            followBtn.setBackgroundResource(R.drawable.follow);
        } else {
            followView.setText("未关注");
            followBtn.setBackgroundResource(R.drawable.unfollow);
        }

        followBtn.setOnClickListener(v -> {
            Map<String, String> map = new HashMap<>();
            map.put("username", GlobalData.getInstance().getUsername());
            map.put("follower", username);
            if (follow.equals("true")) {
                follow = "false";
                followView.setText("未关注");
                followBtn.setBackgroundResource(R.drawable.unfollow);
                System.out.println(map);
                postRepository.deleteFollow(map);
                postRepository.updateFans(username, "sub");
                fans = String.valueOf(Integer.parseInt(fans) - 1);
                fansView.setText(fans);

            } else {
                follow = "true";
                followView.setText("已关注");
                followBtn.setBackgroundResource(R.drawable.follow);
                postRepository.addFollow(map);
                postRepository.updateFans(username, "add");
                fans = String.valueOf(Integer.parseInt(fans) + 1);
                fansView.setText(fans);
            }
        });

        chat_btn.setOnClickListener(v -> {
            Intent intent = new Intent(this, ChatActivity.class);
            intent.putExtra("username", username);
            startActivity(intent);
        });
    }
}
