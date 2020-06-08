package com.example.bakingapp;

import android.app.Application;
import android.appwidget.AppWidgetManager;
import android.content.ComponentName;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import org.json.JSONObject;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import utils.Api;
import utils.Recipe;
import widget.RecipeWidget;

public class RecipeViewModel extends AndroidViewModel {
    private volatile static MutableLiveData<List<Recipe>> recipes;
    private static Application mApplication;
    public RecipeViewModel(Application application){
        super(application);
        mApplication = application;
    }

    public static LiveData<List<Recipe>> getRecipes(){
        if(recipes == null){
            recipes = new MutableLiveData<>();
            loadRecipes();
        }
        return recipes;
    }

    private static void loadRecipes(){
        final Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(Api.BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        Api api = retrofit.create(Api.class);
        Call<List<Recipe>>call = api.getRecipes();
        call.enqueue(new Callback<List<Recipe>>() {
            @Override
            public void onResponse(Call<List<Recipe>> call, Response<List<Recipe>> response) {
                recipes.setValue(response.body());
                AppWidgetManager appWidgetManager = AppWidgetManager.getInstance(mApplication);
                appWidgetManager.notifyAppWidgetViewDataChanged(
                        appWidgetManager.getAppWidgetIds(new ComponentName(mApplication, RecipeWidget.class)),
                        R.id.widgetListView);
            }

            @Override
            public void onFailure(Call<List<Recipe>> call, Throwable t) {
            }
        });
    }
}
