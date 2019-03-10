package com.pvi.jd.gt.personalvirtualinventories.ViewModels;

import android.arch.lifecycle.ViewModel;
import android.content.Context;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;
import com.pvi.jd.gt.personalvirtualinventories.Model.UserRepository;

import java.util.List;

/**
 * View Model for the Questionnaire
 */
public class QuestionnaireViewModel extends ViewModel {
    private UserRepository userRepo;

    public QuestionnaireViewModel() {
        this.userRepo = UserRepository.getUserRepository();
    }

    /**
     * Creates a new user object and passes it to the UserRepository
     * @param email user's email
     * @param password user's password
     */
    public void createNewUser(String email, String password) {
        User newUser = new User(email, password);
        userRepo.setTempUser(newUser);
    }

    /**
     * Record the answers for user's family size and preferred time spent per meal
     * @param numFamilyMembers number of members in user's family
     * @param timePerMeal amount of time in minutes they want to spend cooking a meal
     */
    public void setGeneralInfo(int numFamilyMembers, int timePerMeal) {
        userRepo.getTempUser().setCookTime(timePerMeal);
        userRepo.getTempUser().setNumFamilyMembers(numFamilyMembers);
    }

    /**
     * Sets user's dietary restrictions
     * @param restrictionNames names of user's dietary restrictions
     */
    public void setDietaryPreferences(List<String> restrictionNames) {
        userRepo.getTempUser().setDietRestriction(restrictionNames);
    }

    /**
     * Sets user's food allergies
     * @param allergies the user's food allergies
     */
    public void setFoodAllergies(List<String> allergies) {
        userRepo.getTempUser().setFoodAllergies(allergies);
    }

//    /**
//     * Sets user's disliked foods
//     * @param dislikedFoods list of user's disliked foods
//     */
//    public void setDislikedFoods(List<String> dislikedFoods) {
//        userRepo.getTempUser().setHatedFoods(dislikedFoods);
//    }

    public void addDislikedFoods(List<String> dislikedFoods) {
        userRepo.getTempUser().getHatedFoods().addAll(dislikedFoods);
    }

    /**
     * Sets user's must have meals
     * @param favoriteMeals
     */
    public void setFavoriteMeals(List<String> favoriteMeals) {
        userRepo.getTempUser().setFavoriteMealNames(favoriteMeals);
    }

    /**
     * Sets user's kitchen tools
     * @param tools
     */
    public void setKitchenTools(List<String> tools) {
        userRepo.getTempUser().setKitchenTools(tools);
    }



}
