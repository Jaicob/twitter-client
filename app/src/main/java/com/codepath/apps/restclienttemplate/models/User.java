package com.codepath.apps.restclienttemplate.models;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;

/**
 * Created by Jaicob on 8/4/16.
 */
public class User extends SugarRecord {
    private Long id;
    private String screenName;

    private String profileImageUrl;

    public User(){}

    public User(String screenName, long uid, String profileImageUrl){
        this.id = uid;
        this.screenName = screenName;
        this.profileImageUrl = profileImageUrl;
    }

    public User(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getLong("id");
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

    public Long getId() {
        return id;
    }



}
