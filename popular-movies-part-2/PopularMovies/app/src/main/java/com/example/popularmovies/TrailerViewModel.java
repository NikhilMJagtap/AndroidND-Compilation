package com.example.popularmovies;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import utils.MovieDBUtils;
import utils.Trailer;


public class TrailerViewModel extends AndroidViewModel {
    private static MutableLiveData<ArrayList<Trailer>> trailers;
    public TrailerViewModel(Application application){
        super(application);
        trailers = new MutableLiveData<>();
    }

    public void callAPI(int movieId){
        MovieDBUtils.getInstance().getTrailers(movieId);
    }

    public static void setTrailers(ArrayList<Trailer> trailers) {
        Log.e("API", "model " + trailers.size());
        TrailerViewModel.trailers.setValue(trailers);
    }

    public static MutableLiveData<ArrayList<Trailer>> getTrailers() {
        return trailers;
    }
}
