package com.pvi.jd.gt.personalvirtualinventories.Model;

/**
 * Created by paige on 2/11/2019.
 */

public class Meal {
    private String apiID;
    private Recipe recipe;
    private boolean completed;

    public Meal(String apiID, boolean completed) {
        this.apiID = apiID;
        this.completed = completed;
    }

    public Recipe getRecipe() {
        return recipe;
    }

    public void setRecipe(Recipe recipe) {
        this.recipe = recipe;
    }

    public boolean isCompleted() {
        return completed;
    }

    public void setCompleted(boolean completed) {
        this.completed = completed;
    }

    public String getApiID() {
        return apiID;
    }
}
