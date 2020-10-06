package com.example.libit;

import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.Retrofit;

public class NetworkService {
    private static NetworkService mInstance;
   // private static final String BASE_URL = "https://jsonplaceholder.typicode.com";
    private static final String BASE_URL= "https://api.privatbank.ua/p24api/";
    private Retrofit mRetrofit;

    private NetworkService() {
        mRetrofit = new Retrofit.Builder()
                .baseUrl(BASE_URL)
                .addConverterFactory(GsonConverterFactory.create())
                .build();
    }

    public static NetworkService getInstance() {
        if (mInstance == null) {
            mInstance = new NetworkService();
        }
        return mInstance;
    }

    public JSONPlaceHolderApi getJSONApi() {
        return mRetrofit.create(JSONPlaceHolderApi.class);
    }
}
