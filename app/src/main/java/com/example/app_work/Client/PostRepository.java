package com.example.app_work.Client;

import android.content.Context;
import android.content.Intent;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.app_work.Adapter.CommentAdapter;
import com.example.app_work.Adapter.FollowListAdapter;
import com.example.app_work.Adapter.Interface.OnPostItemClickListener;
import com.example.app_work.Adapter.PostAdapter;
import com.example.app_work.Data.Comment;
import com.example.app_work.Data.FollowList;
import com.example.app_work.Data.GlobalData;
import com.example.app_work.Data.Post;
import com.example.app_work.R;
import com.example.app_work.Service.ApiService;
import com.example.app_work.index.pages.post.PersonInfoActivity;
import com.example.app_work.index.pages.post.PostActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.concurrent.TimeUnit;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class PostRepository {
    private ApiService apiService;
    private Context context;
    private List<Comment> commentList = new ArrayList<>();
    private CommentAdapter commentAdapter;
    private List<FollowList> followLists = new ArrayList<>();
    private FollowListAdapter followListAdapter;
    private List<Post> postList = new ArrayList<>();
    private PostAdapter postAdapter;

    public PostRepository(Context context) {
        this.context = context;
        apiService = ApiClient.getClient().create(ApiService.class);
    }

    public void getPost(String postId, View postView) {
        apiService.getPost(postId)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()) {
                            TextView titleView = postView.findViewById(R.id.post_title);
                            ImageView imageView = postView.findViewById(R.id.post_image);
                            TextView usernameView = postView.findViewById(R.id.post_author);
                            TextView createtimeView = postView.findViewById(R.id.post_created_date);
                            TextView contentView = postView.findViewById(R.id.post_content);
                            titleView.setText(response.body().get("title"));
                            Glide.with(context)
                                    .load("http://192.168.5.175:8080/api/posts/image/" + response.body().get("image"))
                                    .fitCenter()
                                    .into(imageView);
                            usernameView.setText(response.body().get("username"));
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                            long timestamp = Long.parseLong(Objects.requireNonNull(response.body().get("createTime")));
                            createtimeView.setText(sdf.format(new Date(timestamp)));
                            contentView.setText(response.body().get("content"));
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {
                        System.out.println("error!");
                    }
                });
    }

    public void commentPost(Map<String, String> comment_post, RecyclerView recyclerView, String post_id) {
        String viewer = GlobalData.getInstance().getUsername();
        String timestamp = String.valueOf(System.currentTimeMillis());
        comment_post.put("viewer", viewer);
        comment_post.put("create_time", timestamp);
        apiService.commentPost(comment_post)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()) {
                            getComment(recyclerView, post_id);
                            System.out.println(response.body());
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void getComment(RecyclerView recyclerView, String post_id) {
        apiService.getComment(post_id)
                .enqueue(new Callback<List<Map<String, String>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                        if (response.isSuccessful()) {
                            System.out.println(response.body().toString());
                            commentList.clear();
                            SimpleDateFormat sdf = new SimpleDateFormat("yyyy年MM月dd日 HH:mm");
                            List<Comment> tempList = new ArrayList<>();
                            for (Map<String, String> comment : response.body()) {
                                String viewer = comment.get("viewer");
                                String content = comment.get("content");
                                long timestamp = Long.parseLong(Objects.requireNonNull(comment.get("create_time")));
                                String createTime = sdf.format(new Date(timestamp));
                                tempList.add(0, new Comment(viewer, content, createTime));
                            }
                            commentList.addAll(tempList);
                            if (commentAdapter == null) {
                                commentAdapter = new CommentAdapter(commentList, context);
                                recyclerView.setAdapter(commentAdapter);

                                commentAdapter.setOnCommentItemClickListener(position -> {
                                    getUser(commentList.get(position).getViewer());
                                });
                            } else {
                                commentAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {

                    }
                });
    }

    public void getUser(String username) {
        apiService.getUser(username)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        if (response.isSuccessful()) {
                            String username = response.body().get("username");
                            String fans = response.body().get("fans");
                            Map<String, String> map = new HashMap<>();
                            map.put("username", GlobalData.getInstance().getUsername());
                            map.put("follower", username);
                            Intent intent = new Intent(context, PersonInfoActivity.class);
                            intent.putExtra("username", username);
                            intent.putExtra("fans", fans);
                            isFollow(map, intent);
                        }
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void isFollow(Map<String, String> followList, Intent intent) {
        apiService.isFollow(followList)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        System.out.println(response.body());
                        if (response.body().get("msg").equals("true")) {
                            intent.putExtra("follow", "true");
                        } else {
                            intent.putExtra("follow", "false");
                        }
                        context.startActivity(intent);
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void addFollow(Map<String, String> followList) {
        apiService.addFollow(followList)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void deleteFollow(Map<String, String> followList) {
        apiService.deleteFollow(followList)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void updateFans(String username, String option) {
        apiService.updateFans(username, option)
                .enqueue(new Callback<Map<String, String>>() {
                    @Override
                    public void onResponse(Call<Map<String, String>> call, Response<Map<String, String>> response) {
                        System.out.println(response.body());
                    }

                    @Override
                    public void onFailure(Call<Map<String, String>> call, Throwable t) {

                    }
                });
    }

    public void getFollowList(String username, String list_type, RecyclerView recyclerView, Intent intent) {
        apiService.getFollowList(username, list_type)
                .enqueue(new Callback<List<Map<String, String>>>() {
                    @Override
                    public void onResponse(Call<List<Map<String, String>>> call, Response<List<Map<String, String>>> response) {
                        if (response.isSuccessful()) {
                            followLists.clear();
                            List<FollowList> tempList = new ArrayList<>();
                            for (Map<String, String> followList : response.body()) {
                                String follower = followList.get("follower");
                                String fans = followList.get("fans");
                                String followCnt = followList.get("follow");
                                tempList.add(0, new FollowList(follower, fans, followCnt));
                            }
                            followLists.addAll(tempList);
                            if (followListAdapter == null) {
                                followListAdapter = new FollowListAdapter(followLists);
                                recyclerView.setAdapter(followListAdapter);

                                followListAdapter.setOnFollowListItemClickListener(position -> {
                                    String username = followLists.get(position).getUsername();
                                    String fans = followLists.get(position).getFans();
                                    intent.putExtra("username", username);
                                    intent.putExtra("fans", fans);
                                    if (list_type.equals("follow"))
                                        intent.putExtra("follow", "true");
                                    else
                                        intent.putExtra("follow", "false");
                                    context.startActivity(intent);
                                });
                            } else {
                                commentAdapter.notifyDataSetChanged();
                            }
                        }
                    }

                    @Override
                    public void onFailure(Call<List<Map<String, String>>> call, Throwable t) {

                    }
                });
    }

    public void getPosts(String username, RecyclerView recyclerView) {
        apiService.getPosts(username)
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
