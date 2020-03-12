package com.example.newsapp.helper;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.Date;

public class Time {

    public static String getDifference(String stringDate) {
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
            if (days == 1)
                return days + " day ago";
            return days + " days ago";
        }

        long hrs = duration.toHours();
        if (hrs != 0) {
            if (hrs == 1)
                return hrs + " hour ago";
            return hrs + " hours ago";
        }

        long minutes = duration.toMinutes();
        if (minutes != 0) {
            if (minutes == 1)
                return minutes + " minute ago";
            return minutes + " minutes ago";
        }

        return "Few days ago";
    }

    public static Date getDate(String strDate) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ssZ");
        Date date = new Date();
        try {
            date = sdf.parse(strDate.replaceAll("Z$", "+0000"));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }
}
