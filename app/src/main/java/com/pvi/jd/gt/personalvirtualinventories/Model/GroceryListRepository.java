package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.content.Context;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Set<String> ingredientSet = new HashSet<>();
        ArrayList<String> groceryListLines = new ArrayList<>();
        for(int i = 0; i < mealPlanRecipes.size(); i++) {
            Recipe currRecipe = mealPlanRecipes.get(i);
            ArrayList<String> mealIngredients = currRecipe.getIngredientNames();
            for(String ingredientName: mealIngredients) {
                String line = ingredientName;
                ingredientName.toLowerCase();
                if(!ingredientSet.contains(ingredientName)) {
                    if(currRecipe.getIngredientToUnit().containsKey(ingredientName)) {
                        line += "\n" + "\u2022 " + currRecipe.getIngredientToUnit().get(ingredientName);
                    }
                    for(int j = i; j < mealPlanRecipes.size(); j++) {
                        Recipe nextRecipe = mealPlanRecipes.get(j);
                        if(nextRecipe.getIngredientToUnit().containsKey(ingredientName)) {
                            line += "\n" + "\u2022 " + nextRecipe.getIngredientToUnit().get(ingredientName);
                        }
                    }
                    ingredientSet.add(ingredientName);
                    groceryListLines.add(line);
                }
            }
        }
        model.setCurrentGroceryList(groceryListLines);
        //TODO: writeback this data to the database
    }

    /**
     * Retrieve user's current grocery list from database
     * @param uid user database id
     * @param currContext current activity context
     * @return mapping of ingredient to the units needed in different recipes
     */
    public List<String> getCurrentGroceryList(int uid, Context currContext) {
        return model.getCurrentGroceryList();
        //TODO: retrieve from database
    }


}
