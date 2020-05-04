package com.example.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import utils.Movie;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPoster;
    private TextView mTitle, mDate, mDuration, mOverview, mVote;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Intent intent = getIntent();
        int index = 1;
        if(intent.hasExtra(getString(R.string.movie_index_key))){
            index = intent.getIntExtra(getString(R.string.movie_index_key),1);
        }
        setContentView(R.layout.activity_detail);
        MovieRecyclerViewAdapter instance = MovieRecyclerViewAdapter.getInstance(this);
        Movie movie = instance.getMovie(index);
        mPoster = findViewById(R.id.iV_detail_poster);
        mTitle = findViewById(R.id.tv_detail_title);
        mDate = findViewById(R.id.tv_detail_date);
//        mDuration = findViewById(R.id.tv_detail_duration);
        mVote = findViewById(R.id.tv_detail_vote);
        mOverview = findViewById(R.id.tv_detail_overview);
        populateUI(movie);
    }

    private void populateUI(Movie movie){
        mPoster.setImageBitmap(movie.getPoster());
        mTitle.setText(movie.getTitle());
        mDate.setText(movie.getDate());
        String vote_on_ten = movie.getVote() + "/10";
        mVote.setText(vote_on_ten);
//        mDuration.setText(movie.getDuration());
        mOverview.setText(movie.getOverview());
    }
}
