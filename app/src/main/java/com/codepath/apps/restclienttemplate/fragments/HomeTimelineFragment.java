package com.codepath.apps.restclienttemplate.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.TwitterClientApplication;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.network.TwitterClient;

import java.util.ArrayList;

/**
 * Created by Jaicob on 8/14/16.
 */
public class HomeTimelineFragment extends TweetsListFragment {
    private TwitterClient client;
    private TweetAdapter adapter;
    private ArrayList<Tweet> mTweets;
    private RecyclerView rvTweets;
    private SwipeRefreshLayout swipeContainer;

    public static TweetsListFragment newInstance() {
        Bundle args = new Bundle();
        HomeTimelineFragment fragment = new HomeTimelineFragment();
        return fragment;
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = super.onCreateView(inflater,container,savedInstanceState);

        return v;
    }

    // Creation lifecycle
    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterClientApplication.getRestClient();
        mTweets = new ArrayList<>();
        adapter = new TweetAdapter(getActivity(), mTweets);
    }


}
