package com.example.app_work.index;

import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_work.Client.UserRepository;
import com.example.app_work.Model.User;
import com.example.app_work.R;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private UserRepository userRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button login_btn = findViewById(R.id.login);
        login_btn.setOnClickListener(v-> {
            EditText username = findViewById(R.id.username);
            EditText password = findViewById(R.id.password);
            userRepository = new UserRepository();
            User newUser = new User(1L, "tzh", "123");
            userRepository.addUser(newUser);
        });
    }
}
