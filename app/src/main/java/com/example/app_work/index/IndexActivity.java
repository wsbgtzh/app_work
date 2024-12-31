package com.example.app_work.index;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_work.Client.UserRepository;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.Model.User;
import com.example.app_work.R;
import com.example.app_work.index.pages.NavigationActivity;
import com.example.app_work.index.register.RegisterActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

public class IndexActivity extends AppCompatActivity {
    private static final String TAG = "IndexActivity";
    private UserRepository userRepository;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_index);

        Button login_btn = findViewById(R.id.login);
        login_btn.setOnClickListener(v-> {
            TextInputEditText usernameLayout = findViewById(R.id.username);
            TextInputEditText passwordLayout = findViewById(R.id.password);
//            userRepository = new UserRepository(IndexActivity.this);
//            User newUser = new User(username.getText().toString(), password.getText().toString());
//            userRepository.addUser(newUser);
            //userRepository.test();
            userRepository = new UserRepository(IndexActivity.this);
            userRepository.login(usernameLayout.getText().toString(), passwordLayout.getText().toString(), new UserRepository.OnUserAddListener() {
                @Override
                public void onSuccess() {
                    return;
                }

                @Override
                public void onSuccess(Map<String, String> msg) {
                    showSuccessDialog(msg);
                }

                @Override
                public void onFailure(String errorMessage) {
                    showFailureDialog(errorMessage);
                }
            });
        });
        Button register_btn = findViewById(R.id.register);
        register_btn.setOnClickListener(v-> {
            Intent intent = new Intent(IndexActivity.this, RegisterActivity.class);
            startActivity(intent);
        });
    }
    private void showSuccessDialog(Map<String, String> info) {
        new AlertDialog.Builder(this)
                .setTitle("登录成功")
                .setMessage("登录成功！")
                .setPositiveButton("确认", ((dialogInterface, i) -> {
                    System.out.println(info);
                    String username = info.get("username");
                    Integer fans = Integer.parseInt(info.get("fans"));
                    Integer follow = Integer.parseInt(info.get("follow"));
                    GlobalData.getInstance().setUsername(username);
                    GlobalData.getInstance().setFans(fans);
                    GlobalData.getInstance().setFollow(follow);
                    Intent intent = new Intent(IndexActivity.this, NavigationActivity.class);
                    startActivity(intent);
                    finish();
                }))
                .setCancelable(false)
                .show();
    }
    private void showFailureDialog(String msg) {
        new AlertDialog.Builder(this)
                .setTitle("登录失败")
                .setMessage(msg)
                .setPositiveButton("确认", ((dialogInterface, i) -> {

                }))
                .setCancelable(false)
                .show();
    }
}
