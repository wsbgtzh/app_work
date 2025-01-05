package com.example.app_work.index.pages;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Client.UserRepository;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.R;
import com.google.android.material.textfield.TextInputEditText;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class ChatActivity extends AppCompatActivity {
    private RecyclerView recyclerView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        setContentView(R.layout.chat_view);
        super.onCreate(savedInstanceState);
        TextView usernameView = findViewById(R.id.username);
        String username = getIntent().getStringExtra("username");
        usernameView.setText(username);
        TextInputEditText chatText = findViewById(R.id.chat);
        Button sendBtn = findViewById(R.id.send_btn);
        UserRepository userRepository = new UserRepository(this);
        recyclerView = findViewById(R.id.recycler_view);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        userRepository.getChat(GlobalData.getInstance().getUsername(), username, recyclerView);

        sendBtn.setOnClickListener(v -> {
            String chat = chatText.getText().toString();
            Map<String, String> chatInfo = new HashMap<>();
            String user = GlobalData.getInstance().getUsername();
            chatInfo.put("chat", chat);
            chatInfo.put("username", user);
            chatInfo.put("chat_user", username);
            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
            long timestamp = System.currentTimeMillis();
            chatInfo.put("time", sdf.format(timestamp));
            userRepository.addChat(chatInfo, recyclerView);
            chatText.setText("");
        });

    }
}
