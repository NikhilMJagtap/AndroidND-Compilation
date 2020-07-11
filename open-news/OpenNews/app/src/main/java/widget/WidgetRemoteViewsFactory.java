package widget;

import android.appwidget.AppWidgetManager;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import com.example.opennews.R;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import model.Article;
import model.ResponseModelForNewsApi;
import network.NetworkUtil;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class WidgetRemoteViewsFactory  implements RemoteViewsService.RemoteViewsFactory {

    private Context context;
    private int appWidgetId;
    private List<Article> articleList;
    private Map<String, String> options;

    public WidgetRemoteViewsFactory(Context context, Intent intent){
        this.context = context;
        articleList = new ArrayList<>();
        appWidgetId = intent.getIntExtra(AppWidgetManager.EXTRA_APPWIDGET_ID,
                AppWidgetManager.INVALID_APPWIDGET_ID);
        options = new HashMap<>();
        String apiKey = context.getResources().getString(R.string.API_KEY);
        options.put("apiKey", apiKey);
        options.put("category", "general");
    }

    private void updateWidgetListView(){
        Call<ResponseModelForNewsApi> call = NetworkUtil.loadNews(options);
        call.enqueue(new Callback<ResponseModelForNewsApi>() {
            @Override
            public void onResponse(Call<ResponseModelForNewsApi> call, Response<ResponseModelForNewsApi> response) {
                ResponseModelForNewsApi r = response.body();
                if(r != null) {
                    articleList = r.getArticles();
                    Log.e("openNews", "Got " + articleList.size());
                } else
                    Log.e("openNews", response.toString());
            }

            @Override
            public void onFailure(Call<ResponseModelForNewsApi> call, Throwable t) {
                Log.e("openNews", "Error in widget");
            }
        });
        try{
            Thread.sleep(2000);
        }catch (InterruptedException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onCreate() {
        updateWidgetListView();
    }

    @Override
    public void onDataSetChanged() {
        updateWidgetListView();
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        Log.e("openNews", articleList.size() + " art");
        return articleList.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews remoteView = new RemoteViews(context.getPackageName(),
                R.layout.widget_item);
        Log.d("openNews", articleList.get(position).getTitle());
        remoteView.setTextViewText(R.id.tv_widget_name, articleList.get(position).getTitle());
        Bundle bundle = new Bundle();
        bundle.putInt("index", position);
        bundle.putString("category", "headlines");
        Intent intent = new Intent();
        intent.putExtra("index", position);
        intent.putExtra("category", "headlines");
        intent.putExtras(bundle);
        remoteView.setOnClickFillInIntent(R.id.widgetItem, intent);
        return remoteView;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return 1;
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public boolean hasStableIds() {
        return false;
    }
}
