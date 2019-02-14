package com.pvi.jd.gt.personalvirtualinventories.Model;

import android.arch.lifecycle.MutableLiveData;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;

import java.net.MalformedURLException;
import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();
    public static Model get_instance() {
        return _instance;
    }

    private MutableLiveData<User> currentUser;
    private MutableLiveData<MealPlan> currentMealPlan;

    private Model() {
        currentUser = new MutableLiveData<>();
        currentMealPlan = new MutableLiveData<>();
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
}
