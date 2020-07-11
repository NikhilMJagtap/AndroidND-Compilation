package com.example.opennews.ui.business;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.Nullable;
import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opennews.ArticleActivity;
import com.example.opennews.ArticleClickListener;
import com.example.opennews.ArticleFragment;
import com.example.opennews.R;
import com.example.opennews.ui.home.HomeViewModel;

import java.util.ArrayList;
import java.util.List;

import adapter.ArticlesViewAdapter;
import model.Article;

public class BusinessFragment extends Fragment implements ArticleClickListener {

    private BusinessViewModel homeViewModel;
    private RecyclerView rv;
    private ArticlesViewAdapter mAdapter;
    private ProgressBar pb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(BusinessViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sports, container, false);
        rv = root.findViewById(R.id.rv_article);
        pb = root.findViewById(R.id.pb_main_2);
        pb.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new ArticlesViewAdapter(getActivity().getApplicationContext(),"business", this);
        rv.setAdapter(mAdapter);
        homeViewModel.getArticles().observe(getViewLifecycleOwner(), new Observer<List<Article>>() {
            @Override
            public void onChanged(List<Article> articles) {
                mAdapter.setArticles(new ArrayList<Article>(articles));
                pb.setVisibility(View.INVISIBLE);
            }
        });
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.registerOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {
                if(key.equals("def_lang") || key.equals("def_lang_switch")){
                    homeViewModel.loadNews();
                }
            }
        });
        return root;
    }

    @Override
    public void onArticleClick(int pos, String category, ImageView sharedImageView) {
        if(getResources().getBoolean(R.bool.isTablet) &&
                getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
        ){
            ArticleFragment af = new ArticleFragment();
            Bundle bundle = new Bundle();
            bundle.putInt("index", pos);
            bundle.putString("category", category);
            af.setArguments(bundle);
            getActivity().getSupportFragmentManager().beginTransaction().replace(R.id.article_container, af).commit();
            return;
        }
        ActivityOptionsCompat activityOptionsCompat = ActivityOptionsCompat
                .makeSceneTransitionAnimation(getActivity(), sharedImageView, "imageShare");
        Intent intent = new Intent(getActivity(), ArticleActivity.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        intent.putExtra("index", pos);
        intent.putExtra("category", category);
        startActivity(intent, activityOptionsCompat.toBundle());
    }

    @Override
    public void onStop() {
        super.onStop();
        SharedPreferences sharedPreferences = PreferenceManager.getDefaultSharedPreferences(getContext());
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(new SharedPreferences.OnSharedPreferenceChangeListener() {
            @Override
            public void onSharedPreferenceChanged(SharedPreferences sharedPreferences, String key) {

            }
        });
    }
}
