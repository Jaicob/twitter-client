package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jaicob on 8/4/16.
 */
public class Tweet {
    String id;
    String text;
    String createdAt;

    User user;

    public Tweet(String id, String text, User user, String createdAt){
        this.id = id;
        this.text = text;
        this.user = user;
        this.createdAt = createdAt;
    }


    public Tweet(JSONObject jsonObject){
        try {
            this.id = jsonObject.getString("id_str");
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

    public String getId() {
        return id;
    }

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }

    public String getCreatedAt() {
        return createdAt;
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


}
