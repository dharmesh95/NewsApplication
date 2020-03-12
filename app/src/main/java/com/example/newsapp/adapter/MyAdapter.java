package com.example.newsapp.adapter;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.helper.Time;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.NewsModel;
import com.example.newsapp.ui.home.NewsDetailActivity;
import com.google.gson.Gson;

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
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.list_item, viewGroup, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewHolder myViewHolder, int i) {

        Article article = newsModel.getArticles().get(i);
        Glide
                .with(context)
                .load(article.getUrlToImage())
                .into(myViewHolder.image);
        myViewHolder.title.setText(article.getTitle());
        myViewHolder.button.setText(article.getSource().getName());
        myViewHolder.time.setText(Time.getDifference(article.getPublishedAt()));
    }

    @Override
    public int getItemCount() {
        return newsModel.getArticles().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView image;
        TextView  title;
        Button    button;
        TextView  time;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            title = itemView.findViewById(R.id.title);
            image = itemView.findViewById(R.id.userImageView);
            button = itemView.findViewById(R.id.button);
            time = itemView.findViewById(R.id.time);

            itemView.setOnClickListener(this); //title?
        }

        @Override
        public void onClick(View v) {
            Context context = v.getContext();
            Intent intent = new Intent(context, NewsDetailActivity.class);

            System.out.println("Article clicked : " + newsModel.getArticles().get(getAdapterPosition()).toString());

            intent.putExtra("ARTICLE", new Gson().toJson(newsModel.getArticles().get(getAdapterPosition())));

            context.startActivity(intent);
        }
    }


}
