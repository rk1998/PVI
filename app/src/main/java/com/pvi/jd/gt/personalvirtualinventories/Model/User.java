package com.pvi.jd.gt.personalvirtualinventories.Model;

import java.util.LinkedList;
import java.util.List;

public class User {
    private String email;
    private String password;
    private List<String> hatedFoods;
    private List<Recipe> recipeList;
    private int cookTime;
    private List<String> kitchenTools;

    public User() {
        this.email = "email";
        this.password = "password";
    }

    public User(String email, String password) {
        this.email = email;
        this.password = password;
        hatedFoods = new LinkedList<>();
        recipeList = new LinkedList<>();
        kitchenTools = new LinkedList<>();
        cookTime = 0;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
    public List<String> getHatedFoods() {
        return hatedFoods;
    }

    public int getCookTime() {
        return cookTime;
    }

    public List<String> getKitchenTools() {
        return kitchenTools;
    }

    public List<Recipe> getRecipes() {
        return recipeList;
    }
    public void setPassword(String newPassword) {
        password = newPassword;
    }
    public void setHatedFoods(List<String> foods) {
        hatedFoods = foods;
    }
    public void setCookTime(int time) {
        cookTime = time;
    }
    public void setKitchenTools(List<String> tools) {
        this.kitchenTools = tools;
    }

    /**
     * Adds multiple recipes to the User's list
     * @param recipes list of recipes to add.
     */
    public void addRecipes(List<Recipe> recipes) {
        for(Recipe r : recipes) {
            recipeList.add(r);
        }
    }

    /**
     * Adds a new recipe to the User's database
     * @param r recipe to add
     */
    public void addNewRecipe(Recipe r) {
        recipeList.add(r);
    }

    /**
     * Removes recipe from User's list of recipes and from the database
     * @param recipeToRemove
     * @return if the recipe exists in the User's database then the recipe is removed and true is
     * returned. Otherwise false.
     */
    public boolean removeRecipe(Recipe recipeToRemove) {
        return recipeList.remove(recipeToRemove);
    }

    @Override
    public boolean equals(Object obj) {
        User other  = (User) obj;
        return this.email.equals(other.getEmail()) && this.password.equals(other.getPassword());
    }
}
