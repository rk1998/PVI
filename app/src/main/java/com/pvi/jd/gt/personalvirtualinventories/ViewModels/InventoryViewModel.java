package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

/**
 * View Model for Inventory
 */
public class InventoryViewModel extends ViewModel {
    private UserRepository userRepo;
    private MutableLiveData<User> currentUser;
    private Context currContext;

    public InventoryViewModel() {
        this.userRepo = UserRepository.getUserRepository();
    }

    public void init(Context currContext) {
        this.currContext = currContext;
        this.currentUser = userRepo.getCurrUser();
    }
}
