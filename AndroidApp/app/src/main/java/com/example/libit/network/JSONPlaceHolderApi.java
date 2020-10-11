package com.example.libit.network;

import com.example.libit.models.LoginView;
import com.example.libit.models.RegisterView;
import com.example.libit.models.UserView;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/api/account/login")
    Call<Tokens> login(@Body LoginView model);

    @POST("/api/account/register")
    Call<Tokens> register(@Body RegisterView model);

    @POST("/api/profile/info")
    Call<UserView> profile(@Header("Authorization") String token);
}
