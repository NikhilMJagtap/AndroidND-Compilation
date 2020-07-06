package com.example.jokelibrary;

import android.os.Bundle;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class JokeActivity extends AppCompatActivity {
    private static final String JOKE_KEY = "JOKE_KEY";
    private TextView jokeTextView;
    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_joke);
        jokeTextView = (TextView) findViewById(R.id.joke_tv);
        String joke = getIntent().getStringExtra(JOKE_KEY);
        jokeTextView.setText(joke);
    }

}
