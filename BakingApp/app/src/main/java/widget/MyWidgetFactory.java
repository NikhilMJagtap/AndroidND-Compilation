package widget;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.widget.RemoteViews;
import android.widget.RemoteViewsService;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.Observer;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import steps.StepsActivity;
import utils.Recipe;

public class MyWidgetFactory implements RemoteViewsService.RemoteViewsFactory {

    private Context mContext;

    public MyWidgetFactory(Context context){
        mContext = context;
    }

    private ArrayList<Recipe> recipes;

    @Override
    public void onCreate() {
        try {
            recipes = new ArrayList<>(RecipeViewModel.getRecipes().getValue());
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDataSetChanged() {
        try {
            recipes = new ArrayList<>(RecipeViewModel.getRecipes().getValue());
        } catch(NullPointerException e){
            e.printStackTrace();
        }
    }

    @Override
    public void onDestroy() {

    }

    @Override
    public int getCount() {
        return recipes == null ? 0 : recipes.size();
    }

    @Override
    public RemoteViews getViewAt(int position) {
        RemoteViews rvs = new RemoteViews(mContext.getPackageName(), R.layout.recipe_widget_item);
        rvs.setTextViewText(R.id.tv_widget_name, recipes.get(position).getName());
        Intent fillInIntent = new Intent();
        fillInIntent.putExtra(RecipeWidget.EXTRA_ITEM, position);
        rvs.setOnClickFillInIntent(R.id.widgetItem, fillInIntent);
        Log.e("API2", "Dong this");
        return rvs;
    }

    @Override
    public RemoteViews getLoadingView() {
        return null;
    }

    @Override
    public int getViewTypeCount() {
        return recipes == null ? 0 : recipes.size();
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
