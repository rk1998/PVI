package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Bitmap;

import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlan;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlanRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;

import java.util.LinkedList;
import java.util.List;

/**
 * ViewModel for the Meal Plan View screen
 */
public class MealPlanViewModel extends ViewModel {
    private MutableLiveData<MealPlan> mealplanRecipes;
    //private List<MutableLiveData<Recipe>> mealplanRecipes;
    private RecipeRepository recipeRepo;
    private MealPlanRepository mpRepo;

    public MealPlanViewModel() {
        this.recipeRepo = RecipeRepository.getRecipeRepository();
        this.mpRepo = MealPlanRepository.getMealPlanRepository();
    }

    /**
     * initializes live data object of the user's meals for their current plan.
     * @param currentContext Activity context
     */
    public void init(Context currentContext) {
        /*if (this.mealplanRecipes != null) {
            return;
        }
        this.mealplanRecipes = recipeRepo.getRecipesFromList(recipeIDs, currentContext);*/

        User user = new User();
        user.setId(1);
        MutableLiveData<User> userMutableLiveData = new MutableLiveData<>();
        userMutableLiveData.setValue(user);
        this.mealplanRecipes = mpRepo.getCurrMealPlan(userMutableLiveData, currentContext);

    }

    /**
     * Gets the LiveData object of the user's recipes in their current meal plan
     * @return mealplanRecipes
     */
    public MutableLiveData<MealPlan> getMealPlan() {
        return mealplanRecipes;
    }



}
