package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.Transformations;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.IngredientQuantity;
import com.pvi.jd.gt.personalvirtualinventories.Model.InventoryRepository;
import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.ArrayList;

/**
 * View Model for Inventory
 */
public class InventoryViewModel extends ViewModel {
    private UserRepository userRepo;
    private InventoryRepository inventoryRepo;
    private MutableLiveData<User> currentUser;
    private Context currContext;
    private MutableLiveData<ArrayList<IngredientQuantity>> currentInventory;

    public InventoryViewModel() {
        this.userRepo = UserRepository.getUserRepository();
        this.inventoryRepo = InventoryRepository.getInventoryRepository();
    }

    public void init(Context currContext) {
        this.currContext = currContext;
        this.currentUser = userRepo.getCurrUser();
        this.currentInventory = (MutableLiveData<ArrayList<IngredientQuantity>>)
                Transformations.switchMap(this.currentUser, user ->
                        inventoryRepo.getUserInventory(user.getId(), this.currContext));
    }

    /**
     * Adds item to the inventory
     * @param itemToAdd item to add to the inventory
     */
    public void addToInventory(IngredientQuantity itemToAdd) {
        int userId = this.currentUser.getValue().getId();
        ArrayList<IngredientQuantity> items = new ArrayList<>();
        items.add(itemToAdd);
        this.inventoryRepo.updateUserInventory(userId, items, new ArrayList<IngredientQuantity>(), this.currContext);
    }

    public MutableLiveData<ArrayList<IngredientQuantity>> getCurrentInventory() {
        return currentInventory;
    }

    /**
     * Convert Ingredient Quantity items to a string that can be displayed on the Inventory
     * @param inventoryItems
     * @return list of display strings
     */
    public ArrayList<String> getInventoryDisplay(ArrayList<IngredientQuantity> inventoryItems) {
        ArrayList<String> displayList = new ArrayList<>();
        for(int i = 0; i < inventoryItems.size(); i++) {
            String inventoryLine = inventoryItems.get(i).getAmount()
                    + " " + inventoryItems.get(i).getIngredient();
            displayList.add(inventoryLine);
        }
        return displayList;
    }
}
