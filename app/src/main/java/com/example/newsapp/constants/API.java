package com.example.newsapp.constants;

public class API {
    public static final String API_KEY      = "ee25723e73dd4c20a0fc4112fffc6776";
    public static final String ALL_NEWS_URL = "https://newsapi.org/v2/top-headlines?country=us&apiKey=" + API_KEY;
    public static final String SPORT_NEWS_URL = "https://newsapi.org/v2/top-headlines?category=sports&apiKey=" + API_KEY;
    public static final String POLITICS_NEWS_URL = "https://newsapi.org/v2/top-headlines?category=politics&country=us&apiKey=" + API_KEY;


    public static boolean isWifiConn;
    public static boolean isMobileConn;
}
