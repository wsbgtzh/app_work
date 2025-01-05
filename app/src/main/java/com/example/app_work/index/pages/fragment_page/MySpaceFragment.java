package com.example.app_work.index.pages.fragment_page;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Client.PostRepository;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.R;
import com.example.app_work.index.pages.ChatActivity;
import com.example.app_work.index.pages.FollowListActivity;

public class MySpaceFragment extends Fragment {
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_myspace, container, false);
        TextView usernameView = rootView.findViewById(R.id.username);
        usernameView.setText(GlobalData.getInstance().getUsername());
        TextView fansView = rootView.findViewById(R.id.fans);
        fansView.setText(GlobalData.getInstance().getFans().toString());

        ImageButton followListBtn = rootView.findViewById(R.id.follow_list_btn);
        followListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FollowListActivity.class);
            intent.putExtra("list", "follow");
            startActivity(intent);
        });

        ImageButton fansListBtn = rootView.findViewById(R.id.fans_list_btn);
        fansListBtn.setOnClickListener(v -> {
            Intent intent = new Intent(requireContext(), FollowListActivity.class);
            intent.putExtra("list", "fans");
            startActivity(intent);
        });

        ImageButton chatBtn = rootView.findViewById(R.id.chat_btn);
        chatBtn.setOnClickListener(v -> {

        });

        PostRepository postRepository = new PostRepository(requireContext());
        RecyclerView recyclerView = rootView.findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(requireContext()));
        postRepository.getPosts(GlobalData.getInstance().getUsername(), recyclerView);
        return rootView;
    }
}
