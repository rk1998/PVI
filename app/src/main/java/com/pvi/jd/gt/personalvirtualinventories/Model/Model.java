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
    private MutableLiveData<MealPlan> currentMealPlan;
    private Map<String, Recipe> storedRecipes;

    private Model() {
        currentUser = new MutableLiveData<>();
        currentMealPlan = new MutableLiveData<>();
        storedRecipes = new HashMap<>();
    }


    public MutableLiveData<User> getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(MutableLiveData<User> currentUser) {
        this.currentUser = currentUser;
    }

    public MutableLiveData<MealPlan> getCurrentMealPlan() {
        return currentMealPlan;
    }

    public void setCurrentMealPlan(MutableLiveData<MealPlan> currentMealPlan) {
        this.currentMealPlan = currentMealPlan;
    }

    public Map<String, Recipe> getStoredRecipes() {
        return storedRecipes;
    }
}
