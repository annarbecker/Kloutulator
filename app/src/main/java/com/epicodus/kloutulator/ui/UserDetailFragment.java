package com.epicodus.kloutulator.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.annotation.BinderThread;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.epicodus.kloutulator.Constants;
import com.epicodus.kloutulator.R;
import com.epicodus.kloutulator.models.Influencer;
import com.epicodus.kloutulator.services.KloutService;

import org.parceler.Parcels;

import java.io.IOException;
import java.util.ArrayList;

import butterknife.Bind;
import butterknife.ButterKnife;
import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

/**
 * A simple {@link Fragment} subclass.
 */
public class UserDetailFragment extends Fragment {
    public static final String TAG = UserDetailFragment.class.getSimpleName();

    @Bind(R.id.usernameTextView) TextView mUsernameTextView;
    @Bind(R.id.ratingTextView) TextView mRatingTextView;
    @Bind(R.id.dayChangeTextView) TextView mDayChangeTextView;
    @Bind(R.id.weekChangeTextView) TextView mWeekChangeTextView;
    @Bind(R.id.monthChangeTextView) TextView mMonthChangeTextView;
    @Bind(R.id.influenceesRecyclerView) RecyclerView mInfluenceesRecyclerView;
    @Bind(R.id.influencersRecyclerView) RecyclerView getmInfluencersRecyclerView;

    public ArrayList<Influencer> mInfluencers;
    public String kloutID;
    private SharedPreferences mSharedPreferences;
    private String mSearchedUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(getActivity());
        mSearchedUsername = mSharedPreferences.getString(Constants.PREFERENCES_USERNAME_KEY, null);

        getKlout(mSearchedUsername);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);

        return view;
    }

    private void getKlout(final String searchedUsername) {
        final KloutService kloutService = new KloutService();

        kloutService.findKloutID(searchedUsername, new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                e.printStackTrace();
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                kloutService.processResults(response);
                kloutID = kloutService.getKloutID();
                Log.d(TAG, kloutID);

                kloutService.findPerson(kloutID, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        kloutService.processUserResults(response);
                        String score = kloutService.getScore();
                        String dayChange = kloutService.getDayChange();
                        String weekChange = kloutService.getWeekChange();
                        String monthChange = kloutService.getMonthChange();
                        Log.d(TAG, score + "");
                        Log.d(TAG, dayChange + "");
                        Log.d(TAG, weekChange + "");
                        Log.d(TAG, monthChange + "");


                    }
                });

                kloutService.findInfluence(kloutID, new Callback() {
                    @Override
                    public void onFailure(Call call, IOException e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onResponse(Call call, Response response) throws IOException {
                        mInfluencers = kloutService.processInfluenceResults(response);
                    }
                });
            }
        });
    }

}
