package com.codepath.apps.restclienttemplate.fragments;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.User;
import com.squareup.picasso.Picasso;

import org.parceler.Parcels;

/**
 * Created by Jaicob on 8/14/16.
 */
public class ProfileInfoFragment extends android.support.v4.app.Fragment {
    private User user;
    private ImageView ivImage;
    private TextView tvScreenName;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private TextView tvLocation;
    private TextView tvDescription;
    private TextView tvNumTweets;

    public static ProfileInfoFragment newInstance() {
        ProfileInfoFragment fragment = new ProfileInfoFragment();
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        Log.d("DEBUG", "onCreateView: CREATING FRAGMENT");

        super.onCreate(savedInstanceState);
        user = (User) Parcels.unwrap(getActivity().getIntent().getParcelableExtra("user"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        Log.d("DEBUG", "onCreateView: CREATING FRAGMENT VIEW");
        View v = inflater.inflate(R.layout.fragment_profile_info, container, false);

        tvScreenName = (TextView) v.findViewById(R.id.tvScreenNamep);
        tvFollowers = (TextView) v.findViewById(R.id.tvFollowersp);
        tvFollowing = (TextView) v.findViewById(R.id.tvFollowingp);
        tvLocation = (TextView) v.findViewById(R.id.tvLocationp);
        tvDescription = (TextView) v.findViewById(R.id.tvDescriptionp);
        tvNumTweets = (TextView) v.findViewById(R.id.tvNumTweetsp);
        ivImage = (ImageView) v.findViewById(R.id.ivProfileImagep);

        tvScreenName.setText(user.getScreenName());
        tvFollowers.setText("Followers: " + user.getFollowers_count().toString());
        tvFollowing.setText("Following: " + user.getFriends_count().toString());
        tvLocation.setText("Location: " + user.getLocation());
        tvDescription.setText(user.getDescription());
        tvNumTweets.setText("Tweets: " + user.getTweet_count().toString());

        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivImage);
        ivImage.setColorFilter(Color.parseColor("#8033bbbb"), PorterDuff.Mode.SCREEN);
        ivImage.setAlpha(0.9f);
        return v;
    }
}
