package com.example.libit.network;

import android.content.SharedPreferences;
import android.util.Log;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Tokens {
    @SerializedName("token")
    @Expose
    private String token;
    @SerializedName("refreshToken")
    @Expose
    private String refreshToken;

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

    public String getRefreshToken() {
        return refreshToken;
    }

    public void setRefreshToken(String refreshToken) {
        this.refreshToken = refreshToken;
    }

    public Tokens(String token, String refreshToken) {
        this.token = token;
        this.refreshToken = refreshToken;
    }

    public static void saveJWTToken(String token, SharedPreferences prefs) {
        SharedPreferences.Editor edit = prefs.edit();

        try {
            edit.putString("token", token);
            Log.i("token", token);
//            edit.commit();
            edit.apply();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
