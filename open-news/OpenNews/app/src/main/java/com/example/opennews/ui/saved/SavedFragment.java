package com.example.opennews.ui.saved;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.ProgressBar;

import androidx.annotation.NonNull;
import androidx.core.app.ActivityOptionsCompat;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.ItemTouchHelper;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opennews.ArticleActivity;
import com.example.opennews.ArticleClickListener;
import com.example.opennews.ArticleFragment;
import com.example.opennews.R;
import com.example.opennews.ui.home.HomeViewModel;
import com.example.opennews.ui.sports.SportsViewModel;

import java.util.ArrayList;
import java.util.List;

import adapter.ArticlesViewAdapter;
import adapter.SavedArticlesAdapter;
import db.AppDatabase;
import db.AppExecutors;
import db.ArticleDB;
import model.Article;

public class SavedFragment extends Fragment implements ArticleClickListener {

    private SavedViewModel homeViewModel;
    private RecyclerView rv;
    private SavedArticlesAdapter mAdapter;
    private ProgressBar pb;
    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        homeViewModel =
                ViewModelProviders.of(this).get(SavedViewModel.class);
        View root = inflater.inflate(R.layout.fragment_sports, container, false);
        rv = root.findViewById(R.id.rv_article);
        pb = root.findViewById(R.id.pb_main_2);
        pb.setVisibility(View.VISIBLE);
        rv.setLayoutManager(new LinearLayoutManager(getContext()));
        mAdapter = new SavedArticlesAdapter(getActivity().getApplicationContext(), "saved", this);
        rv.setAdapter(mAdapter);
        SavedViewModel.getArticles().observe(getActivity(),
                new Observer<List<ArticleDB>>() {
                    @Override
                    public void onChanged(List<ArticleDB> articleDBS) {
                        if(articleDBS!=null)
                            mAdapter.setArticles(new ArrayList<ArticleDB>(articleDBS));
                        pb.setVisibility(View.INVISIBLE);
                    }
                });


        ItemTouchHelper.SimpleCallback simpleCallback = new
                ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.LEFT | ItemTouchHelper.RIGHT) {
                    @Override
                    public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder target) {
                        return false;
                    }

                    @Override
                    public void onSwiped(@NonNull final RecyclerView.ViewHolder viewHolder, int direction) {
                        final ArticleDB adb = mAdapter.getArticles().get(viewHolder.getAdapterPosition());
                        AppExecutors.getInstance().diskIO().execute(new Runnable() {
                            @Override
                            public void run() {
                                AppDatabase.getInstance(getContext()).articleDAO().remove(adb);
                            }
                        });
                        mAdapter.remove(viewHolder.getAdapterPosition());
                    }
                };
        ItemTouchHelper itemTouchHelper = new ItemTouchHelper(simpleCallback);
        itemTouchHelper.attachToRecyclerView(rv);


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

    }
}

