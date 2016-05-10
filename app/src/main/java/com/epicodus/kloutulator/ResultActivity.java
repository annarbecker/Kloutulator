package com.epicodus.kloutulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;
import java.util.ArrayList;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {
    public static final String TAG = ResultActivity.class.getSimpleName();
    public ArrayList<Influencer> mInfluencers;
    public String kloutID;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        Intent intent = getIntent();
        String searchedUsername = intent.getStringExtra("searchedUsername");
        getKlout(searchedUsername);
//        getPerson();
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

//    private void getPerson() {
//        final KloutService kloutService = new KloutService();
//
//
//    }
