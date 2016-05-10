package com.epicodus.kloutulator;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import butterknife.Bind;
import butterknife.ButterKnife;

public class MainActivity extends AppCompatActivity implements View.OnClickListener{
    @Bind(R.id.usernameEditText) EditText mUsernameTextEdit;
    @Bind(R.id.searchButton) Button mSerachButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        ButterKnife.bind(this);
        mSerachButton.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        if(v == mSerachButton) {
            Intent intent = new Intent(MainActivity.this, ResultActivity.class);
            String searchedUsername = mUsernameTextEdit.getText().toString();
            intent.putExtra("searchedUsername", searchedUsername);
            startActivity(intent);
        }
    }
}
