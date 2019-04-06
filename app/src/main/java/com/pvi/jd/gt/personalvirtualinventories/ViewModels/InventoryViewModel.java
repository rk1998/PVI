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
}
