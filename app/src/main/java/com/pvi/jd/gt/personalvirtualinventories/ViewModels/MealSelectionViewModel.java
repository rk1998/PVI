package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.MediatorLiveData;
import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Observer;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;
import android.support.annotation.Nullable;

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
    private UserRepository userRepo = UserRepository.getUserRepository();
    private MealPlanRepository mpRepo = MealPlanRepository.getMealPlanRepository();
    private RecipeRepository recipeRepo = RecipeRepository.getRecipeRepository();
    private LiveData<List<Recipe>> userRecipes;
    private MutableLiveData<List<String>> apiIDS;
    public void init(Context currContext) {
        this.currUser = userRepo.getCurrUser();
        //int uid = this.currUser.getValue().getId();
        int uid = 1;
        /*todo: with livedata you have to use observe method to get the actual data. not sure how this will work in view model*/
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

    public void createMealPlan(List<Recipe> recipes) {

    }
}
