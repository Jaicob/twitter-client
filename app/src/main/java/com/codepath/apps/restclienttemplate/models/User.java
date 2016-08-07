package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jaicob on 8/4/16.
 */
public class User {
    private String name;
    private String screenName;
    private long uid;

    public User(String name, String screenName, long uid){
        this.name = name;
        this.screenName = screenName;
        this.uid = uid;
    }

    public User(JSONObject jsonObject) {
        try {
            this.name = jsonObject.getString("name");
            this.screenName = jsonObject.getString("screen_name");
            this.uid = jsonObject.getLong("id");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getName() {
        return name;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }



}
