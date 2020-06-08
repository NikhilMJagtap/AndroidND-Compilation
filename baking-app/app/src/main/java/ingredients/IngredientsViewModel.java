package ingredients;

import android.app.Application;
import android.util.Log;

import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

import utils.Ingredient;

public class IngredientsViewModel extends AndroidViewModel {

    private MutableLiveData<List<Ingredient>> ingredients;

    IngredientsViewModel(Application application){
        super(application);
        ingredients = new MutableLiveData<>();
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients.setValue(ingredients);
    }

    public MutableLiveData<List<Ingredient>> getIngredients() {
        return ingredients;
    }
}
