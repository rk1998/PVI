package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.graphics.Bitmap;
import android.support.annotation.Nullable;
import android.util.Log;

import com.pvi.jd.gt.personalvirtualinventories.Model.Meal;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlan;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlanRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.Arrays;
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
    private UserRepository userRepo;

    public MealPlanViewModel() {
        this.recipeRepo = RecipeRepository.getRecipeRepository();
        this.mpRepo = MealPlanRepository.getMealPlanRepository();
        this.userRepo = UserRepository.getUserRepository();
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

//        User user = new User();
//        user.setId(1);
        MutableLiveData<User> userMutableLiveData = userRepo.getCurrUser();
        this.mealplanRecipes = mpRepo.getCurrMealPlan(userMutableLiveData.getValue(), currentContext);


        /*MutableLiveData<MealPlan> userRecipes = Transformations.map(mealplanRecipes, mealplan -> {
            for (int i = 0; i < mealplan.getMealPlan().size(); i++) {
                Meal m = mealplan.getMealPlan().get(i);
                MutableLiveData<Recipe> MLDR = recipeRepo.getRecipe(m.getApiID(), currentContext);
                Transformations.map(MLDR, recipe-> {
                    m.setRecipe(recipe);
                    return recipe;
                });
            }
            return mealplan;
        });*/


    }

    /**
     * Gets the LiveData object of the user's recipes in their current meal plan
     * @return mealplanRecipes
     */
    public MutableLiveData<MealPlan> getMealPlan() {
        return mealplanRecipes;
    }

    public MutableLiveData<Recipe> getRecipe(String apiID, Context currContext) {
        return recipeRepo.getRecipe(apiID, currContext);
    }

    /**
     * Changes meal completion status in the database
     * @param recipe recipe that is changing completion status
     * @param completed the new completion status
     * @param currContext current activity context
     */
    public void changeMealCompletionStatus(Recipe recipe, boolean completed, Context currContext) {
        mpRepo.setCompleteStatus(recipe, completed, currContext);
    }

    /**
     * removes a meal from the user's meal plan and updates it in the database
     * @param mealToRemove meal that is being removed.
     */
    public void removeMeal(Meal mealToRemove, Context currentContext) {
        List<Recipe> recipesToRemove = new LinkedList<>();
        recipesToRemove.add(mealToRemove.getRecipe());
        mpRepo.editMealPlan(userRepo.getCurrUser().getValue(), new LinkedList<>(), recipesToRemove,
                currentContext);
    }

}
