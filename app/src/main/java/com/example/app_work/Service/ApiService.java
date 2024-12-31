package com.example.app_work.Service;

import com.example.app_work.Model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;
import retrofit2.http.Query;

public interface ApiService {
    @POST("/api/users/register/")
    Call<Map<String, String>> addUser(@Body User user);

    @GET("/api/users/login/")
    Call<Map<String, String>> login(@Query("username") String username, @Query("password") String password);

    @Multipart
    @POST("/api/posts/upload/")
    Call<Map<String, String>> uploadPost(
            @Part MultipartBody.Part image,
            @Part("title") RequestBody title,
            @Part("content") RequestBody content,
            @Part("username") RequestBody username,
            @Part("create_time") RequestBody createTime,
            @Part("like_count") RequestBody likeCount,
            @Part("comment_count") RequestBody commentCount
            );

    @GET("/api/posts/all/")
    Call<List<Map<String, String>>> getAllPost();
}
