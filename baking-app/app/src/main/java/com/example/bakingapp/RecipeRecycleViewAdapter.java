package com.example.bakingapp;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

import jp.wasabeef.blurry.Blurry;
import steps.StepsActivity;
import utils.Recipe;

public class RecipeRecycleViewAdapter extends RecyclerView.Adapter<RecipeRecycleViewAdapter.ViewHolder> {

    private Context mContext;
    private ArrayList<Recipe> recipes;
    public RecipeRecycleViewAdapter(Context context){
        mContext = context;
        recipes = new ArrayList<>();
    }

    @NonNull
    @Override
    public RecipeRecycleViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_recipes, parent, false);
        return new RecipeRecycleViewAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeRecycleViewAdapter.ViewHolder holder, int position) {
        holder.mName.setText(recipes.get(position).getName());

        switch (recipes.get(position).getName()){
            case "Nutella Pie":
                holder.imageView.setImageResource(R.drawable.nutella_pie);
                break;
            case "Brownies":
                holder.imageView.setImageResource(R.drawable.brownie);
                break;
            case "Yellow Cake":
                holder.imageView.setImageResource(R.drawable.yellow_cake);
                break;
            case "Cheesecake":
                holder.imageView.setImageResource(R.drawable.cheese_cake);
                break;
            default:
                break;
        }
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public ArrayList<Recipe> getRecipes() {
        return recipes;
    }

    public void setRecipes(ArrayList<Recipe> recipes) {
        this.recipes = recipes;
        notifyDataSetChanged();
    }

    class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        TextView mName;
        ImageView imageView;
        final private String indexKey = "INDEX";
        ViewHolder(View view){
            super(view);
            mName = view.findViewById(R.id.tv_recipe_name);
            imageView = view.findViewById(R.id.iv_recipe);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            int index = getAdapterPosition();
            Intent intent = new Intent(mContext, StepsActivity.class);
            intent.putExtra(indexKey, index);
            mContext.startActivity(intent);
        }
    }
}
