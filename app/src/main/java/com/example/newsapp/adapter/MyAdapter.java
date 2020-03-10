package com.example.newsapp.adapter;

import android.content.Context;
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
import com.example.newsapp.models.Article;
import com.example.newsapp.models.NewsModel;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.Period;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

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
                .into(myViewHolder.userImageView);
        myViewHolder.usernameTextView.setText(article.getTitle());
        myViewHolder.button.setText(article.getSource().getName());
        myViewHolder.timeTextView.setText(String.valueOf(getDifference(article.getPublishedAt())));
    }

    @Override
    public int getItemCount() {
        return newsModel.getArticles().size();
    }

    public class MyViewHolder extends RecyclerView.ViewHolder {
        ImageView userImageView;
        TextView  usernameTextView;
        Button    button;
        TextView  timeTextView;

        public MyViewHolder(@NonNull View itemView) {
            super(itemView);
            usernameTextView = itemView.findViewById(R.id.usernameTextView);
            userImageView = itemView.findViewById(R.id.userImageView);
            button = itemView.findViewById(R.id.button);
            timeTextView = itemView.findViewById(R.id.time);
        }
    }

    public String getDifference(String stringDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date now = new Date();
        Date date = new Date();
        try {
            date = sdf.parse(stringDate.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }

        LocalDateTime localDateTime = date.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime nowDateTime = now.toInstant().atZone(ZoneId.systemDefault()).toLocalDateTime();

        Duration duration = Duration.between(localDateTime, nowDateTime);

        long days = duration.toDays();
        if (days != 0) {
            return days + " days ago";
        }

        long hrs = duration.toHours();
        if (hrs != 0) {
            return hrs + " hours ago";
        }

        long minutes = duration.toMinutes();
        if (minutes != 0) {
            return minutes + " minutes ago";
        }

        return "Few days ago";
    }
}