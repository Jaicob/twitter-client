package com.codepath.apps.restclienttemplate.models;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;

/**
 * Created by Jaicob on 8/4/16.
 */
public class Tweet {
    String text;

    public Tweet(String text){
        this.text = text;
    }

    public Tweet(JSONObject jsonObject) throws JSONException{
        try {
            this.text = jsonObject.getString("text");
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

    public String getText() {
        return text;
    }

    public void setText(String text) {
        this.text = text;
    }


}
