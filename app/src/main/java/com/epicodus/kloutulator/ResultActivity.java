package com.epicodus.kloutulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.Response;

public class ResultActivity extends AppCompatActivity {
    public static final String TAG = ResultActivity.class.getSimpleName();

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
                        Log.d(TAG, score + "");
                    }
                });


            }
        });
    }

//    private void getPerson() {
//        final KloutService kloutService = new KloutService();
//
//
//    }
}
