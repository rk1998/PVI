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
    //private List<MutableLiveData<Recipe>> mealplanRecipes;
    private RecipeRepository recipeRepo;
    private Context currContext;
    public MealPlanViewModel() {
        this.recipeRepo = RecipeRepository.getRecipeRepository();
    }

    /**
     * initializes live data object of the user's meals for their current plan.
     * @param recipeIDs list of the user's recipe IDs in their current plan
     * @param currentContext Activity context
     */
    public void init(List<String> recipeIDs, Context currentContext) {
        if(this.mealplanRecipes != null) {
            return;
        }
        this.currContext = currentContext;
        this.mealplanRecipes = recipeRepo.getRecipesFromList(recipeIDs, this.currContext);
    }

    /**
     * Gets the LiveData object of the user's recipes in their current meal plan
     * @return mealplanRecipes
     */
    public MutableLiveData<List<Recipe>> getMealPlanRecipes() {
        return mealplanRecipes;
    }



}
