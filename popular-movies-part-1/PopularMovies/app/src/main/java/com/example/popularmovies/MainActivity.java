package com.example.popularmovies;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import utils.MovieDBUtils;

public class MainActivity extends AppCompatActivity {

    private RecyclerView mRecyclerView;
    private MovieRecyclerViewAdapter movieRecyclerViewAdapter;
    private int noOfColumns;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.rv_movies);
        noOfColumns = getNoOfColumns(150);
        mRecyclerView.setLayoutManager(new GridLayoutManager(this, noOfColumns));
        // mRecyclerView.hasFixedSize();
        movieRecyclerViewAdapter = MovieRecyclerViewAdapter.getInstance(this);
        mRecyclerView.setAdapter(movieRecyclerViewAdapter);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.sort, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.popularity:
                MovieDBUtils.getInstance().start("mostPopular", movieRecyclerViewAdapter);
                break;
            case R.id.rating:
                MovieDBUtils.getInstance().start("highestRated", movieRecyclerViewAdapter);
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
}
