package com.example.popularmovies;

import android.content.Context;
import android.net.ConnectivityManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import utils.AppDatabase;
import utils.AppExecutors;
import utils.Movie;

public class FavRecyclerViewAdapter extends RecyclerView.Adapter<FavRecyclerViewAdapter.ViewHolder> {

    private LayoutInflater inflater;
    private ArrayList<Movie> mMovies;
    private Context context;
    FavRecyclerViewAdapter(Context context){
        mMovies = new ArrayList<>();
        inflater = LayoutInflater.from(context);
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = inflater.inflate(R.layout.rv_fav, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolder holder, final int position) {
        holder.mTitle.setText(mMovies.get(position).getTitle());
        holder.mScore.setText(mMovies.get(position).getVote() + "/10");
//        holder.mFavBox.setChecked(mMovies.get(position).getFavourite());
        holder.mFavBox.setChecked(true);
        holder.mFavBox.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(holder.mFavBox.isChecked()){
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(context).movieDAO().addFavourite(mMovies.get(position));
                        }
                    });
                } else {
                    AppExecutors.getInstance().diskIO().execute(new Runnable() {
                        @Override
                        public void run() {
                            AppDatabase.getInstance(context).movieDAO().deleteFavourite(mMovies.get(position));
                        }
                    });
                }
            }
        });

    }

    @Override
    public int getItemCount() {
        return mMovies.size();
    }

    public void setMovies(ArrayList<Movie> m){
        mMovies = m;
        notifyDataSetChanged();
    }

    public ArrayList<Movie> getMovies(){
        return mMovies;
    }

    protected class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mTitle, mScore;
        CheckBox mFavBox;
        ViewHolder(View view){
            super(view);
            mTitle = view.findViewById(R.id.fav_title);
            mScore = view.findViewById(R.id.fav_score);
            mFavBox = view.findViewById(R.id.fav_star);
        }

        @Override
        public void onClick(View v) {

        }
    }

}
