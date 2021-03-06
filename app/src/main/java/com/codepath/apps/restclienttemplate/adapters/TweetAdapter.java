package com.codepath.apps.restclienttemplate.adapters;

import android.content.Context;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.restclienttemplate.R;
import com.codepath.apps.restclienttemplate.models.Tweet;
import com.codepath.apps.restclienttemplate.models.User;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by Jaicob on 8/4/16.
 */
public class TweetAdapter extends RecyclerView.Adapter<TweetAdapter.ViewHolder>{
    private List<Tweet> mTweets;
    private Context mContext;
    private static OnItemClickListener listener;
    //region Constructor
    public TweetAdapter(Context context, List<Tweet> tweets) {
        mContext = context;
        mTweets = tweets;
    }


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
        User user = tweet.getUser();

        holder.tvText.setText(tweet.getText());
        holder.tvLocation.setText("Location: " + user.getLocation());
        holder.tvCreatedAt.setText(tweet.getCreatedAt());
        holder.tvScreenName.setText(user.getScreenName());

        Picasso.with(mContext).load(user.getProfileImageUrl()).into(holder.ivProfileImage);
        holder.ivProfileImage.setColorFilter(Color.parseColor("#8033bbbb"), PorterDuff.Mode.SCREEN);
        holder.ivProfileImage.setAlpha(0.9f);
    }
    //endregion

    public interface OnItemClickListener {
        void onItemClick(View itemView, int position);
    }
    public void setOnItemClickListener(OnItemClickListener listener) {
        this.listener = listener;
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }

    //region ViewHolder
    public static class ViewHolder extends RecyclerView.ViewHolder{
        public TextView tvText;
        public TextView tvLocation;
        public TextView tvCreatedAt;
        public TextView tvScreenName;
        public ImageView ivProfileImage;

        public ViewHolder(final View itemView){
            super(itemView);
            tvText = (TextView) itemView.findViewById(R.id.tvText);
            tvLocation = (TextView) itemView.findViewById(R.id.tvLocation);
            tvCreatedAt = (TextView) itemView.findViewById(R.id.tvCreatedAt);
            tvScreenName = (TextView) itemView.findViewById(R.id.tvScreenName);
            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    // Triggers click upwards to the adapter on click
                    if (listener != null)
                        listener.onItemClick(itemView, getLayoutPosition());
                }
            });
        }
    }
    //endregion

    // Clean all elements of the recycler
    public void clear() {
        mTweets.clear();
        notifyDataSetChanged();
    }

    // Add a list of items
    public void addAll(List<Tweet> list) {
        mTweets.addAll(list);
        notifyDataSetChanged();
    }
}
