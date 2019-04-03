package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class GroceryListRepository {
    private static final GroceryListRepository GROCERY_LIST_REPOSITORY = new GroceryListRepository();
    private Model model = Model.get_instance();

    public static GroceryListRepository getGroceryListRepository() {
        return GROCERY_LIST_REPOSITORY;
    }

    /**
     * Generates a user's grocery list given their current meal plan
     * @param mealPlanRecipes recipes selected for their meal plan
     */
    public void generateGroceryList(List<Recipe> mealPlanRecipes) {
        Map<String, ArrayList<String>> ingredientToUnits = new HashMap<>();
        for(int i = 0; i < mealPlanRecipes.size(); i++) {
            Recipe currRecipe = mealPlanRecipes.get(i);
            ArrayList<String> mealIngredients = currRecipe.getIngredientNames();
            for(String ingredientName: mealIngredients) {
                ingredientName.toLowerCase();
                if(!ingredientToUnits.containsKey(ingredientName)) {
                    ArrayList<String> ingredientUnits = new ArrayList<>();
                    ingredientUnits.add(currRecipe.getIngredientToUnit().get(ingredientName));
                    for(int j = i; j < mealPlanRecipes.size(); j++) {
                        Recipe nextRecipe = mealPlanRecipes.get(j);
                        if(nextRecipe.getIngredientToUnit().containsKey(ingredientName)) {
                            ingredientUnits.add(nextRecipe.getIngredientToUnit().get(ingredientName));
                        }
                    }
                    ingredientToUnits.put(ingredientName, ingredientUnits);
                }
            }
        }
        model.setCurrentGroceryList(ingredientToUnits);
        //TODO: writeback this data to the database
    }

    /**
     * Retrieve user's current grocery list from database
     * @param uid user database id
     * @param currContext current activity context
     * @return mapping of ingredient to the units needed in different recipes
     */
    public Map<String, ArrayList<String>> getCurrentGroceryList(int uid, Context currContext) {
        return model.getCurrentGroceryList();
        //TODO: retrieve from database
    }


}
