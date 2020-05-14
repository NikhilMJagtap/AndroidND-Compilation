package com.example.popularmovies;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.lang.reflect.Array;
import java.util.ArrayList;

import utils.Comment;

public class CommentRecyclerViewAdapter extends RecyclerView.Adapter<CommentRecyclerViewAdapter.ViewHolder> {

    private ArrayList<Comment> mComments;
    private LayoutInflater mInflater;
    public CommentRecyclerViewAdapter(Context context){
        mInflater = LayoutInflater.from(context);
        mComments = new ArrayList<>();
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_comments, parent, false);
        return new CommentRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Comment comment = mComments.get(position);
        holder.mAuthor.setText(comment.getAuthor());
        holder.mText.setText(comment.getText());
    }

    @Override
    public int getItemCount() {
        return mComments.size();
    }

    public void setComments(ArrayList<Comment> comments){
        mComments = comments;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{

        TextView mAuthor, mText;

        ViewHolder(View view){
            super(view);
            mAuthor = view.findViewById(R.id.tv_comment_author);
            mText = view.findViewById(R.id.tv_comment_text);
        }
    }
}
