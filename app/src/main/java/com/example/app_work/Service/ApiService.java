package com.example.app_work.Service;

import com.example.app_work.Model.User;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;
import retrofit2.http.Path;

public interface ApiService {
    @POST("/api/users/add")
    Call<String> addUser(@Body User user);

    @GET("/api/users/{id}")
    Call<User> getUser(@Path("id") Long id);
}
