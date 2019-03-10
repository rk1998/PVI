package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.MutableLiveData;
import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

public class LoginViewModel extends ViewModel {
    private UserRepository userRepo;
    private MutableLiveData<User> attemptedUser;

    public LoginViewModel() {
        userRepo = UserRepository.getUserRepository();
    }

    /**
     * Attempts login with given email and password
     * @param email user's attempted email
     * @param password user's attempted password
     * @param currentContext current activity context
     */
    public void init(String email, String password, Context currentContext) {
        attemptedUser = userRepo.authGetUserInfo(email, password, currentContext);
    }

    public MutableLiveData<User> getAttemptedUser() {
        return attemptedUser;
    }
}
