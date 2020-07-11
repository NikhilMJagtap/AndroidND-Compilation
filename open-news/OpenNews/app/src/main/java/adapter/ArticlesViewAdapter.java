package adapter;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityOptionsCompat;
import androidx.lifecycle.ViewModelProvider;
import androidx.lifecycle.ViewModelProviders;
import androidx.recyclerview.widget.RecyclerView;

import com.example.opennews.ArticleActivity;
import com.example.opennews.ArticleClickListener;
import com.example.opennews.MainActivity;
import com.example.opennews.R;
import com.example.opennews.ui.home.HomeViewModel;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;
import java.util.zip.Inflater;

import model.Article;
import utils.DateConverter;

public class ArticlesViewAdapter extends RecyclerView.Adapter<ArticlesViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Article> articles;
    private String category;
    ArticleClickListener articleClickListener;
    public ArticlesViewAdapter(Context context, String cat, ArticleClickListener acl){
        mContext = context;
        articles = new ArrayList<>();
        category = cat;
        articleClickListener = acl;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view;
        if(viewType == 1)
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_article_first, parent, false);
        else
            view = LayoutInflater.from(mContext).inflate(R.layout.rv_article, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public int getItemViewType(int position) {
        return position == 0 ? 1 : 2;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.title.setText(articles.get(position).getTitle());
        holder.author.setText(articles.get(position).getAuthor());
        holder.source.setText(articles.get(position).getSource().getName());
        holder.date.setText(DateConverter.utcToLocal(articles.get(position).getPublishedAt()));
        String url = articles.get(position).getUrlToImage();
        if(url == null)
            url = "https://image.freepik.com/free-vector/online-breaking-news-concept-vector-illustration-businessmen-businesswomen-with-megaphone-telescope-are-standing-near-big-letters-using-their-own-smart-phones-laptop-reading-news-flat_126608-12.jpg";
        Picasso.get().load(url).into(holder.image);
    }

    public void setArticles(ArrayList<Article> articles) {
        this.articles = articles;
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return articles.size();
//        return 10;
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView title, author, source, date;
        private ImageView image;
        ViewHolder(View view){
            super(view);
            title = view.findViewById(R.id.rv_title);
            author = view.findViewById(R.id.rv_author);
            source = view.findViewById(R.id.rv_source);
            date = view.findViewById(R.id.rv_date);
            image = view.findViewById(R.id.rv_image);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            articleClickListener.onArticleClick(getAdapterPosition(), category, image);
        }
    }
}
