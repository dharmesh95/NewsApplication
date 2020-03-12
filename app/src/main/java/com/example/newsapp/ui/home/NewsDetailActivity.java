package com.example.newsapp.ui.home;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.example.newsapp.R;
import com.example.newsapp.constants.Keys;
import com.example.newsapp.helper.Time;
import com.example.newsapp.models.Article;
import com.google.gson.Gson;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

public class NewsDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_news_detail);

        Gson gson = new Gson();
        final Article article = gson.fromJson(getIntent().getStringExtra(Keys.ARTICLE), Article.class);

        ((TextView) findViewById(R.id.detail_title)).setText(article.getTitle());
        Glide.with(this).load(article.getUrlToImage()).into((ImageView) findViewById(R.id.detail_image));

        Button source = (Button) findViewById(R.id.source);
        source.setText(article.getSource().getName());

        source.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String theurl = article.getUrl();
                Uri urlstr = Uri.parse(theurl);
                Intent urlintent = new Intent();
                urlintent.setData(urlstr);
                urlintent.setAction(Intent.ACTION_VIEW);
                startActivity(urlintent);
            }
        });

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
