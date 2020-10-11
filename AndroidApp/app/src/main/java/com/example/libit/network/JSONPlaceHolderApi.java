package com.example.libit.network;

import com.example.libit.models.LoginView;
import com.example.libit.models.ProfileView;
import com.example.libit.models.RegisterView;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/api/account/login")
    public Call<Tokens> login(@Body LoginView m);

    @POST("/api/account/register")
    public Call<Tokens> register(@Body RegisterView m);

    @GET("/api/profile/info")
    public Call<ProfileView> showInfo(@Header("Authorization") String token);
  //  public Call<ProfileView> showInfo(String token);

    ///@POST("/api/auth/refresh")
    ///public Call<Tokens> refresh(@Body Refresh m);
}
