package com.example.testblog;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import static com.example.testblog.NetworkService.posts;

public class MessageActivity extends AppCompatActivity {

    private EditText editText;
    public static final int POST_REQUEST_CODE = 12;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_message);

        editText = findViewById(R.id.et_message);

    }

    public void onPostThis (View view) {

        if (!String.valueOf(editText.getText()).equals("")) {
            //NetworkService.posts.add("post ID: 101" +  "\n" +  "\n" +"user ID: 1" +  "\n" +  "\n" + "Title: foo" + "\n" +  "\n" + String.valueOf(editText.getText()));
            Post post = new Post();
            post.setBody(String.valueOf(editText.getText()));
            post.setId(NetworkService.posts.size() + 1);
            post.setUserId(1);
            post.setTitle("Title");
            postOurMessage(post);
            editText.getText().clear();


            CharSequence txt = "Posted!";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, txt, duration);
            toast.show();
        } else {
            CharSequence txt = "Enter your text please";
            int duration = Toast.LENGTH_SHORT;
            Toast toast = Toast.makeText(this, txt, duration);
            toast.show();
        }
    }

    public void postOurMessage(Post post) {
        NetworkService.getInstance()
                .getApi()
                .postData(post)
                .enqueue(new Callback<Post>() {
                    @Override
                    public void onResponse(@NonNull Call<Post> call, @NonNull Response<Post> response) {
                        if (response.isSuccessful()) {
                            Post post = response.body();
                            posts.add("post ID: " + post.getId() +  "\n" +  "\n" +"user ID: " + post.getUserId() +  "\n" +  "\n" + "Title: " + post.getTitle() + "\n" +  "\n" + post.getBody());
                            setResult(Activity.RESULT_OK);
                        } else {
                            posts.add("Error");
                        }
                    }
                    @Override
                    public void onFailure(@NonNull Call<Post> call, @NonNull Throwable t) {

                        posts.add("Error occurred while getting request!");
                        t.printStackTrace();
                    }
                });
    }
}