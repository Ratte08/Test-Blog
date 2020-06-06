package com.example.testblog;

import android.view.LayoutInflater;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

class RecViewAdapter extends RecyclerView.Adapter<RecViewAdapter.PostViewHolder> {

    private ArrayList<String> posts;

    @NonNull
    @Override
    public PostViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        TextView textView = (TextView) LayoutInflater.from(parent.getContext()).inflate(R.layout.rec_text_view, parent, false);
        return new PostViewHolder(textView);
    }

    @Override
    public void onBindViewHolder(@NonNull PostViewHolder holder, int position) {
        TextView textView = holder.textView;
        //TODO: ждм БД
        textView.setText(posts.get(position));
    }

    @Override
    public int getItemCount() {
        //TODO: После появления базы данных спрашивать у неё кол-во записей
        return posts.size();
    }

    public RecViewAdapter (ArrayList<String> posts) {
        this.posts = posts;
    }

    public class PostViewHolder extends RecyclerView.ViewHolder {

        private TextView textView;

        public PostViewHolder(@NonNull TextView v) {
            super(v);
            textView = v;
        }
    }
}
