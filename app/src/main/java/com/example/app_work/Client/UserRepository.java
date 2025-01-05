package com.example.app_work.Client;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.app_work.Adapter.ChatAdapter;
import com.example.app_work.Adapter.FollowListAdapter;
import com.example.app_work.Adapter.Interface.OnPostItemClickListener;
import com.example.app_work.Adapter.PostAdapter;
import com.example.app_work.Data.Chat;
import com.example.app_work.Data.FollowList;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.Data.Post;
import com.example.app_work.Model.User;
import com.example.app_work.Service.ApiService;
import com.example.app_work.index.pages.post.PostActivity;
import com.google.android.material.textfield.TextInputEditText;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class UserRepository {
    private ApiService apiService;
    private Context context;
    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;
    private List<Chat> chatList = new ArrayList<>();
    private ChatAdapter chatAdapter;
    public UserRepository(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void register(User user, TextInputEditText usernameLayout, OnUserAddListener listener) {
        Call<Map<String, String>> call = apiService.addUser(user);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(@NonNull Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    String status = response.body().get("status");
                    if ("fail".equals(status)) {
                        System.out.println(response.body().get("msg"));
                        displayErrorMessages(response.body().get("msg"), usernameLayout);
                        listener.onFailure("Error");
                    } else {
                        String username = response.body().get("username");
                        String password = response.body().get("password");
                        String confirm_password = response.body().get("confirm_password");
                        listener.onSuccess();
                    }
                } else {
                    System.out.println("Error: " + response.message());
                    System.out.println("HTTP Code: " + response.code());
                }
            }

            @Override
            public void onFailure(@NonNull Call<Map<String, String>> call, Throwable t) {
                System.out.println("Network error: " + t.getMessage());
            }
        });
    }

    public void login(String username, String password, OnUserAddListener listener) {
        Call<Map<String, String>> call = apiService.login(username, password);
        call.enqueue(new Callback<Map<String, String>>() {
            @Override
            public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                if (response.isSuccessful()) {
                    assert response.body() != null;
                    System.out.println(response.body());
                    String status = response.body().get("status");
                    if (status.equals("success")) {
                        listener.onSuccess(response.body());
                    } else {
                        listener.onFailure("账号或密码错误！");
                    }
                } else {
                    listener.onFailure("请求异常！");
                }
            }

            @Override
            public void onFailure(Call<Map<String, String>> call, Throwable t) {
                listener.onFailure("与服务器断开连接！");
            }
        });
    }

    public void uploadPost(String title, String content, Bitmap image) {
        byte[] imageBytes = getImageBytes(image);
        RequestBody imageBody = RequestBody.create(MediaType.parse("image/jpeg"), imageBytes);
        MultipartBody.Part imagePart = MultipartBody.Part.createFormData("image", "image.jpg", imageBody);
        RequestBody titleBody = RequestBody.create(MediaType.parse("text/plain"), title);
        RequestBody contentBody = RequestBody.create(MediaType.parse("text/plain"), content);
        RequestBody usernameBody = RequestBody.create(MediaType.parse("text/plain"), GlobalData.getInstance().getUsername());
        RequestBody createTimeBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(System.currentTimeMillis()));
        RequestBody likeCountBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(0));
        RequestBody commentCountBody = RequestBody.create(MediaType.parse("text/plain"), String.valueOf(0));
        UserRepository outer = this;
        apiService.uploadPost(imagePart, titleBody, contentBody, usernameBody, createTimeBody, likeCountBody, commentCountBody)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()) {
                            String msg = response.body().get("msg");
                            if (msg.equals("success")) {
                                Toast.makeText(outer.context, "发帖成功！", Toast.LENGTH_SHORT).show();
                            }
                            System.out.println(msg);
                        } else {
                            System.out.println("error!");
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        System.out.println("error!");
                    }
                });
    }

    public void getAllPost(RecyclerView recyclerView) {
        apiService.getAllPost()
                .enqueue(new Callback<List<Map<String, String>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                        if (response.isSuccessful()) {
                            GlobalData.getInstance().setPosts(response.body());
                            postList.clear();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                            List<Post> tempList = new ArrayList<>();
                            for (Map<String, String> post : GlobalData.getInstance().getPosts()) {
                                String postId = post.get("post_id");
                                String commentCount = post.get("comment_count");
                                String img = post.get("image");
                                System.out.println(img);
                                String likeCount = post.get("like_count");
                                long timestamp = Long.parseLong(Objects.requireNonNull(post.get("createTime")));
                                String createTime = sdf.format(new Date(timestamp));
                                String title = post.get("title");
                                String content = post.get("content");
                                String username = post.get("username");
                                tempList.add(0, new Post(postId, title, img, getTimeAgo(timestamp), username, createTime, commentCount, likeCount));
                            }
                            postList.addAll(tempList);
                            if (postAdapter == null) {
                                postAdapter = new PostAdapter(context, postList, new OnPostItemClickListener() {
                                    @Override
                                    public void onCommentClick(int position) {
                                        Post post = postList.get(position);
                                    }

                                    @Override
                                    public void onLikeClick(int position) {
                                        Post post = postList.get(position);
                                        int newLikeCount = Integer.parseInt(post.getLikeCount()) + 1;
                                        post.setLikeCount(String.valueOf(newLikeCount));
                                        postAdapter.notifyItemChanged(position);
                                        apiService.updateLikeCount(post.getPostId(), post.getLikeCount())
                                                .enqueue(new Callback<Map<String, String>>() {
                                                    @Override
                                                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                                                        if (response.isSuccessful()) {
                                                            System.out.println(response.body());
                                                        }
                                                    }

                                                    @Override
                                                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                                                        System.out.println("error!!");
                                                    }
                                                });
                                    }

                                    @Override
                                    public void onImageClick(int position) {
                                        Post post = postList.get(position);
                                        String post_id = post.getPostId();
                                        Intent intent = new Intent(context, PostActivity.class);
                                        intent.putExtra("post_id", post_id);
                                        intent.putExtra("username", post.getAuthor());
                                        context.startActivity(intent);
                                    }
                                });
                                recyclerView.setAdapter(postAdapter);
                            } else {
                                postAdapter.notifyDataSetChanged();
                            }
                        }
                        else
                            System.out.println("Error!!");
                    }

                    @Override
                    public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {

                    }
                });

    }

    public void addChat(Map<String, String> chatInfo, RecyclerView recyclerView) {
        apiService.Chat(chatInfo)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        System.out.println(response.body());
                        getChat(chatInfo.get("username"), chatInfo.get("chat_user"), recyclerView);
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void getChat(String username, String chat_user, RecyclerView recyclerView) {
        apiService.getChat(username, chat_user)
                .enqueue(new Callback<List<Map<String, String>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                        if (response.isSuccessful()) {
                            chatList.clear();
                            List<Chat> tempList = new ArrayList<>();
                            for (Map<String, String> chatList : response.body()) {
                                String username = chatList.get("username");
                                String chat_user = chatList.get("chat_user");
                                String time = chatList.get("time");
                                String chat = chatList.get("chat");
                                tempList.add(new Chat(username, chat_user, chat, time));
                            }
                            chatList.addAll(tempList);
                            if (chatAdapter == null) {
                                chatAdapter = new ChatAdapter(chatList);
                                recyclerView.setAdapter(chatAdapter);

//                                chatAdapter.setOnFollowListItemClickListener(position -> {
//                                    String username = followLists.get(position).getUsername();
//                                    String fans = followLists.get(position).getFans();
//                                    intent.putExtra("username", username);
//                                    intent.putExtra("fans", fans);
//                                    if (list_type.equals("follow"))
//                                        intent.putExtra("follow", "true");
//                                    else
//                                        intent.putExtra("follow", "false");
//                                    context.startActivity(intent);
//                                });
                            } else {
                                chatAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {

                    }
                });
    }

    public interface OnUserAddListener {
        void onSuccess();
        void onSuccess(Map<String, String> msg);
        void onFailure(String errorMessage);
    }
    private void displayErrorMessages(String msg, TextInputEditText usernameLayout) {
        usernameLayout.setError(null);
        usernameLayout.setError(msg);
    }

    private byte[] getImageBytes(Bitmap selectedImage) {
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        selectedImage.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
        return byteArrayOutputStream.toByteArray();
    }

    private String getTimeAgo(long timestamp) {
        long currentTime = System.currentTimeMillis(); // 获取当前时间戳
        long timeDifference = currentTime - timestamp; // 计算时间差

        // 计算不同时间单位
        long seconds = TimeUnit.MILLISECONDS.toSeconds(timeDifference);
        long minutes = TimeUnit.MILLISECONDS.toMinutes(timeDifference);
        long hours = TimeUnit.MILLISECONDS.toHours(timeDifference);
        long days = TimeUnit.MILLISECONDS.toDays(timeDifference);

        // 根据时间差选择合适的单位
        if (seconds < 60) {
            return seconds + "秒前";
        } else if (minutes < 60) {
            return minutes + "分钟前";
        } else if (hours < 24) {
            return hours + "小时前";
        } else if (days < 30) {
            return days + "天前";
        } else if (days < 365) {
            return days / 30 + "个月前";
        } else {
            return days / 365 + "年前";
        }
    }
}
