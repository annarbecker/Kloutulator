package com.epicodus.kloutulator;

import android.util.Log;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.IOException;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

/**
 * Created by Guest on 5/10/16.
 */
public class KloutService {
    public static final String TAG = KloutService.class.getSimpleName();
    public String kloutID;
    public String score;

    public void findKloutID(String searchedUsername, Callback callback) {
        String KLOUT_API_KEY = Constants.KLOUT_API_KEY;
        String KLOUT_BASE_URL = Constants.KLOUT_BASE_URL;
        String API_PARAM = Constants.API_PARAM;
        String SEARCH_PARAM = Constants.SEARCH_PARAM;

        OkHttpClient client = new OkHttpClient.Builder().build();


        HttpUrl.Builder urlBuilder = HttpUrl.parse(KLOUT_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(API_PARAM, KLOUT_API_KEY);
        urlBuilder.addQueryParameter(SEARCH_PARAM, searchedUsername);
        String url = urlBuilder.build().toString();
        Log.d( "URL:", url);

        Request request = new Request.Builder().url(url).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void processResults(Response response) {

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject personJSON = new JSONObject(jsonData);
                kloutID = personJSON.getString("id");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getKloutID() {
        return kloutID;
    }

    public void findPerson(String kloutID, Callback callback) {
        String KLOUT_API_KEY = Constants.KLOUT_API_KEY;
        String USER_BASE_URL = Constants.USER_BASE_URL;
        String API_PARAM = Constants.API_PARAM;
        String KLOUT_ID_PARAM = kloutID;

        OkHttpClient client = new OkHttpClient.Builder().build();


        HttpUrl.Builder urlBuilder = HttpUrl.parse(USER_BASE_URL).newBuilder();
        urlBuilder.addQueryParameter(API_PARAM, KLOUT_API_KEY);
        urlBuilder.addPathSegment(KLOUT_ID_PARAM);
        String userUrl = urlBuilder.build().toString();
        Log.d( "USER URL:", userUrl);

        Request request = new Request.Builder().url(userUrl).build();

        Call call = client.newCall(request);
        call.enqueue(callback);
    }

    public void processUserResults(Response response) {

        try {
            String jsonData = response.body().string();
            if (response.isSuccessful()) {
                JSONObject personJSON = new JSONObject(jsonData);
                score = personJSON.getJSONObject("score").getString("score");
                String dayChange = personJSON.getJSONObject("scoreDeltas").getString("dayChange");
                String weekChange = personJSON.getJSONObject("scoreDeltas").getString("weekChange");
                String monthChange = personJSON.getJSONObject("scoreDeltas").getString("monthChange");
            }
        } catch (IOException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
    public String getScore() {
        return score;
    }
}
