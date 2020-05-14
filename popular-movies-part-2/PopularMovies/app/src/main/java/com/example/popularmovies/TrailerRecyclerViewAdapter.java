package com.example.popularmovies;

import android.content.Context;
import android.content.Intent;
import android.media.Image;
import android.net.Uri;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import utils.Trailer;

public class TrailerRecyclerViewAdapter extends RecyclerView.Adapter<TrailerRecyclerViewAdapter.ViewHolder> {
    LayoutInflater mInflater;
    ArrayList<Trailer> trailers;
    Context mContext;
    final String YT_URI = "https://www.youtube.com";
    TrailerRecyclerViewAdapter(Context context){
        trailers = new ArrayList<>();
        mContext = context;
        mInflater = LayoutInflater.from(context);
    }

    @NonNull
    @Override
    public TrailerRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = mInflater.inflate(R.layout.rv_trailer, parent, false);
        return new TrailerRecyclerViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull TrailerRecyclerViewAdapter.ViewHolder holder, int position) {
        final Trailer trailer = trailers.get(position);
        holder.mTrailerText.setText(trailer.getTextTrailer());
        holder.mPlayBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Uri uri = Uri.parse(YT_URI).buildUpon()
                        .appendPath("watch").
                        appendQueryParameter("v", trailer.getTrailerKey())
                        .build();
                Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                if(intent.resolveActivity(mContext.getPackageManager()) != null){
                    mContext.startActivity(intent);
                } else
                    Toast.makeText(mContext,"Can't play trailer. Need YouTube or browser", Toast.LENGTH_LONG).show();
            }
        });
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    public void setTrailers(ArrayList<Trailer> trailers){
        this.trailers = trailers;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        ImageView mPlayBtn;
        TextView mTrailerText;
        public ViewHolder(View view){
            super(view);
            mTrailerText = view.findViewById(R.id.trailer_tv);
            mPlayBtn = view.findViewById(R.id.btn_play);
        }
    }
}
