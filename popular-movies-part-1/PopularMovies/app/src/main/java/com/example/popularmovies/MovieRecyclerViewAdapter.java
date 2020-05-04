package com.example.popularmovies;

import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import utils.Movie;
import utils.MovieDBUtils;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    LayoutInflater mInflater;
    ArrayList<Movie> mData;
    MovieDBUtils utils;
    Context context;
    private static MovieRecyclerViewAdapter instance;

    public static MovieRecyclerViewAdapter getInstance(Context context){
        if (instance == null)
            instance = new MovieRecyclerViewAdapter(context);
        return instance;
    }

    private MovieRecyclerViewAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        utils = MovieDBUtils.getInstance();
        utils.start("highestRated", this);
        this.context = context;
        mData = new ArrayList<>();
    }

    public void setmData(ArrayList<Movie> newData){
        mData = newData;
        notifyDataSetChanged();
    }

    public void imageLoad(){
        notifyDataSetChanged();
    }

    public Movie getMovie(int index){
        if(index < mData.size())
            return mData.get(index);
        return null;
    }

    @NonNull
    @Override
    public MovieRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_movie, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Movie m = mData.get(position);
        holder.mTitleTextView.setText(m.getTitle());
        holder.mRatingValue.setText(m.getVote()+"");
        holder.mRatingBar.setProgress((int) m.getVote()*10);
        holder.mImageView.setImageBitmap(m.getPoster());
//        Log.i("API", m.getPoster()+"");
        holder.mDate.setText(m.getDate());

    }

    @Override
    public int getItemCount() {
        return mData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        ImageView mImageView;
        TextView mTitleTextView;

        ProgressBar mRatingBar;
        TextView mRatingValue;
        TextView mDate;
        public ViewHolder(View view){
            super(view);
            mTitleTextView = view.findViewById(R.id.tv_movie_title);
            mRatingBar = view.findViewById(R.id.pb_rating_bar);
            mRatingValue = view.findViewById(R.id.tv_rating);
            mImageView = view.findViewById(R.id.iv_movie_poster);
            mDate = view.findViewById(R.id.tv_movie_date);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Intent intent = new Intent(context, DetailActivity.class);
            intent.putExtra(context.getString(R.string.movie_index_key), index);
            context.startActivity(intent);
        }
    }
}
