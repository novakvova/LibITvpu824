package com.example.libit.network;

import com.example.libit.models.LoginView;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/api/account/login")
    public Call<Tokens> login(@Body LoginView m);

    ///@POST("/api/auth/refresh")
    ///public Call<Tokens> refresh(@Body Refresh m);
}
