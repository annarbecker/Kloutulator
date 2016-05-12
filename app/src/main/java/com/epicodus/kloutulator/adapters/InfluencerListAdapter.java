package com.epicodus.kloutulator.adapters;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.kloutulator.Constants;
import com.epicodus.kloutulator.R;
import com.epicodus.kloutulator.models.Influencer;
import com.epicodus.kloutulator.ui.ResultActivity;
import com.epicodus.kloutulator.ui.UserDetailFragment;

import java.sql.ResultSet;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 5/11/16.
 */
public class InfluencerListAdapter  extends RecyclerView.Adapter<InfluencerListAdapter.InfluencerViewHolder> {
    private ArrayList<Influencer> mInfluencers = new ArrayList<>();
    private Context mCContext;
    private SharedPreferences mSharedPreferences;
    private String mSearchedUsername;
    private SharedPreferences.Editor mEditor;


    public InfluencerListAdapter(Context context, ArrayList<Influencer> influencers) {
        mCContext = context;
        mInfluencers = influencers;
    }

    @Override
    public InfluencerListAdapter.InfluencerViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.influencer_list_item, parent, false);
        return new InfluencerViewHolder(view);
    }

    @Override
    public void onBindViewHolder(InfluencerListAdapter.InfluencerViewHolder holder, int position) {
        holder.bindInfluencer(mInfluencers.get(position));
    }

    @Override
    public int getItemCount() {
        return mInfluencers.size();
    }

    public class InfluencerViewHolder extends RecyclerView.ViewHolder {
        private Context mContext;
        @Bind(R.id.nameTextView) TextView mNameTextView;

        public InfluencerViewHolder(View itemView) {
            super(itemView);
            ButterKnife.bind(this, itemView);
            mContext = itemView.getContext();
            mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(mContext);
            mEditor = mSharedPreferences.edit();

            itemView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    int itemPosition = getLayoutPosition();
                    Intent intent = new Intent(mContext, ResultActivity.class);

                    mSearchedUsername = mInfluencers.get(itemPosition).getName();
                    Log.d("CLICKED USERNAME ", mSearchedUsername);

                    addToSharedPreferences(mSearchedUsername);
                    mCContext.startActivity(intent);
                }
            });
        }

        public void bindInfluencer(Influencer influencer) {
            mNameTextView.setText(influencer.getName());
        }

    }

    private void addToSharedPreferences(String searchedUsername) {
        mEditor.putString(Constants.PREFERENCES_USERNAME_KEY, searchedUsername).apply();
    }
}
