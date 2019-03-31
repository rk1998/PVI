package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

/**
 * View Model for Grocery List
 */
public class GroceryListViewModel extends ViewModel {
    private UserRepository userRepo;
    private MutableLiveData<User> currentUser;
    private Context currentContext;

    public GroceryListViewModel() {
        userRepo = UserRepository.getUserRepository();
    }

    public void init(Context currContext) {
        this.currentContext = currContext;
        this.currentUser = userRepo.getCurrUser();
    }
}
