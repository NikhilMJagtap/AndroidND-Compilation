package com.example.opennews.ui.saved;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import java.util.ArrayList;
import java.util.List;

import db.AppDatabase;
import db.AppExecutors;
import db.ArticleDB;
import model.Article;

public class SavedViewModel extends AndroidViewModel {

    private static MutableLiveData<List<ArticleDB>> articles;
    private static Application mApplication;
    public SavedViewModel(Application application) {
        super(application);
        this.mApplication = application;
        articles = new MutableLiveData<>();
        AppExecutors.getInstance().diskIO().execute(new Runnable() {
            @Override
            public void run() {
                articles.postValue(new ArrayList<ArticleDB>(AppDatabase.getInstance(mApplication).articleDAO().get()));
            }
        });
    }

    public static MutableLiveData<List<ArticleDB>> getArticles(){
        return articles;
    }

    public static void loadNews() {

    }
}
