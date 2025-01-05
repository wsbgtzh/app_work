package com.example.app_work.Service;

import com.example.app_work.Data.Post;
import com.example.app_work.Model.User;

import java.util.List;
import java.util.Map;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.PUT;
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

    @PUT("/api/posts/{post_id}")
    Call<Map<String, String>> updateLikeCount(@Path("post_id") String id, @Query("like_count") String likeCount);

    @GET("/api/posts/get/{post_id}")
    Call<Map<String, String>> getPost(@Path("post_id") String postId);

    @POST("/api/posts/comment/")
    Call<Map<String, String>> commentPost(@Body Map<String, String> comment_post);

    @GET("/api/posts/get_comment/{post_id}")
    Call<List<Map<String, String>>> getComment(@Path("post_id") String post_id);

    @GET("/api/users/get_user/{username}")
    Call<Map<String, String>> getUser(@Path("username") String username);

    @POST("/api/posts/is_follow/")
    Call<Map<String, String>> isFollow(@Body Map<String, String> followList);

    @POST("/api/posts/add_follow")
    Call<Map<String, String>> addFollow(@Body Map<String, String> followList);

    @POST("/api/posts/delete_follow")
    Call<Map<String, String>> deleteFollow(@Body Map<String, String> followList);

    @PUT("/api/users/update_fans")
    Call<Map<String, String>> updateFans(@Query("username") String username, @Query("option") String option);

    @GET("/api/users/get_follow_list/{username}")
    Call<List<Map<String, String>>> getFollowList(@Path("username") String username, @Query("list_type") String listType);

    @GET("/api/users/get_posts/{username}")
    Call<List<Map<String, String>>> getPosts(@Path("username") String username);

    @POST("/api/users/add_chat/")
    Call<Map<String, String>> Chat(@Body Map<String, String> charInfo);

    @GET("/api/users/get_chat/")
    Call<List<Map<String, String>>> getChat(@Query("username") String username, @Query("chat_user") String chat_user);
}
