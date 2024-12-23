package com.example.app_work.Client;

import androidx.annotation.NonNull;

import com.example.app_work.Model.User;
import com.example.app_work.Service.ApiService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;

    public UserRepository() {
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void addUser(User user) {
        Call<String> call = apiService.addUser(user);

        call.enqueue(new Callback<String>() {
            @Override
            public void onResponse(@NonNull Call<String> call, Response<String> response) {
                if (response.isSuccessful()) {
                    System.out.println("User added successfully: " + response.body());
                } else {
                    System.out.println("Error: " + response.message());
                    System.out.println("HTTP Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<String> call, Throwable t) {
                System.out.println("Network error: " + t.getMessage());
            }
        });
    }
}
