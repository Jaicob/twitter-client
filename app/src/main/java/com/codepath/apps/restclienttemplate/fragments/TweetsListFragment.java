package com.codepath.apps.restclienttemplate.fragments;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClientApplication;
import com.codepath.apps.restclienttemplate.activities.ProfileActivity;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;
import org.parceler.Parcels;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

/**
 * Created by Jaicob on 8/13/16.
 */
public class TweetsListFragment extends Fragment {
    private TwitterClient client;
    protected TweetAdapter adapter;
    protected ArrayList<Tweet> mTweets;
    private RecyclerView rvTweets;
    private SwipeRefreshLayout swipeContainer;


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterClientApplication.getRestClient();
        mTweets = new ArrayList<>();
        adapter = new TweetAdapter(getActivity(), mTweets);
    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
        setupAdapter();

        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);
        populateTimeline();
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String max_id = mTweets.get(mTweets.size()-1).getTweetId().toString();
                populateTimelinePaginated(max_id);
            }
        });


        swipeContainer = (SwipeRefreshLayout) v.findViewById(R.id.swipeContainer);
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                populateTimeline();
            }
        });
        swipeContainer.setColorSchemeResources(R.color.colorLightest,
                R.color.colorLight,
                R.color.colorMedium,
                R.color.colorDark);
        swipeContainer.setProgressBackgroundColorSchemeColor(Color.parseColor("#003333"));

        return v;
    }

    public void setupAdapter() {
        adapter.setOnItemClickListener(new TweetAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View view, int position) {
                Tweet t = mTweets.get(position);
                User user = t.getUser();

                Intent intent = new Intent(getActivity(), ProfileActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
            }
        });
    }


    public void populateTimelinePaginated(String max_id){
        client.getHomeTimeline(max_id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mTweets.clear();
                mTweets.addAll(Tweet.jsonArrayToTweets(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void populateTimeline(){
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                // TODO: make this use since ID instead of reloading all tweets
                mTweets.clear();
                mTweets.addAll(Tweet.jsonArrayToTweets(response));
                adapter.notifyDataSetChanged();
                swipeContainer.setRefreshing(false);
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
            }
        });
    }

    public void addAllTweets(List<Tweet> tweets){
        mTweets.addAll(tweets);
        adapter.notifyDataSetChanged();
    }

    public void addTweet(Tweet tweet) {
        mTweets.add(0,tweet);
        adapter.notifyDataSetChanged();
    }

    public void clearTweets(){
        mTweets.clear();
        adapter.notifyDataSetChanged();
    }

}
