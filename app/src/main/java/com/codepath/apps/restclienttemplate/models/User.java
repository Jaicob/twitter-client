package com.codepath.apps.restclienttemplate.models;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jaicob on 8/4/16.
 */
public class User {
    private long uid;
    private String screenName;

    private String profileImageUrl;

    public User(String screenName, long uid, String profileImageUrl){
        this.uid = uid;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
    }

    public User(JSONObject jsonObject) {
        try {
            this.uid = jsonObject.getLong("id");
            this.screenName = jsonObject.getString("screen_name");
            this.profileImageUrl = jsonObject.getString("profile_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getScreenName() {
        return screenName;
    }

    public long getUid() {
        return uid;
    }



}
