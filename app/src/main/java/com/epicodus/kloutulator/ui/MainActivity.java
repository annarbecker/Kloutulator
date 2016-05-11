package com.epicodus.kloutulator.ui;

import android.content.Intent;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.epicodus.kloutulator.Constants;
import com.epicodus.kloutulator.R;

import org.parceler.Parcels;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.usernameEditText) EditText mUsernameTextEdit;
    @Bind(R.id.searchButton) Button mSerachButton;

    private SharedPreferences mSharedPreferences;
    private SharedPreferences.Editor mEditor;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSerachButton.setOnClickListener(this);

        mSharedPreferences = PreferenceManager.getDefaultSharedPreferences(this);
        mEditor = mSharedPreferences.edit();
    }

    @Override
    public void onClick(View v) {
        if(v == mSerachButton) {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            String searchedUsername = mUsernameTextEdit.getText().toString();
            addToSharedPreferences(searchedUsername);
            startActivity(intent);
        }
    }

    private void addToSharedPreferences(String searchedUsername) {
        mEditor.putString(Constants.PREFERENCES_USERNAME_KEY, searchedUsername).apply();
    }
}
