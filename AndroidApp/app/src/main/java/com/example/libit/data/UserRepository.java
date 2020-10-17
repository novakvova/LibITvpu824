package com.example.libit.data;

import com.example.libit.models.UserView;

public class UserRepository {
    private static volatile UserRepository instance;
    private UserView userProfile;

    // private constructor : singleton access
    private UserRepository() {
    }

    public static UserRepository getInstance() {
        if (instance == null) {
            instance = new UserRepository();
        }
        return instance;
    }

    public UserView getUserProfile() {
        return userProfile;
    }

    public boolean isLoggedIn() {
        return userProfile != null;
    }

    public void logout() {
        userProfile = null;
    }

    public void setUserProfile() {

    }
}
