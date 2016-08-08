package com.codepath.apps.restclienttemplate.models;

import android.icu.text.SimpleDateFormat;
import android.icu.util.TimeZone;
import android.net.ParseException;
import android.text.format.DateUtils;

import com.orm.SugarRecord;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Locale;

/**
 * Created by Jaicob on 8/4/16.
 */
public class Tweet extends SugarRecord {
    User user;
    String tweetId;
    String text;
    String createdAt;


    public Tweet(){ }

    public Tweet(String id, String text, User user, String createdAt, String location){
        this.tweetId = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }

    public Tweet(JSONObject jsonObject){
        try {
            this.tweetId = jsonObject.getString("id_str");
            this.text = jsonObject.getString("text");
            this.user = new User(jsonObject.getJSONObject("user"));
            this.createdAt = jsonObject.getString("created_at");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public static ArrayList<Tweet> jsonArrayToTweets(JSONArray array){
        ArrayList<Tweet> results = new ArrayList<>();

        for(int i = 0; i < array.length(); i++) {
            Tweet m = null;
            try {
                m = new Tweet(array.getJSONObject(i));
            } catch (JSONException e) {
                e.printStackTrace();
            }
            results.add(m);
        }
        return results;
    }

    public String getTweetId() {
        return tweetId;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return getRelativeTimeAgo(createdAt);
    }

    public void setCreatedAt(String createdAt) {
        this.createdAt = createdAt;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    private String getRelativeTimeAgo(String rawJsonDate) {
        String twitterFormat = "EEE MMM dd HH:mm:ss ZZZZZ yyyy";
        SimpleDateFormat sf = new SimpleDateFormat(twitterFormat, Locale.ENGLISH);
        sf.setLenient(true);
        sf.setTimeZone(TimeZone.getDefault());
        String relativeDate = "";

        try {
            long dateMillis = sf.parse(rawJsonDate).getTime();
            relativeDate = DateUtils.getRelativeTimeSpanString(dateMillis,
                    System.currentTimeMillis(),
                    DateUtils.SECOND_IN_MILLIS).toString();
        } catch (ParseException e) {
            e.printStackTrace();
        } catch (java.text.ParseException e) {
            e.printStackTrace();
        }

        return relativeDate;
    }

}
