package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.ViewModel;

import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

public class RecipeSelectionViewModel extends ViewModel {
    private UserRepository userRepo;
    private RecipeRepository recipeRepository;

    public RecipeSelectionViewModel() {
        userRepo = UserRepository.getUserRepository();
        recipeRepository = RecipeRepository.getRecipeRepository();
    }
}
