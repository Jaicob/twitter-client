package com.codepath.apps.restclienttemplate.activities;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.os.Bundle;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.TabLayout;
import android.support.v4.app.FragmentManager;
import android.support.v4.view.ViewPager;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.TwitterClientApplication;
import com.codepath.apps.restclienttemplate.adapters.TimelineFragmentPageAdapter;
import com.codepath.apps.restclienttemplate.fragments.ComposeTweetDialogFragment;
import com.codepath.apps.restclienttemplate.fragments.TweetsListFragment;
import com.codepath.apps.restclienttemplate.models.User;
import com.codepath.apps.restclienttemplate.network.TwitterClient;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;
import org.parceler.Parcels;

import cz.msebera.android.httpclient.Header;

public class StreamActivity extends AppCompatActivity implements ComposeTweetDialogFragment.ComposeTweetDialogListener {

    private TwitterClient client;
    private ImageView ivImage;
    private TweetsListFragment tweetsListFragment;
    private int userId;
    private User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_stream);
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitle("");
        client = TwitterClientApplication.getRestClient();

        client.getCurrentUser(new JsonHttpResponseHandler(){

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Log.d("DEBUG________", "onSuccess: " + response);
                    userId = response.getInt("id");
                } catch (JSONException e) {
                    e.printStackTrace();
                }
                client.getOneUser(userId, new JsonHttpResponseHandler(){

                    @Override
                    public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                        super.onSuccess(statusCode, headers, response);
                        Log.d("DEBUGG_____", "onSuccess: " + response);
                        try {
                            user = new User(response);
                            getIntent().putExtra("user", Parcels.wrap(user));
                        } catch (Exception e) {
                            e.printStackTrace();
                        }
                    }

                    @Override
                    public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                        super.onFailure(statusCode, headers, throwable, errorResponse);
                        Log.d("DEBUG__________", "onFailure: " + errorResponse);

                    }
                });

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONArray errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);
                Log.d("DEBUG__________", "onFailure: " + errorResponse);
            }
        });


//        tweetsListFragment = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);

        ivImage = (ImageView) findViewById(R.id.ivBackground);
        ivImage.setColorFilter(Color.parseColor("#112222"), PorterDuff.Mode.SCREEN);
        ivImage.setAlpha(0.75f);


        final ViewPager viewPager = (ViewPager) findViewById(R.id.viewpager);
        TimelineFragmentPageAdapter pagerAdapter = new TimelineFragmentPageAdapter(getSupportFragmentManager(),
                StreamActivity.this);
        viewPager.setAdapter(pagerAdapter);

        // Give the TabLayout the ViewPager
        final TabLayout tabLayout = (TabLayout) findViewById(R.id.sliding_tabs);
        tabLayout.setupWithViewPager(viewPager);
        tabLayout.post(new Runnable() {
            @Override
            public void run() {
                tabLayout.setupWithViewPager(viewPager);
            }
        });

        for (int i = 0; i < tabLayout.getTabCount(); i++) {
            TabLayout.Tab tab = tabLayout.getTabAt(i);
            tab.setCustomView(pagerAdapter.getTabView(i));
        }

        FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
               showEditDialog();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.menu_stream, menu);

        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case R.id.miProfile:
                Intent intent = new Intent(this, ProfileActivity.class);
                intent.putExtra("user", Parcels.wrap(user));
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
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
//                tweetsListFragment.addTweet(new Tweet(response));
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Toast.makeText(StreamActivity.this, "Failed to submit report", Toast.LENGTH_SHORT).show();
            }
        });
    }


}
