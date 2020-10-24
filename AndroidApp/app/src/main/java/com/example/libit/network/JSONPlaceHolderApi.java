package com.example.libit.network;

import com.example.libit.models.Book;
import com.example.libit.models.Category;
import com.example.libit.models.LoginView;
import com.example.libit.models.Photo;
import com.example.libit.models.RegisterView;
import com.example.libit.models.UserView;

import java.util.List;

import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import okhttp3.ResponseBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

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

    @POST("/api/library/addbook")
    Call<Book> addBook(@Body Book book);

}
