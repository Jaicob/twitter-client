package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;

import java.util.List;

/**
 * Created by Jaicob on 8/4/16.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    private List<Tweet> mTweets;
    private Context mContext;

    //region Constructor
    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }
    //endregion


    @Override
    public ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater inflater = LayoutInflater.from(context);

        View contactView = inflater.inflate(R.layout.item_tweet, parent, false);

        ViewHolder viewHolder = new ViewHolder(contactView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(ViewHolder holder, int position) {
        Tweet tweet = mTweets.get(position);

        holder.tvText.setText(tweet.getText());
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //region ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvText;

        public ViewHolder(View itemView){
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
        }
    }
    //endregion


}
