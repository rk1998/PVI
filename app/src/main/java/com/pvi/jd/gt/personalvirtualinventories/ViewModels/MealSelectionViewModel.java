package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlanRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.List;

/**
 * Created by paige on 2/10/2019.
 */

public class MealSelectionViewModel extends ViewModel {
    private MutableLiveData<User> currUser;
    private UserRepository userRepo = UserRepository.getUserRepository();
    private MealPlanRepository mpRepo = MealPlanRepository.getMealPlanRepository();
    private RecipeRepository recipeRepo = RecipeRepository.getRecipeRepository();
    private LiveData<List<Recipe>> userRecipes;

    public void init(Context currContext) {
        this.currUser = userRepo.getCurrUser();
        //int uid = this.currUser.getValue().getId();
        int uid = 1;
        List<String> apiIDS = recipeRepo.getUserRecipeIDs(uid, currContext).getValue();
        //todo: getrecipes is throwing nullpointerexception bc apiids is null
        this.userRecipes = recipeRepo.getRecipes(apiIDS, currContext);
    }

    public LiveData<List<Recipe>> getUserRecipes() {
        return userRecipes;
    }

    public void createMealPlan(List<Recipe> recipes) {

    }
}
