package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.LiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.List;

/**
 * View Model for selecting recipes at the end of the questionnaire
 *
 */
public class RecipeSelectionViewModel extends ViewModel {
    private UserRepository userRepo;
    private RecipeRepository recipeRepository;
    private User currentUser;
    private LiveData<List<String>> searchedRecipeIDS;
    private LiveData<List<Recipe>> searchedRecipes;

    public RecipeSelectionViewModel() {
        userRepo = UserRepository.getUserRepository();
        recipeRepository = RecipeRepository.getRecipeRepository();
    }

    public void init(Context currentContext) {
        this.currentUser = userRepo.getTempUser();
        searchedRecipeIDS = recipeRepository.searchRecipes(this.currentUser, currentContext);
        searchedRecipes = Transformations.switchMap(searchedRecipeIDS, searchedRecipeIDS -> {
            LiveData<List<Recipe>> recipes = recipeRepository.getRecipesFromList(searchedRecipeIDS, currentContext);
            return recipes;
        });
    }

    public LiveData<List<Recipe>> getSearchedRecipes() {
        return searchedRecipes;
    }

    /**
     * this method is used to store the user's selected meals and all of the info
     * from the questionnaire
     * @param selectedRecipes recipe IDs the user selected for their meal bank
     * @param currentContext current activity context
     */
    public void writeUserData(List<Recipe> selectedRecipes, Context currentContext) {
        this.currentUser.setRecipes(selectedRecipes);
        userRepo.createUserInDB(this.currentUser,currentContext);
    }
}
