package com.codepath.apps.restclienttemplate.models;

import com.orm.SugarRecord;

import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcel;

/**
 * Created by Jaicob on 8/4/16.
 */
@Parcel
public class User extends SugarRecord   {
    private Long id;
    private String location;
    private Long tweet_count;
    private String screenName;
    private String description;
    private Long friends_count;
    private Long followers_count;
    private String profileImageUrl;

    public User(){}

    public User(String screenName, long uid, String profileImageUrl, String location, String description, Long friends_count, Long followers_count, Long tweet_count){
        this.id = uid;
        this.location = location;
        this.tweet_count = tweet_count;
        this.screenName = screenName;
        this.description = description;
        this.friends_count = friends_count;
        this.followers_count = followers_count;
        this.profileImageUrl = profileImageUrl;
    }

    public User(JSONObject jsonObject) {
        try {
            this.id = jsonObject.getLong("id");
            this.location = jsonObject.getString("location");
            this.tweet_count = jsonObject.getLong("statuses_count");
            this.screenName = jsonObject.getString("screen_name");
            this.description = jsonObject.getString("description");
            this.friends_count = jsonObject.getLong("friends_count");
            this.followers_count = jsonObject.getLong("followers_count");
            this.profileImageUrl = jsonObject.getString("profile_image_url_https");
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }

    public String getProfileImageUrl() {
        return profileImageUrl;
    }

    public String getLocation() {
        return location;
    }

    public Long getTweet_count() {
        return tweet_count;
    }

    public String getScreenName() {
        return screenName;
    }

    public String getDescription() {
        return description;
    }

    public Long getFriends_count() {
        return friends_count;
    }

    public Long getFollowers_count() {
        return followers_count;
    }

    public Long getId() {
        return id;
    }



}
