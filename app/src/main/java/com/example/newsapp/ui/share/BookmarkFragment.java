package com.example.newsapp.ui.share;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.newsapp.R;
import com.example.newsapp.adapter.MyAdapter;
import com.example.newsapp.helper.OfflineStore;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.NewsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.util.ArrayList;
import java.util.List;

public class BookmarkFragment extends Fragment {

    RecyclerView   recyclerView;
    ShareViewModel homeViewModel;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(ShareViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.my_recycler_view);

        try {
            NewsModel newsModel = getModelFromString(OfflineStore.readFromFile("bookmarks.txt", getActivity()));
            recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
            recyclerView.setAdapter(new MyAdapter(getActivity(), newsModel));
            return root;
        } catch (Exception ex) {
            ex.printStackTrace();
        }

        return root;
    }

    public NewsModel getModelFromString(String response) {
        String[] articlesStrings = response.split(OfflineStore.BOOKMARKS_SEPARATOR);
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        List<Article> articles = new ArrayList<>();
        for (String articleString : articlesStrings) {
            System.out.println("articleString" + articleString);
            try {
                articles.add(gson.fromJson(articleString, Article.class));
            } catch (Exception e) {
                System.err.println("Error occurred converting to Article:" + articleString);
            }
        }
        return new NewsModel(articles);
    }

}