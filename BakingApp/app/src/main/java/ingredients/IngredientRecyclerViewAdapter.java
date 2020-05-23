package ingredients;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;

import java.util.ArrayList;

import utils.Ingredient;

public class IngredientRecyclerViewAdapter extends RecyclerView.Adapter<IngredientRecyclerViewAdapter.ViewHolder> {
    private Context mContext;
    private ArrayList<Ingredient> ingredients;
    public IngredientRecyclerViewAdapter(Context context){
        mContext = context;
        ingredients = new ArrayList<>();
    }

    @NonNull
    @Override
    public IngredientRecyclerViewAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.rv_ingredient, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull final IngredientRecyclerViewAdapter.ViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.ingredientTextView.setText(ingredient.getIngredient());
        holder.quantityTextView.setText(Double.toString(ingredient.getQuantity()));
        holder.measureTextView.setText(ingredient.getMeasure());
        holder.tickImageView.setVisibility(ingredient.isChecked() ? View.VISIBLE : View.INVISIBLE);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public ArrayList<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView ingredientTextView, quantityTextView, measureTextView;
        private ImageView tickImageView;
        ViewHolder(View view){
            super(view);
            ingredientTextView = view.findViewById(R.id.tv_ingredient_name);
            quantityTextView = view.findViewById(R.id.tv_ingredient_quantity);
            measureTextView = view.findViewById(R.id.tv_ingredient_measure);
            tickImageView = view.findViewById(R.id.iv_tick);
            view.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            if(tickImageView.getVisibility() == View.VISIBLE){
                tickImageView.setVisibility(View.INVISIBLE);
                ingredients.get(getAdapterPosition()).setChecked(false);
            }
            else {
                tickImageView.setVisibility(View.VISIBLE);
                ingredients.get(getAdapterPosition()).setChecked(true);
            }
        }
    }
}
