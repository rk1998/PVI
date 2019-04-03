package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.GroceryListRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlanRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.LinkedList;
import java.util.List;

/**
 * Created by paige on 2/10/2019.
 */

public class MealSelectionViewModel extends ViewModel {
    private MutableLiveData<User> currUser;
    //private User currUser;
    private UserRepository userRepo = UserRepository.getUserRepository();
    private MealPlanRepository mpRepo = MealPlanRepository.getMealPlanRepository();
    private RecipeRepository recipeRepo = RecipeRepository.getRecipeRepository();
    private GroceryListRepository groceryRepo = GroceryListRepository.getGroceryListRepository();
    private LiveData<List<Recipe>> userRecipes;
    private MutableLiveData<List<String>> apiIDS;

    public void init(Context currContext) {
        this.currUser = userRepo.getCurrUser();
        int uid = this.currUser.getValue().getId();
        apiIDS = recipeRepo.getUserRecipeIDs(uid, currContext);
        userRecipes = Transformations.switchMap(apiIDS, apiIDS-> {
            LiveData<List<Recipe>> data = recipeRepo.getRecipesFromList(apiIDS, currContext);
            return data;
        });

    }

    public LiveData<List<Recipe>>  getUserRecipes() {
        return this.userRecipes;
    }

    public MutableLiveData<List<String>> getApiIDS() {
        return apiIDS;
    }

    /**
     * Writes new meal plan back to the meal plan repository
     * @param recipeApiIDs new meal plan selected from user's recipe bank
     * @param currContext current activity context
     */
    public void createMealPlan(List<Recipe> recipeApiIDs, Context currContext) {
        groceryRepo.generateGroceryList(recipeApiIDs);
        mpRepo.createCurrMealPlan(recipeApiIDs, currContext);
    }

    /**
     * Edits current meal plan
     * @param newRecipes new recipes to add to current meal plan
     * @param currContext current activity context
     */
    public void editCurrentMealPlan(List<Recipe> newRecipes, Context currContext) {
        mpRepo.editMealPlan(this.currUser.getValue(), newRecipes, new LinkedList<>(), currContext);
    }
}
