package com.example.newsapp.ui.politics;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.example.newsapp.R;
import com.example.newsapp.adapter.MyAdapter;
import com.example.newsapp.constants.API;
import com.example.newsapp.helper.OfflineStore;
import com.example.newsapp.models.Article;
import com.example.newsapp.models.NewsModel;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.io.OutputStreamWriter;
import java.util.List;
import java.util.stream.Collectors;

import static android.content.Context.MODE_PRIVATE;

public class PoliticsFragment extends Fragment {


    RecyclerView recyclerView;
    PoliticsViewModel homeViewModel;

    static final int READ_BLOCK_SIZE = 100;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(PoliticsViewModel.class);

        View root = inflater.inflate(R.layout.fragment_home, container, false);

        recyclerView = root.findViewById(R.id.my_recycler_view);

        if (API.isWifiConn || API.isMobileConn) {


        } else {
            try {
                NewsModel newsModel = getModelFromString(OfflineStore.readFromFile("offline-politics.txt", getActivity()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new MyAdapter(getActivity(), newsModel));
                return root;
            } catch (Exception ex) {
                ex.printStackTrace();
            }
        }

        StringRequest stringRequest = new StringRequest(API.POLITICS_NEWS_URL, new Response.Listener<String>() {
            @Override
            public void onResponse(String response) {

                NewsModel newsModel = getModelFromString(response);
                List<Article> articles = newsModel.getArticles().stream().filter(article -> article.getUrlToImage() != null)
                        .collect(Collectors.<Article>toList());
                newsModel.setArticles(articles);
                //Stored value inside file
                OfflineStore.writeOnFile(response, "offline-politics.txt", getActivity());

                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new MyAdapter(getActivity(), newsModel));

            }
        }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {
                NewsModel newsModel = getModelFromString(OfflineStore.readFromFile("offline-politics.txt", getActivity()));
                recyclerView.setLayoutManager(new LinearLayoutManager(getActivity()));
                recyclerView.setAdapter(new MyAdapter(getActivity(), newsModel));
                System.out.println(error.toString());

            }
        });

        RequestQueue requestQueue = Volley.newRequestQueue(getActivity());
        requestQueue.add(stringRequest);


        return root;
    }

    public NewsModel getModelFromString(String response) {
        GsonBuilder gsonBuilder = new GsonBuilder();
        Gson gson = gsonBuilder.create();
        return gson.fromJson(response, NewsModel.class);
    }

}