package com.example.libit.data;

import androidx.annotation.NonNull;

import com.example.libit.models.UserView;
import com.example.libit.network.NetworkService;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

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
