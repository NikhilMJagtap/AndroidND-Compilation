package com.example.opennews.ui.sports;

import android.app.Application;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.example.opennews.R;

import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;

import model.Article;
import model.ResponseModelForNewsApi;
import network.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class SportsViewModel extends AndroidViewModel {

    private static MutableLiveData<List<Article>> articles;
    private static Application mApplication;
    private static SharedPreferences sharedPreferences;
    public SportsViewModel(Application application) {
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
            options.put("q", "sports");
            options.put("language", def_lang);
            call = NetworkUtil.loadSpecifics(options);
        } else {
            options.put("category", "sports");
            call = NetworkUtil.loadNews(options);
        }
        call.enqueue(new Callback<ResponseModelForNewsApi>() {
            @Override
            public void onResponse(Call<ResponseModelForNewsApi> call, Response<ResponseModelForNewsApi> response) {
                ResponseModelForNewsApi r = response.body();
                articles.setValue(r.getArticles());
            }

            @Override
            public void onFailure(Call<ResponseModelForNewsApi> call, Throwable t) {
                Log.e("TAG", call.toString());
            }
        });
    }
}
