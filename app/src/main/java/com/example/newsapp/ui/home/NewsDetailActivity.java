package com.example.newsapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.constants.Keys;
import com.example.newsapp.helper.OfflineStore;
import com.example.newsapp.helper.Time;
import com.example.newsapp.models.Article;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.gson.Gson;

import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Gson gson = new Gson();
        String articleJsonString = getIntent().getStringExtra(Keys.ARTICLE);
        final Article article = gson.fromJson(articleJsonString, Article.class);

        ((TextView) findViewById(R.id.detail_title)).setText(article.getTitle());
        Glide.with(this).load(article.getUrlToImage()).into((ImageView) findViewById(R.id.detail_image));

        Button source = findViewById(R.id.source);
        source.setText(article.getSource().getName());
        source.setOnClickListener(v -> {
            String theUrl = article.getUrl();
            Uri urlStr = Uri.parse(theUrl);
            Intent urlIntent = new Intent();
            urlIntent.setData(urlStr);
            urlIntent.setAction(Intent.ACTION_VIEW);
            startActivity(urlIntent);
        });

        FloatingActionButton share = findViewById(R.id.share);
        share.setOnClickListener(v -> {
            Intent sendIntent = new Intent();
            sendIntent.setAction(Intent.ACTION_SEND);
            sendIntent.putExtra(Intent.EXTRA_TEXT, article.getUrl());
            sendIntent.setType("text/plain");
            startActivity(sendIntent);

        });

        FloatingActionButton bookmarkButton = findViewById(R.id.bookmark);
        NewsDetailActivity newsDetailActivity = this;
        bookmarkButton.setOnClickListener(v -> OfflineStore.appendOnFile((new Gson().toJson(article)) + OfflineStore.BOOKMARKS_SEPARATOR, "bookmarks.txt", newsDetailActivity));

        ((TextView) findViewById(R.id.detail_time)).setText(Time.getDifference(article.getPublishedAt()));
        ((TextView) findViewById(R.id.description)).setText(article.getDescription());
        ((TextView) findViewById(R.id.timestamp)).setText(formatDate(article.getPublishedAt()));
    }

    private String formatDate(String publishedAt) {
        Date date = Time.getDate(publishedAt);
        SimpleDateFormat sdf = new SimpleDateFormat("dd MMM, yyyy");
        return sdf.format(date);
    }
}
