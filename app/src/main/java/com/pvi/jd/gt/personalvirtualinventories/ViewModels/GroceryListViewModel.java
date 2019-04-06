package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.GroceryListRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * View Model for Grocery List
 */
public class GroceryListViewModel extends ViewModel {
    private UserRepository userRepo;
    private GroceryListRepository groceryRepo;
    private MutableLiveData<User> currentUser;
    private Context currentContext;
    private MutableLiveData<ArrayList<IngredientQuantity>> currentGroceryList;
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
        this.currentGroceryList = (MutableLiveData<ArrayList<IngredientQuantity>>)
                Transformations.switchMap(this.currentUser, user ->
                        groceryRepo.getUserGroceryList(user.getId(), this.currentContext));
    }

    public MutableLiveData<ArrayList<IngredientQuantity>> getCurrentGroceryList() {
        return currentGroceryList;
    }

    public List<String> getGroceryListDisplay(ArrayList<IngredientQuantity> ingredientQuantities) {
        Set<String> ingredientSet = new HashSet<>();
        ArrayList<String> groceryListLines = new ArrayList<>();
        for(int i = 0; i < ingredientQuantities.size(); i++) {
            IngredientQuantity entry = ingredientQuantities.get(i);
            if(!ingredientSet.contains(entry.getIngredient())) {
                String line = entry.getIngredient();
                line += "\n" + "\u2022 " + entry.getAmount();
                for(int j = i; j < ingredientQuantities.size(); j++) {
                    IngredientQuantity nextEntry = ingredientQuantities.get(j);
                    if(nextEntry.getIngredient().equals(entry.getIngredient())) {
                        line += "\n" + "\u2022 " + nextEntry.getAmount();
                    }
                }
                ingredientSet.add(entry.getIngredient());
                groceryListLines.add(line);
            }
        }
        return groceryListLines;
    }
}
