package com.example.opennews;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.Context;
import android.content.Intent;
import android.content.res.Configuration;
import android.net.Uri;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;

import com.example.opennews.ui.business.BusinessViewModel;
import com.example.opennews.ui.entertainment.EntertainmentViewModel;
import com.example.opennews.ui.home.HomeViewModel;
import com.example.opennews.ui.saved.SavedViewModel;
import com.example.opennews.ui.science.ScienceViewModel;
import com.example.opennews.ui.sports.SportsViewModel;

import db.AppDatabase;
import db.AppExecutors;
import db.ArticleDB;
import db.Converter;
import model.Article;

public class ArticleActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_article);
        if(! getResources().getBoolean(R.bool.isTablet) ||
                getResources().getConfiguration().orientation != Configuration.ORIENTATION_LANDSCAPE
        )
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_menu, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch (id){
            case R.id.action_save:
                final Article articleSave = getArticle();
                final Context context = this;
                AppExecutors.getInstance().diskIO().execute(new Runnable() {
                    @Override
                    public void run() {
                        AppDatabase.getInstance(context).articleDAO().save(Converter.articleToArticleDB(articleSave));
                    }
                });
                break;
            case R.id.action_share:
                Article article = getArticle();
                String url = article.getUrl();
                Intent shareIntent = new Intent(Intent.ACTION_SEND);
                shareIntent.setType("text/plain");
                shareIntent.putExtra(Intent.EXTRA_SUBJECT, "Checkout this new news article!");
                shareIntent.putExtra(Intent.EXTRA_TEXT, url);
                startActivity(shareIntent);
                break;
            default:
                break;
        }
        return true;
    }

    public Article getArticle(){
        String category;
        int index;
        category = getIntent().getStringExtra("category");
        if (category == null) category = "headlines";
        index = getIntent().getIntExtra("index", 0);
        return getArticleFrom(index, category);
    }

    public Article getArticleFrom(int index, String category){
        final Article article;
        switch(category){
            case "headlines":
                article = HomeViewModel.getArticles().getValue().get(index);
                break;
            case "business":
                article = BusinessViewModel.getArticles().getValue().get(index);
                break;
            case "sports":
                article = SportsViewModel.getArticles().getValue().get(index);
                break;
            case "science":
                article = ScienceViewModel.getArticles().getValue().get(index);
                break;
            case "entertainment":
                article = EntertainmentViewModel.getArticles().getValue().get(index);
                break;
            case "saved":
                ArticleDB articleDB = SavedViewModel.getArticles().getValue().get(index);
                article = Converter.articleDBToArticle(articleDB);
                break;
            default:
                article = null;
        }
        return article;
    }
}
