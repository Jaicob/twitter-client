package com.codepath.apps.restclienttemplate.fragments;

import android.app.Fragment;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.annotation.Nullable;
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
public class ProfileInfoFragment extends Fragment {
    private User user;
    private ImageView ivImage;
    private TextView tvScreenName;
    private TextView tvFollowers;
    private TextView tvFollowing;
    private TextView tvLocation;
    private TextView tvDescription;
    private TextView tvNumTweets;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) Parcels.unwrap(getActivity().getIntent().getParcelableExtra("user"));
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_profile_info, container, false);

        ivImage = (ImageView) v.findViewById(R.id.ivBackgroundp);
        ivImage.setColorFilter(Color.parseColor("#112222"), PorterDuff.Mode.SCREEN);
        ivImage.setAlpha(0.75f);


        tvScreenName = (TextView) v.findViewById(R.id.tvScreenNamep);
        tvFollowers = (TextView) v.findViewById(R.id.tvFollowersp);
        tvFollowing = (TextView) v.findViewById(R.id.tvFollowingp);
        tvLocation = (TextView) v.findViewById(R.id.tvLocationp);
        tvDescription = (TextView) v.findViewById(R.id.tvDescriptionp);
        tvNumTweets = (TextView) v.findViewById(R.id.tvNumTweetsp);
        ivImage = (ImageView) v.findViewById(R.id.ivProfileImagep);

        tvScreenName.setText(user.getScreenName());
        tvFollowers.setText(user.getFollowers_count().toString());
        tvFollowing.setText(user.getFriends_count().toString());
        tvLocation.setText("Location: " + user.getLocation());
        tvDescription.setText(user.getDescription());
        tvNumTweets.setText(user.getTweet_count().toString());

        Picasso.with(getContext()).load(user.getProfileImageUrl()).into(ivImage);
        ivImage.setColorFilter(Color.parseColor("#8033bbbb"), PorterDuff.Mode.SCREEN);
        ivImage.setAlpha(0.9f);
        return v;
    }
}
