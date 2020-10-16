package com.example.libit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Category {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("name")
    @Expose
    private String name;

    @SerializedName("image")
    @Expose
    public String image;

    public long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public String getImage() {
        return image;
    }
}
