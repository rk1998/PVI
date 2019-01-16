package com.pvi.jd.gt.personalvirtualinventories.Model;

import java.util.List;

public class Recipe {
    private int apiID;
    private String recipeTitle;
    private int cookTime;
    private int prepTime;
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

    public Recipe(int id, String title, int cook, int prep, String instructions,
                  List<String> ingredients, String source, String imgSource) {
        apiID = id;
        recipeTitle = title;
        cookTime = cook;
        prepTime = prep;
        this.instructions = instructions;
        this.ingredients = ingredients;
        recipeSource = source;
        imgURL = imgSource;

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
}
