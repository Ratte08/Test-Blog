package com.example.testblog;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.testblog.NetworkService.posts;

public class MainActivity extends AppCompatActivity {

    private RecViewAdapter adapter = new RecViewAdapter(posts);

    private OkHttpClient client = new OkHttpClient.Builder()
            .connectTimeout(60, TimeUnit.SECONDS)
            .readTimeout(30,TimeUnit.SECONDS)
            .build();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        setupRv();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.notifyDataSetChanged();
    }

    public void onNewPost(View view) {
        Intent intent = new Intent(this, MessageActivity.class);
        startActivity(intent);
    }

    public void setupRv () {
        loadPostsOfUser(1);
        RecyclerView postRecycler = findViewById(R.id.rv_posts);
        postRecycler.setAdapter(adapter);
    }

    public void loadPostsOfUser(int id) {
        NetworkService.getInstance()
                .getApi()
                .getPostOfUser(id)
                .enqueue(new Callback<List<Post>>() {
                    @Override
                    public void onResponse(@NonNull Call<List<Post>> call, @NonNull Response<List<Post>> response) {
                        if (response.isSuccessful()) {
                            List<Post> list = response.body();

                            for (Post post : list) {
                                posts.add("post ID: " + post.getId() +  "\n" +  "\n" +"user ID: " + post.getUserId() +  "\n" +  "\n" + "Title: " + post.getTitle() + "\n" +  "\n" + post.getBody());
                            }

                        } else {
                            posts.add("Error");
                        }
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void onFailure(@NonNull Call<List<Post>> call, @NonNull Throwable t) {

                        posts.add("Error occurred while getting request!");
                        t.printStackTrace();
                        adapter.notifyDataSetChanged();
                    }
                });
    }
}