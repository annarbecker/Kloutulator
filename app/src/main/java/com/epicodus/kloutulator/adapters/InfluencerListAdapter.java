package com.epicodus.kloutulator.adapters;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.kloutulator.R;
import com.epicodus.kloutulator.models.Influencer;

import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;

/**
 * Created by Guest on 5/11/16.
 */
public class InfluencerListAdapter  extends RecyclerView.Adapter<InfluencerListAdapter.InfluencerViewHolder> {
    private ArrayList<Influencer> mInfluencers = new ArrayList<>();
    private Context mCContext;

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
        }

        public void bindInfluencer(Influencer influencer) {
            mNameTextView.setText(influencer.getName());
        }

    }
}
