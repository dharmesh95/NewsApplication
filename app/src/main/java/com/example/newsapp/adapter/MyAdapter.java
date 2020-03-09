package com.example.newsapp.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.NewsModel;

public class MyAdapter extends RecyclerView.Adapter<MyAdapter.MyViewHolder> {

    Context   context;
    NewsModel newsModel;

    public MyAdapter(Context context, NewsModel newsModel) {
        this.context = context;
        this.newsModel = newsModel;
    }

    @NonNull
    @Override
    public MyViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view =  LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Article article = newsModel.getArticles().get(i);
        Glide
                .with(context)
                .load(article.getUrlToImage())
                .into(myViewHolder.userImageView);
        myViewHolder.usernameTextView.setText(article.getTitle());

    }

    @Override
    public int getItemCount() {
        return newsModel.getArticles().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userImageView;
        TextView usernameTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            userImageView = itemView.findViewById(R.id.userImageView);

        }
    }
}