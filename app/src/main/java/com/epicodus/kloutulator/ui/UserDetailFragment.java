package com.epicodus.kloutulator.ui;


import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.preference.PreferenceManager;
import android.support.v4.app.Fragment;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.epicodus.kloutulator.Constants;
import com.epicodus.kloutulator.R;
import com.epicodus.kloutulator.models.Influencer;
import com.epicodus.kloutulator.services.KloutService;

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
    public String score;
    private String dayChange;
    private String monthChange;
    private String weekChange;

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

        View view = inflater.inflate(R.layout.fragment_user_detail, container, false);
        ButterKnife.bind(this, view);

        mUsernameTextView.setText(mSearchedUsername);
        setScore(score);

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

                if(kloutID == null) {
                    Log.d("No user", "by that name");
                    Intent intent = new Intent(getActivity(), MainActivity.class);
                    startActivity(intent);

                    Handler handler = new Handler(Looper.getMainLooper());
                    handler.post(new Runnable() {
                        @Override
                        public void run() {
                            Toast.makeText(getActivity(), "INVALID USERNAME", Toast.LENGTH_LONG).show();
                        }
                    });
                } else {
                    Log.d(TAG, kloutID);
                    kloutService.findPerson(kloutID, new Callback() {
                        @Override
                        public void onFailure(Call call, IOException e) {
                            e.printStackTrace();
                        }

                        @Override
                        public void onResponse(Call call, Response response) throws IOException {
                            kloutService.processUserResults(response);
                            score = kloutService.getScore();
                            Log.d(TAG, score);


//                            dayChange = kloutService.getDayChange();
//                            setDayChange(dayChange);
//
//
//                            weekChange = kloutService.getWeekChange();
//                            setWeekChange(weekChange);
//
//
//                            monthChange = kloutService.getMonthChange();
//                            setMonthChange(monthChange);
//
//                            Log.d(TAG, dayChange);
//                            Log.d(TAG, weekChange);
//                            Log.d(TAG, monthChange);

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
            }
        });
    }

    public void setScore(String score) {
        mRatingTextView.setText(score);
    }

    public void setDayChange(String dayChange) {
        mDayChangeTextView.setText(dayChange);
    }

    public void setWeekChange(String weekChange) {
        mWeekChangeTextView.setText(weekChange);
    }

    public void setMonthChange(String monthChange) {
        mMonthChangeTextView.setText(monthChange);

    }

}
