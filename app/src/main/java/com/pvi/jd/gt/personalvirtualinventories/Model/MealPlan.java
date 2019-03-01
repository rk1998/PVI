package com.pvi.jd.gt.personalvirtualinventories.Model;

import java.util.AbstractMap;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * Created by paige on 2/11/2019.
 */

public class MealPlan {

    private List<Meal> mealPlan;
    private boolean exists;

    public MealPlan() {
        mealPlan = new ArrayList<>();
        exists = true;
    }

    public void addMeal(String apiID, boolean completed) {
        mealPlan.add(new Meal(apiID, completed));
    }

    public void addMeal(Recipe recipe, boolean completed) {
        Meal meal = new Meal(recipe.getApiID(), completed);
        meal.setRecipe(recipe);
        mealPlan.add(meal);

    }

    public List<Meal> getMealPlan() {
        return mealPlan;
    }

    public void setMealPlan(List<Meal> mealPlan) {
        this.mealPlan = mealPlan;
    }

    public boolean isExists() {
        return exists;
    }

    public void setExists(boolean exists) {
        this.exists = exists;
    }
}
