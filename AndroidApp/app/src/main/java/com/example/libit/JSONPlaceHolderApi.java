package com.example.libit;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;

public interface JSONPlaceHolderApi {
    @GET("/posts/{id}")
    public Call<Post> getPostWithID(@Path("id") int id);

    @GET("pubinfo?json&exchange&coursid=5")
    public Call<List<Currency>> getCurrencies();
}
