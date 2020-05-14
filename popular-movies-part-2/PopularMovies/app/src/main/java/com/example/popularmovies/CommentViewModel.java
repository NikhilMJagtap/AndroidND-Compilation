package com.example.popularmovies;

import android.app.Application;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.ArrayList;

import utils.Comment;
import utils.MovieDBUtils;

public class CommentViewModel  extends AndroidViewModel {
    private static MutableLiveData<ArrayList<Comment>> comments;
    public CommentViewModel(Application application){
        super(application);
        comments = new MutableLiveData<>();
    }

    public static MutableLiveData<ArrayList<Comment>> getComments() {
        return comments;
    }

    public static void setComments(ArrayList<Comment> comments) {
        CommentViewModel.comments.setValue(comments);
    }

    public void callAPI(int id){
        MovieDBUtils.getInstance().getComments(755);
    }
}
