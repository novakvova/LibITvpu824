package com.example.libit.network;

import com.example.libit.models.LoginView;
import com.example.libit.models.ProfileEditView;
import com.example.libit.models.ProfileView;
import com.example.libit.models.RegisterView;
import com.example.libit.models.UserView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Header;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/api/account/login")
    Call<Tokens> login(@Body LoginView model);

    @POST("/api/account/register")
    Call<Tokens> register(@Body RegisterView model);

    @POST("/api/profile/info")
    Call<UserView> profile();

//    @POST("/api/profile/editprofile")
//    Call<UserView> edit(@Body ProfileEditView model);

    @POST("/api/profile/editprofile")
    Call<UserView> edit(@Body ProfileEditView model);

    @POST("/api/profile/allusers")
    Call<List<ProfileView>> getuserslist1();

    @GET("/api/profile/allusers")
    Call<List<ProfileView>> getuserslist();
}
