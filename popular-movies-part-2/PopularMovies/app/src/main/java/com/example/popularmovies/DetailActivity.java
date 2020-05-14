package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import utils.AppDatabase;
import utils.AppExecutors;
import utils.Comment;
import utils.Movie;
import utils.Trailer;

public class DetailActivity extends AppCompatActivity {

    private ImageView mPoster, mShare;
    private TextView mTitle, mDate, mDuration, mOverview, mVote, mFavInst;
    private RecyclerView mTrailerRecycle, mCommentRecycle;
    private TrailerRecyclerViewAdapter mAdapter;
    private CommentRecyclerViewAdapter mCommentAdapter;
    private TrailerViewModel viewModel;
    private CommentViewModel commentViewModel;
    private CheckBox mBox;
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
        mVote = findViewById(R.id.tv_detail_vote);
        mFavInst = findViewById(R.id.fav_inst);
        mBox = findViewById(R.id.cb_detail_fav);
        mOverview = findViewById(R.id.tv_detail_overview);
        mTrailerRecycle = findViewById(R.id.rv_trailers);
        mCommentRecycle = findViewById(R.id.rv_comments);
        mShare = findViewById(R.id.share_btn);
        mCommentRecycle.setLayoutManager(new LinearLayoutManager(this));
        mTrailerRecycle.setLayoutManager(new LinearLayoutManager(this));
        mAdapter = new TrailerRecyclerViewAdapter(this);
        mCommentAdapter = new CommentRecyclerViewAdapter(this);
        mTrailerRecycle.setAdapter(mAdapter);
        mCommentRecycle.setAdapter(mCommentAdapter);
        viewModel = ViewModelProviders.of(this).get(TrailerViewModel.class);
        commentViewModel = ViewModelProviders.of(this).get(CommentViewModel.class);
        populateUI(movie);
        viewModel.getTrailers().observe(this, new Observer<ArrayList<Trailer>>() {
            @Override
            public void onChanged(ArrayList<Trailer> trailers) {
                mAdapter.setTrailers(trailers);
            }
        });
        commentViewModel.getComments().observe(this, new Observer<ArrayList<Comment>>() {
            @Override
            public void onChanged(ArrayList<Comment> comments) {
                mCommentAdapter.setComments(comments);
            }
        });
        mShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(mAdapter.trailers.size() > 0) {
                    String link = mAdapter.trailers.get(0).getTrailerKey();
                    link = "https://www.youtube.com/watch?v=" + link;
                    Intent intent = new Intent(Intent.ACTION_SEND);
                    intent.setType("text/plain");
                    intent.putExtra(Intent.EXTRA_SUBJECT, "Hey! Checkout this movie.");
                    intent.putExtra(Intent.EXTRA_TEXT, link);
                    startActivity(intent);
                }
            }
        });
    }

    private void populateUI(final Movie movie){
        mPoster.setImageBitmap(movie.getPoster());
        mTitle.setText(movie.getTitle());
        mDate.setText(movie.getDate());
        String vote_on_ten = movie.getVote() + "/10";
        mVote.setText(vote_on_ten);
        final Context context = this;
        AppDatabase.getInstance(context).movieDAO().isFavouriteLive(movie.getId()).observe(this,
                new Observer<Movie>() {
                    @Override
                    public void onChanged(Movie movie) {
                        if(movie == null){
                            mFavInst.setText(getString(R.string.ad_fav));
                            mBox.setChecked(false);
                        } else {
                            mFavInst.setText(getString(R.string.rm_fav));
                            mBox.setChecked(true);
                        }
                    }
                });
        mBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        if(mBox.isChecked()) {
//                            mFavInst.setText(getString(R.string.rm_fav));
                            AppDatabase.getInstance(context).movieDAO().addFavourite(movie);
                        } else {
//                            mFavInst.setText(getString(R.string.ad_fav));
                            AppDatabase.getInstance(context).movieDAO().deleteFavourite(movie);
                        }
                    }
                });
            }
        });
        mOverview.setText(movie.getOverview());
        viewModel.callAPI(movie.getId());
        commentViewModel.callAPI(movie.getId());
    }
}
