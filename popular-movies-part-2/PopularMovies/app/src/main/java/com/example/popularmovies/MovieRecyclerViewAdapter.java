package com.example.popularmovies;

import android.animation.ObjectAnimator;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;


import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import utils.AppDatabase;
import utils.AppExecutors;
import utils.Movie;
import utils.MovieDBUtils;

public class MovieRecyclerViewAdapter extends RecyclerView.Adapter<MovieRecyclerViewAdapter.ViewHolder> {
    LayoutInflater mInflater;
    ArrayList<Movie> mData;
    MovieDBUtils utils;
    Context context;
    private static MovieRecyclerViewAdapter instance;
    private AppDatabase mAppDatabase;

    public static MovieRecyclerViewAdapter getInstance(Context context){
        if (instance == null)
            instance = new MovieRecyclerViewAdapter(context);
        return instance;
    }

    private MovieRecyclerViewAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        this.context = context;
        mAppDatabase = AppDatabase.getInstance(context);
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
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        final Movie m = mData.get(position);
        holder.mTitleTextView.setText(m.getTitle());
        holder.mRatingValue.setText(m.getVote()+"");
        holder.mRatingBar.setProgress((int) m.getVote()*10);
        holder.mImageView.setImageBitmap(m.getPoster());
        holder.mDate.setText(m.getDate());
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                Movie mFavMovie = AppDatabase.getInstance(context).movieDAO().isFavourite(m.getId());
                holder.mCheckBox.setChecked(mFavMovie != null);
            }
        });
        AppDatabase.getInstance(context).movieDAO().isFavouriteLive(m.getId()).observe((AppCompatActivity)context,
                new Observer<Movie>() {
                    @Override
                    public void onChanged(Movie movie) {
                        if(movie == null)
                            holder.mCheckBox.setChecked(false);
                        else
                            holder.mCheckBox.setChecked(true);
                    }
                });
        holder.mCheckBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int movieId = m.getId();
                if(holder.mCheckBox.isChecked()) {
                    Log.i("API", "Adding " + m.getTitle());
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            m.setFavourite(true);
                            mAppDatabase.movieDAO().addFavourite(m);
                        }
                    });
                }else{
                    Log.i("API", "Removing " + m.getTitle());
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            m.setFavourite(false);
                            mAppDatabase.movieDAO().deleteFavourite(m);
                        }
                    });
                }
            }
        });
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
        CheckBox mCheckBox;
        public ViewHolder(View view){
            super(view);
            mTitleTextView = view.findViewById(R.id.tv_movie_title);
            mRatingBar = view.findViewById(R.id.pb_rating_bar);
            mRatingValue = view.findViewById(R.id.tv_rating);
            mImageView = view.findViewById(R.id.iv_movie_poster);
            mDate = view.findViewById(R.id.tv_movie_date);
            mCheckBox = view.findViewById(R.id.cb_fav);
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
