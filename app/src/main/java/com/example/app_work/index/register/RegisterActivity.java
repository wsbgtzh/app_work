package com.example.app_work.index.register;

import android.app.AlertDialog;
import android.os.Bundle;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.app_work.Client.UserRepository;
import com.example.app_work.Model.User;
import com.example.app_work.R;
import com.google.android.material.textfield.TextInputEditText;

import java.util.Map;

public class RegisterActivity extends AppCompatActivity {
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
        Button confirm_register_btn = findViewById(R.id.confirm_register);
        confirm_register_btn.setOnClickListener(v-> {
            TextInputEditText usernameLayout = findViewById(R.id.username);
            TextInputEditText passwordLayout = findViewById(R.id.password);
            TextInputEditText confirmPasswordLayout = findViewById(R.id.confirm_password);
            String username = usernameLayout.getText().toString();
            String password = passwordLayout.getText().toString();
            String confirm_password = confirmPasswordLayout.getText().toString();
            UserRepository userRepository = new UserRepository(RegisterActivity.this);
            User user = new User(username, password, confirm_password);
            userRepository.register(user, usernameLayout, new UserRepository.OnUserAddListener() {

                @Override
                public void onSuccess() {
                    showSuccessDialog();
                }

                @Override
                public void onSuccess(Map<String, String> msg) {
                    return;
                }

                @Override
                public void onFailure(String errorMessage) {
                    return;
                }
            });
        });
    }
    private void showSuccessDialog() {
        new AlertDialog.Builder(this)
                .setTitle("注册成功")
                .setMessage("您以注册成功！")
                .setPositiveButton("确认", ((dialogInterface, i) -> {
                    finish();
                }))
                .setCancelable(false)
                .show();
    }
}
