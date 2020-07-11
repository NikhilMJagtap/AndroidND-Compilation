package com.example.opennews;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.drawable.GradientDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.text.Html;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.widget.Toolbar;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.fragment.app.Fragment;

import com.example.opennews.ui.home.HomeViewModel;
import com.example.opennews.ui.sports.SportsViewModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.squareup.picasso.Picasso;

import model.Article;

public class ArticleFragment extends Fragment {

    Article article;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_article, container, false);
        return view;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        ImageView iv = view.findViewById(R.id.expandedImage);
        TextView title, author, content;
        title = view.findViewById(R.id.article_title);
        author = view.findViewById(R.id.article_byline);
        content = view.findViewById(R.id.article_content);
        FloatingActionButton fab = view.findViewById(R.id.fab);
        String category = "headlines";
        int index = 0;

        if(getResources().getBoolean(R.bool.isTablet) &&
            getResources().getConfiguration().orientation == Configuration.ORIENTATION_LANDSCAPE
        ){
            Bundle bundle = getArguments();
            if(bundle != null){
                index = bundle.getInt("index");
                category = bundle.getString("category");
                article = ((ArticleActivity)getActivity()).getArticleFrom(index, category);
            }
        } else
            article = ((ArticleActivity)getActivity()).getArticle();
        title.setText(article.getTitle());
        author.setText(article.getAuthor());
        if(article.getContent() != null && article.getContent().length() >= 20)
            content.setText(article.getContent().substring(0, article.getContent().length() - 15) + getResources().getString(R.string.dots_string));
        else
            content.setText(article.getAuthor());
        String imageUrl = article.getUrlToImage();
        imageUrl = imageUrl == null ? "https://image.freepik.com/free-vector/online-breaking-news-concept-vector-illustration-businessmen-businesswomen-with-megaphone-telescope-are-standing-near-big-letters-using-their-own-smart-phones-laptop-reading-news-flat_126608-12.jpg"
                : imageUrl;
        Picasso.get().load(imageUrl).into(iv);
        Animation bottomUp = AnimationUtils.loadAnimation(getContext(), R.anim.bottom_up);
        ViewGroup panel = (ViewGroup) view.findViewById(R.id.nested_scroll);
        panel.startAnimation(bottomUp);
        panel.setVisibility(View.VISIBLE);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse(article.getUrl()));
                startActivity(intent);
            }
        });
    }
}
