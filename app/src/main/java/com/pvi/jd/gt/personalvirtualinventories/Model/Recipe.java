package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.LiveData;

import java.util.LinkedList;
import java.util.List;

public class Recipe {
    private int apiID;
    private String recipeTitle;
    private int cookTime;
    private int prepTime;
    private int numServings;
    private String instructions;
    private List<String> ingredients;
    private String recipeSource;
    private String imgURL;


    /**
     * Constructor for Custom Recipes
     * @param title - recipe title
     * @param cook - recipe cook time
     * @param prep - recipe preperation time
     * @param instructions - recipe instructions
     * @param ingredients - recipe ingredients
     */
    public Recipe(String title, int cook, int prep, String instructions, List<String> ingredients) {
        recipeTitle = title;
        cookTime = cook;
        prepTime = prep;
        this.instructions = instructions;
        this.ingredients = ingredients;
        apiID = -1;
        recipeSource = "custom";
        imgURL = "none";
    }

    public Recipe(int id, String title, int cook, int prep, int servings, String instructions,
                  List<String> ingredients, String source, String imgSource) {
        apiID = id;
        recipeTitle = title;
        cookTime = cook;
        prepTime = prep;
        numServings = servings;
        this.instructions = instructions;
        this.ingredients = ingredients;
        recipeSource = source;
        imgURL = imgSource;

    }

    public Recipe() {
        recipeTitle = "";
        cookTime = 0;
        prepTime = 0;
        numServings = 0;
        instructions = "";
        ingredients = new LinkedList<>();
        recipeSource = "";
        imgURL = "";
    }
    public int getApiID() {
        return apiID;
    }
    public String getRecipeTitle() {
        return recipeTitle;
    }
    public int getCookTime() {
        return cookTime;
    }
    public int getPrepTime() {
        return prepTime;
    }

    public int getNumServings() {
        return numServings;
    }

    public String getInstructions() {
        return instructions;
    }
    public List<String> getIngredients() {
        return ingredients;
    }
    public String getRecipeSource() {
        return recipeSource;
    }
    public String getImgURL() {
        return imgURL;
    }


    public void setRecipeTitle(String title) {
        recipeTitle = title;
    }

    public void setCookTime(int cookTime) {
        this.cookTime = cookTime;
    }

    public void setPrepTime(int prepTime) {
        this.prepTime = prepTime;
    }
    public void setNumServings(int servings) {
        this.numServings = servings;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setIngredients(List<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void setRecipeSource(String recipeSource) {
        this.recipeSource = recipeSource;
    }
}
