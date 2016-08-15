package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;

import java.util.ArrayList;

/**
 * Created by Jaicob on 8/14/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {

    public static TweetsListFragment newInstance() {
        HomeTimelineFragment fragment = new HomeTimelineFragment();
        return fragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        mTweets = new ArrayList<>();
        adapter = new TweetAdapter(getActivity(), mTweets);
        super.onCreate(savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);
        return v;
    }

}
