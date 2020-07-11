package com.example.opennews;

import android.widget.ImageView;

public interface ArticleClickListener{
    void onArticleClick(int pos, String category, ImageView sharedImageView);
}
