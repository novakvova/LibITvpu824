package com.example.libit.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Book {
    @SerializedName("id")
    @Expose
    private long id;

    @SerializedName("author")
    @Expose
    private String author;

    @SerializedName("name")
    @Expose
    public String name;

    @SerializedName("image")
    @Expose
    public String image;

    @SerializedName("categoryId")
    @Expose
    private long categoryId;

    public Book() {
    }

    public Book(long id, String name, String author, long categoryId) {
        this.id = id;
        this.name = name;
        this.author = author;
        this.categoryId = categoryId;
    }

    public long getId() {
        return id;
    }

    public void setId(long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public long getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(long categoryId) {
        this.categoryId = categoryId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
