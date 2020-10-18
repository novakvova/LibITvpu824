package com.example.libit.network;

import com.example.libit.models.Category;
import com.example.libit.models.LoginView;
import com.example.libit.models.Photo;
import com.example.libit.models.RegisterView;
import com.example.libit.models.UserView;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.POST;

public interface JSONPlaceHolderApi {
    @POST("/api/account/login")
    Call<Tokens> login(@Body LoginView model);

    @POST("/api/account/register")
    Call<Tokens> register(@Body RegisterView model);

    @POST("/api/profile/info")
    Call<UserView> profile();

    @POST("/api/profile/update")
    Call<UserView> update(@Body UserView profile);

    @POST("/api/profile/update-photo")
    Call<UserView> updatePhoto(@Body Photo photo);

    @GET("/api/library/categories")
    Call<List<Category>> getCategories();
}
