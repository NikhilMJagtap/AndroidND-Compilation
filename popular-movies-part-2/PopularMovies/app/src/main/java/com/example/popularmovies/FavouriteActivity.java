package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;

import java.util.ArrayList;
import java.util.List;

import utils.AppDatabase;
import utils.Movie;

public class FavouriteActivity extends AppCompatActivity {

    private RecyclerView mFavRV;
    private FavRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_favourite);
        mFavRV = findViewById(R.id.rv_fav);
        adapter = new FavRecyclerViewAdapter(this);
        mFavRV.setLayoutManager(new LinearLayoutManager(this));
        mFavRV.setAdapter(adapter);
        AppDatabase.getInstance(this).movieDAO().allFavourite().observe(this,
                new Observer<List<Movie>>() {
                    @Override
                    public void onChanged(List<Movie> movies) {
                        adapter.setMovies(new ArrayList<Movie>(movies));
                    }
                });
    }
}
