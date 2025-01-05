package com.example.app_work.index.pages;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Client.PostRepository;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.R;
import com.example.app_work.index.pages.post.PersonInfoActivity;

public class FollowListActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        String list = getIntent().getStringExtra("list");
        setContentView(R.layout.follow_list_recycler_view);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        PostRepository postRepository = new PostRepository(this);
        Intent intent = new Intent(this, PersonInfoActivity.class);
        if (list.equals("follow"))
            postRepository.getFollowList(GlobalData.getInstance().getUsername(), "follow", recyclerView, intent);
        else
            postRepository.getFollowList(GlobalData.getInstance().getUsername(), "fans", recyclerView, intent);
    }
}
