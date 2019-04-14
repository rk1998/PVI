package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.ViewModel;

import com.pvi.jd.gt.personalvirtualinventories.Model.GroceryListRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.InventoryRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.MealPlanRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.RecipeRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

public class LogoutViewModel extends ViewModel {

    private UserRepository userRepo;
    private MealPlanRepository mealPlanRepo;
    private RecipeRepository recipeRepo;
    private InventoryRepository inventoryRepo;
    private GroceryListRepository groceryListRepo;

    public LogoutViewModel() {
        this.userRepo = UserRepository.getUserRepository();
        this.mealPlanRepo = MealPlanRepository.getMealPlanRepository();
        this.recipeRepo = RecipeRepository.getRecipeRepository();
        this.inventoryRepo = InventoryRepository.getInventoryRepository();
        this.groceryListRepo = GroceryListRepository.getGroceryListRepository();
    }

    public void clearCachedData() {
        this.userRepo.clearCurrUser();
        this.mealPlanRepo.clearCurrMealPlan();
        this.recipeRepo.clearStoredRecipes();
        this.inventoryRepo.clearInventory();
        this.groceryListRepo.clearGroceryList();
    }

}
