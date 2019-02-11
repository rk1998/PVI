package com.pvi.jd.gt.personalvirtualinventories.Model;

import com.pvi.jd.gt.personalvirtualinventories.Model.User;

import java.util.LinkedList;
import java.util.List;

public class Model {
    private static final Model _instance = new Model();
    public static Model get_instance() {
        return _instance;
    }
    private List<User> userList;
    private User currentUser;
    private Model() {
        userList = new LinkedList<>();
        currentUser = new User();
    }

    /**
     * Adds new user to user list and to User database
     * @param newUser the new user to add
     * @return if the addition was successful
     */
    public boolean addUser(User newUser) {
        if(userList.contains(newUser)) {
            return false;
        } else {
            userList.add(newUser);
            currentUser = newUser;
            return true;
        }
    }

    /**
     * Attempts to login user with given email and password
     * @param userEmail
     * @param password
     * @return if the user was successfully logged in
     */
    public boolean loginUser(String userEmail, String password) {
        User temp = new User(userEmail, password);
        for(User u: userList) {
            if(u.equals(temp)) {
                currentUser = u;
                return true;
            }
        }
        return false;
    }


    public User getCurrentUser() {
        return currentUser;
    }

    public void setCurrentUser(User currentUser) {
        this.currentUser = currentUser;
    }
}
