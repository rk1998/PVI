package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;

import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;

public class Model {
    private static final Model _instance = new Model();
    public static Model get_instance() {
        return _instance;
    }

    private MutableLiveData<User> currentUser;
    //private User currentUser;
    private MutableLiveData<MealPlan> currentMealPlan;
    private Map<String, Recipe> storedRecipes;
    private User tempUser;

    private Model() {
        currentUser = new MutableLiveData<>();
        //currentUser = new User();
        currentMealPlan = new MutableLiveData<>();
        storedRecipes = new HashMap<>();
        tempUser = new User();
    }


    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

    /*public User getCurrentUser() {
        return currentUser;
    }*/

    /*public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }*/

    public void setCurrentUser(MutableLiveData<User> currentUser) {
        this.currentUser = currentUser;
    }

    public MutableLiveData<MealPlan> getCurrentMealPlan() {
        return currentMealPlan;
    }

    public void setCurrentMealPlan(MutableLiveData<MealPlan> currentMealPlan) {
        this.currentMealPlan = currentMealPlan;
    }

    /**
     * Sets the temporary user (used to store registration info)
     * @param user initial user info (email, pw)
     */
    public void setTempUser(User user) {
        tempUser = user;
    }

    /**
     * Gets temp user
     * @return tempUser
     */
    public User getTempUser() {
        return tempUser;
    }

    public Map<String, Recipe> getStoredRecipes() {
        return storedRecipes;
    }
}
