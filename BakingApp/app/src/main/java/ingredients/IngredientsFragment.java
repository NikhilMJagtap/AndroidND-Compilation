package ingredients;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.bakingapp.R;
import com.example.bakingapp.RecipeRecycleViewAdapter;
import com.example.bakingapp.RecipeViewModel;

import java.util.ArrayList;
import java.util.List;

import utils.Ingredient;
import utils.Recipe;

public class IngredientsFragment extends Fragment {

    private RecyclerView mRecyclerView;
    private IngredientRecyclerViewAdapter mAdapter;
    private final String indexKey = "INDEX";
    private IngredientsViewModel ingredientsViewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_ingredients, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        mRecyclerView = getView().findViewById(R.id.rv_ingredients);
        mAdapter = new IngredientRecyclerViewAdapter(getContext());
        mRecyclerView.setLayoutManager(new LinearLayoutManager(getContext(), LinearLayoutManager.HORIZONTAL, false));
        mRecyclerView.setAdapter(mAdapter);
        Intent intent = getActivity().getIntent();
        int position = 0;
        if(intent.hasExtra(indexKey)){
            position = intent.getIntExtra(indexKey, 0);
        }
        Recipe recipe  = RecipeViewModel.getRecipes().getValue().get(position);
        ingredientsViewModel = new IngredientsViewModel(getActivity().getApplication());
        mAdapter.setIngredients(new ArrayList<>(recipe.getIngredients()));
        ingredientsViewModel.getIngredients().observe(getViewLifecycleOwner(), new Observer<List<Ingredient>>() {
            @Override
            public void onChanged(List<Ingredient> ingredients) {
                mAdapter.setIngredients(new ArrayList<Ingredient>(ingredients));
            }
        });
        ingredientsViewModel.setIngredients(new ArrayList<>(recipe.getIngredients()));
    }

    @Override
    public void onActivityCreated(@Nullable Bundle savedInstanceState) {
        super.onActivityCreated(savedInstanceState);
        if(savedInstanceState != null) return;
    }
}
