package com.example.popularmovies;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.content.Intent;
import android.net.ConnectivityManager;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Toast;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

import utils.AppDatabase;
import utils.AppExecutors;
import utils.Movie;
import utils.MovieDBUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private int noOfColumns;
    private AppDatabase mAppDatabase;
    private MainViewModel mMainViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mAppDatabase = AppDatabase.getInstance(this);
        if(!isNetworkConnected()){
            Toast.makeText(this, "No internet connection. Will show favourite movies in database.",
                    Toast.LENGTH_LONG).show();
            Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
            startActivity(intent);
            return;
        }
        setContentView(R.layout.activity_main);
        mAppDatabase = AppDatabase.getInstance(this);
        mRecyclerView = findViewById(R.id.rv_movies);
        noOfColumns = getNoOfColumns(150);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, noOfColumns));

        movieRecyclerViewAdapter = MovieRecyclerViewAdapter.getInstance(this);
        mRecyclerView.setAdapter(movieRecyclerViewAdapter);

        mMainViewModel = ViewModelProviders.of(this).get(MainViewModel.class);
        mMainViewModel.getMovies().observe(this, new Observer<ArrayList<Movie>>() {
            @Override
            public void onChanged(ArrayList<Movie> movies) {
                movieRecyclerViewAdapter.setmData(movies);
            }
        });
    }

    private boolean isNetworkConnected() {
        ConnectivityManager cm = (ConnectivityManager) getSystemService(Context.CONNECTIVITY_SERVICE);

        return cm.getActiveNetworkInfo() != null && cm.getActiveNetworkInfo().isConnected();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        AppDatabase.getInstance(this).movieDAO().allFavourite().removeObservers(this);
        switch (id){
            case R.id.popularity:
//                MovieDBUtils.getInstance().start("mostPopular", movieRecyclerViewAdapter);
                MainViewModel.callAPI("mostPopular");
                break;
            case R.id.rating:
//                MovieDBUtils.getInstance().start("highestRated", movieRecyclerViewAdapter);
                MainViewModel.callAPI("highestRated");
                break;
            case R.id.favourite:
//                Observer<List<Movie>> obs = getObserver();
//                AppDatabase.getInstance(this).movieDAO().allFavourite().observe(this, obs);
//                AppDatabase.getInstance(this).movieDAO().allFavourite().removeObserver(obs);
//                movieRecyclerViewAdapter.setmData(new ArrayList<Movie>(MainViewModel.mFavMoviesLiveData.getValue()));

                Intent intent = new Intent(MainActivity.this, FavouriteActivity.class);
                startActivity(intent);
                break;
            default:
                break;
        }
        return true;
    }

    @Override
    protected void onPause() {
        super.onPause();

    }

    private int getNoOfColumns(int columnWidth){
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        float widthDp = dm.widthPixels/dm.density;
        int cols = (int) (widthDp/columnWidth + 0.5);
        return cols;
    }

    @Override
    protected void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
    }

    public Observer<List<Movie>> getObserver(){
        return new Observer<List<Movie>>() {
            @Override
            public void onChanged(List<Movie> movies) {
                MainViewModel.setFav(new ArrayList<Movie>(movies));
            }
        };
    }
}
