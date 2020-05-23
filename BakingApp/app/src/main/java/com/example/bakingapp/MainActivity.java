package com.example.bakingapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Context;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.widget.ImageView;

import java.util.ArrayList;
import java.util.List;

import jp.wasabeef.blurry.Blurry;
import utils.Recipe;

public class MainActivity extends AppCompatActivity {

    private RecipeRecycleViewAdapter mAdapter;
    private RecyclerView mRecycleView;
    private RecipeViewModel mViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
            mRecycleView = findViewById(R.id.rv_recipes);
            mAdapter = new RecipeRecycleViewAdapter(this);
            mRecycleView.setLayoutManager(new GridLayoutManager(this, getNoOfColumns(230)));
            mRecycleView.setAdapter(mAdapter);
            mViewModel = ViewModelProviders.of(this).get(RecipeViewModel.class);
        mViewModel.getRecipes().observe(this, new Observer<List<Recipe>>() {
                @Override
                public void onChanged(List<Recipe> recipes) {
                    ImageView iv = findViewById(R.id.iv_recipe);
                    mAdapter.setRecipes(new ArrayList<>(recipes));
                }
            });
    }

    private int getNoOfColumns(int columnWidth){
        DisplayMetrics dm = this.getResources().getDisplayMetrics();
        float widthDp = dm.widthPixels/dm.density;
        int cols = (int) (widthDp/columnWidth + 0.5);
        return cols;
    }

}
