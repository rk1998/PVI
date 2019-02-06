package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Bitmap;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * ViewModel for the Meal Plan View screen
 */
public class MealPlanViewModel extends ViewModel {
    private MutableLiveData<List<Recipe>> mealplanRecipes;
    private RecipeRepository recipeRepo;
    private boolean isMealPlanCreated;
    private Context currContext;
    public MealPlanViewModel() {
        this.mealplanRecipes = new MutableLiveData<>();
        this.mealplanRecipes.setValue(new LinkedList<Recipe>());
        this.recipeRepo = RecipeRepository.getRecipeRepository();
        this.isMealPlanCreated = false;
    }

    /**
     * initializes live data object of the user's meals for their current plan.
     * @param recipeIDs list of the user's recipe IDs in their current plan
     * @param currentContext Activity context
     */
    public void init(List<String> recipeIDs, Context currentContext) {
        if (this.mealplanRecipes != null) {
            return;
        }
        this.currContext = currentContext;
        this.mealplanRecipes = (MutableLiveData) recipeRepo.getRecipes(recipeIDs, currentContext);
    }

    /**
     * Gets the LiveData object of the user's recipes in their current meal plan
     * @return mealplanRecipes
     */
    public LiveData<List<Recipe>> getMealplanRecipes() {
        return mealplanRecipes;
    }

    /**
     * Gets the image of a recipe given an image url
     * @param imgURL url of the recipe image
     * @return LiveData object containing Bitmap of the recipe.
     */
    public LiveData<Bitmap> getRecipeImage(String imgURL) {
        return recipeRepo.getRecipeImage(imgURL, this.currContext);
    }


}
