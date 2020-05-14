package com.example.popularmovies;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModel;

import java.util.ArrayList;
import java.util.List;

import utils.AppDatabase;
import utils.AppExecutors;
import utils.Movie;
import utils.MovieDBUtils;

public class MainViewModel extends AndroidViewModel {

    private static MutableLiveData<ArrayList<Movie>> mMoviesLiveData;
    public static MutableLiveData<ArrayList<Movie>> mFavMoviesLiveData;

    private static Application mApplication;

    public MainViewModel(Application application){
        super(application);
        mApplication = application;
        mMoviesLiveData = new MutableLiveData<>();
        mFavMoviesLiveData = new MutableLiveData<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                mFavMoviesLiveData.postValue(new ArrayList<>(AppDatabase.getInstance(mApplication).movieDAO().favourites()));
            }
        });
        callAPI("mostPopular");
    }

    public static void callAPI(String query){
        if(query.equals("favourite")) return;
        MovieDBUtils.getInstance().start(query, MovieRecyclerViewAdapter.getInstance(mApplication));
    }

    public MutableLiveData<ArrayList<Movie>> getMovies(){
        return mMoviesLiveData;
    }

    public static void setMovies(ArrayList<Movie> movies){
        mMoviesLiveData.setValue(movies);
    }

    public static void setFav(ArrayList<Movie> movies){
        mFavMoviesLiveData.setValue(movies);
    }


}
