package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.LiveData;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

public class Recipe {
    private String apiID;
    private String recipeTitle;
    private int cookTime;
    private int prepTime;
    private int numServings;
    private String instructions;
    private ArrayList<String> ingredients;
    private ArrayList<String> nutritionInfo;
    private String recipeSource;
    private String imgURL;


    /**
     * Constructor for Custom Recipes
     * @param title - recipe title
     * @param cook - recipe cook time
     * @param prep - recipe preparation time
     * @param instructions - recipe instructions
     * @param ingredients - recipe ingredients
     */
    public Recipe(String title, int cook, int prep, String instructions, ArrayList<String> ingredients) {
        recipeTitle = title;
        cookTime = cook;
        prepTime = prep;
        this.instructions = instructions;
        this.ingredients = ingredients;
        apiID = "";
        recipeSource = "custom";
        imgURL = "";
        nutritionInfo = new ArrayList<>();
    }

    public Recipe(String id, String title, int cook, int prep, int servings, String instructions,
                  ArrayList<String> ingredients, String source, String imgSource) {
        apiID = id;
        recipeTitle = title;
        cookTime = cook;
        prepTime = prep;
        numServings = servings;
        this.instructions = instructions;
        this.ingredients = ingredients;
        recipeSource = source;
        imgURL = imgSource;
        nutritionInfo = new ArrayList<>();

    }

    public Recipe() {
        recipeTitle = "";
        cookTime = 0;
        prepTime = 0;
        numServings = 0;
        instructions = "";
        ingredients = new ArrayList<>();
        recipeSource = "";
        imgURL = "";
        apiID = "";
        nutritionInfo = new ArrayList<>();
    }

    @Override
    public boolean equals(Object o) {
        Recipe other = (Recipe) o;
        return (this == o) || (this.getApiID().equals(other.getApiID()));
    }
    public String getApiID() {
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

    public String getDetails() {
        String details =  "Cook time: " + cookTime + " Minutes\nPrep time: " + prepTime + " Minutes" +
                "\nNumber of Servings: " + numServings + "\n";
        for(int i = 0; i < nutritionInfo.size(); i++) {
            details += nutritionInfo.get(i);
        }
        return details;
    }
    public String getInstructions() {
        return instructions;
    }
    public ArrayList<String> getIngredients() {
        return ingredients;
    }

    public ArrayList<String> getNutritionInfo() {
        return nutritionInfo;
    }

    public String getRecipeSource() {
        return recipeSource;
    }
    public String getImgURL() {
        return imgURL;
    }

    public void setApiID(String apiID) {
        this.apiID = apiID;
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

    public void setNutritionInfo(ArrayList<String> nutritionInfo) {
        this.nutritionInfo = nutritionInfo;
    }

    public void setNumServings(int servings) {
        this.numServings = servings;
    }
    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
    public void setIngredients(ArrayList<String> ingredients) {
        this.ingredients = ingredients;
    }
    public void setRecipeSource(String recipeSource) {
        this.recipeSource = recipeSource;
    }
    public void setImgURL(String url) {
        this.imgURL = url;
    }
}
