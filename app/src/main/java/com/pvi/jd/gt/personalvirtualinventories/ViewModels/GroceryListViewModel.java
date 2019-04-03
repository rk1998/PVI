package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.GroceryListRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.ArrayList;
import java.util.Map;

/**
 * View Model for Grocery List
 */
public class GroceryListViewModel extends ViewModel {
    private UserRepository userRepo;
    private GroceryListRepository groceryRepo;
    private MutableLiveData<User> currentUser;
    private Context currentContext;
    private Map<String, ArrayList<String>> currentGroceryList;
    public GroceryListViewModel() {
        userRepo = UserRepository.getUserRepository();
        groceryRepo = GroceryListRepository.getGroceryListRepository();
    }

    /**
     * Initializes data retrieval for grocery list
     * @param currContext current activity context
     */
    public void init(Context currContext) {
        this.currentContext = currContext;
        this.currentUser = userRepo.getCurrUser();
        int uid = this.currentUser.getValue().getId();
        this.currentGroceryList = groceryRepo.getCurrentGroceryList(uid, this.currentContext);
    }

    public Map<String, ArrayList<String>> getCurrentGroceryList() {
        return currentGroceryList;
    }
}
