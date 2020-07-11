package com.example.opennews.ui.home;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.core.widget.NestedScrollView;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.opennews.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Article;
import model.ResponseModelForNewsApi;
import network.NetworkUtil;
import network.NewsService;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class HomeViewModel extends AndroidViewModel {

    private static MutableLiveData<List<Article>> articles;
    private static Application mApplication;
    private static SharedPreferences sharedPreferences;
    public HomeViewModel(Application application) {
        super(application);
        this.mApplication = application;
        articles = new MutableLiveData<>();
        sharedPreferences = PreferenceManager.getDefaultSharedPreferences(mApplication);
        loadNews();
    }

    public static MutableLiveData<List<Article>> getArticles(){
        return articles;
    }

    public static void loadNews() {
        Call<ResponseModelForNewsApi> call;
        Map<String, String> options = new HashMap<>();
        String apiKey = mApplication.getResources().getString(R.string.API_KEY);
        options.put("apiKey", apiKey);
        if(sharedPreferences.getBoolean("def_lang_switch", false)){
            Log.e("openNews","here");
            String def_lang = sharedPreferences.getString("def_lang", "en");
            Log.e("openNews",def_lang);
            options.put("q", "headline");
            options.put("language", def_lang);
            call = NetworkUtil.loadSpecifics(options);
        } else {
            options.put("category", "general");
            call = NetworkUtil.loadNews(options);
        }
        call.enqueue(new Callback<ResponseModelForNewsApi>() {
            @Override
            public void onResponse(Call<ResponseModelForNewsApi> call, Response<ResponseModelForNewsApi> response) {
                ResponseModelForNewsApi r = response.body();
                if(r != null)
                    articles.setValue(r.getArticles());
                else
                    Log.e("openNews", response.toString());
            }

            @Override
            public void onFailure(Call<ResponseModelForNewsApi> call, Throwable t) {
                Log.e("TAG", call.toString());
            }
        });
    }

}