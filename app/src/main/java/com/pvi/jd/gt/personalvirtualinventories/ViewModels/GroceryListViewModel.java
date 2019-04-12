package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.GroceryListRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.Model.InventoryRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.Recipe;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * View Model for Grocery List
 */
public class GroceryListViewModel extends ViewModel {
    private UserRepository userRepo;
    private GroceryListRepository groceryRepo;
    private InventoryRepository   inventoryRepo;
    private MutableLiveData<User> currentUser;
    private Context currentContext;
    private MutableLiveData<ArrayList<IngredientQuantity>> currentGroceryList;
    public GroceryListViewModel() {
        userRepo = UserRepository.getUserRepository();
        groceryRepo = GroceryListRepository.getGroceryListRepository();
        inventoryRepo = InventoryRepository.getInventoryRepository();
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

    /**
     * Adds item to the grocery list
     * @param itemToAdd item to add to the grocery list
     */
    public void addToGroceryList(IngredientQuantity itemToAdd) {
        int userId = this.currentUser.getValue().getId();
        ArrayList<IngredientQuantity> items = new ArrayList<>();
        items.add(itemToAdd);
        this.groceryRepo.updateUserGroceryList(userId, items, new ArrayList<>(), this.currentContext);
    }

    public MutableLiveData<ArrayList<IngredientQuantity>> getCurrentGroceryList() {
        return currentGroceryList;
    }

    /**
     * Writes back checked off grocery list items to the database
     * @param groceryListItem grocery list item that's been checked off.
     */
    public void checkOffGroceryListItem(IngredientQuantity groceryListItem) {
        int userID = this.currentUser.getValue().getId();
        IngredientQuantity inventoryItem = this.inventoryRepo.convertToInventoryItem(groceryListItem);
        if(!this.inventoryRepo.itemExists(inventoryItem)) {
            ArrayList<IngredientQuantity> itemsToAdd = new ArrayList<>();
            itemsToAdd.add(inventoryItem);
            this.inventoryRepo.updateUserInventory(userID, itemsToAdd, new ArrayList<>(),
                    this.currentContext);
        }

    }

    /**
     * Converts IngredientQuantity objects into Strings that can be displayed on the grocery list
     * @param ingredientQuantities array list of ingredient quanities in user's grocery list
     * @return list of strings to display on grocery list
     */
    public List<String> getGroceryListDisplay(ArrayList<IngredientQuantity> ingredientQuantities) {
        Set<String> ingredientSet = new HashSet<>();
        ArrayList<String> groceryListLines = new ArrayList<>();
        for(int i = 0; i < ingredientQuantities.size(); i++) {
            IngredientQuantity entry = ingredientQuantities.get(i);
            if(!ingredientSet.contains(entry.getIngredient())) {
                String line = entry.getIngredient();
                if(!entry.getAmount().isEmpty()) {
                    line += "\n" + "\u2022 " + entry.getAmount();
                }
                for(int j = i; j < ingredientQuantities.size(); j++) {
                    IngredientQuantity nextEntry = ingredientQuantities.get(j);
                    if(nextEntry.getIngredient().equals(entry.getIngredient())) {
                        if(!nextEntry.getAmount().isEmpty()) {
                            line += "\n" + "\u2022 " + nextEntry.getAmount();
                        }
                    }
                }
                ingredientSet.add(entry.getIngredient());
                groceryListLines.add(line);
            }
        }
        return groceryListLines;
    }
}
