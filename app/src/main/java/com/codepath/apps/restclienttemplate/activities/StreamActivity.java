package com.codepath.apps.restclienttemplate.activities;

import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.ComposeTweetDialogFragment;
import com.codepath.apps.restclienttemplate.EndlessRecyclerViewScrollListener;
import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClientApplication;
import com.codepath.apps.restclienttemplate.adapters.TweetAdapter;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class StreamActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogListener {

    private TwitterClient client;
    private TweetAdapter adapter;
    private ArrayList<Tweet> mTweets;
    private RecyclerView rvTweets;
    private ImageView ivImage;
    private SwipeRefreshLayout swipeContainer;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");

        ivImage = (ImageView) findViewById(R.id.ivBackground);
        ivImage.setColorFilter(Color.parseColor("#112222"), PorterDuff.Mode.SCREEN);
        ivImage.setAlpha(0.75f);

        client = TwitterClientApplication.getRestClient();
        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        mTweets = new ArrayList<>();
        adapter = new TweetAdapter(this, mTweets);
        rvTweets.setAdapter(adapter);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        populateTimeline();

        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                String max_id = mTweets.get(mTweets.size()-1).getTweetId().toString();
                populateTimelinePaginated(max_id);
            }
        });

        swipeContainer = (SwipeRefreshLayout) findViewById(R.id.swipeContainer);
        // Setup refresh listener which triggers new data loading
        swipeContainer.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                // Your code to refresh the list here.
                // Make sure you call swipeContainer.setRefreshing(false)
                // once the network request has completed successfully.
                populateTimeline();
            }
        });
        // Configure the refreshing colors
        swipeContainer.setColorSchemeResources(R.color.colorLightest,
                R.color.colorLight,
                R.color.colorMedium,
                R.color.colorDark);
        swipeContainer.setProgressBackgroundColorSchemeColor(Color.parseColor("#003333"));


        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showEditDialog();
            }
        });
    }

    public void populateTimelinePaginated(String max_id){
        client.getHomeTimeline(max_id, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                mTweets.addAll(Tweet.jsonArrayToTweets(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
//                mTweets.addAll(Tweet.listAll(Tweet.class));
//                adapter.notifyDataSetChanged();
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
//                 mTweets.addAll((ArrayList<Tweet>) Tweet.listAll(Tweet.class));
//                 adapter.notifyDataSetChanged();
             }

             @Override
             public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                 super.onFailure(statusCode, headers, responseString, throwable);
//                 mTweets.addAll(Tweet.listAll(Tweet.class));
//                 adapter.notifyDataSetChanged();
             }
         });
    }

    private void showEditDialog() {
        FragmentManager fm = getSupportFragmentManager();
        ComposeTweetDialogFragment composeTweetDialogFragment = ComposeTweetDialogFragment.newInstance("Some Title");
        composeTweetDialogFragment.show(fm, "fragment_compose_tweet");
    }

    public void onFinishComposeTweetDialog(String status){
        client.postTweet(status, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                Toast.makeText(StreamActivity.this, "Report Submitted", Toast.LENGTH_SHORT).show();
                mTweets.add(0,new Tweet(response));
                adapter.notifyDataSetChanged();
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(StreamActivity.this, "Failed to submit report", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
