package com.example.libit.network;

import com.example.libit.models.LoginView;
import com.example.libit.models.RegisterView;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/api/account/login")
    public Call<Tokens> login(@Body LoginView model);

    @POST("/api/account/register")
    public Call<Tokens> register(@Body RegisterView model);

    ///@POST("/api/auth/refresh")
    ///public Call<Tokens> refresh(@Body Refresh m);
}
