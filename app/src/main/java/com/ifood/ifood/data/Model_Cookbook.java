package com.ifood.ifood.data;

import android.support.annotation.Nullable;

public class Model_Cookbook {
    private Integer id;
    private Integer userId;
    private String title;
    private String imageId;
    private int totalRecipes;

    public Model_Cookbook() {
    }

    public Model_Cookbook(Integer userId, String title, String imageId, int totalRecipes) {
        this.userId = userId;
        this.title = title;
        this.imageId = imageId;
        this.totalRecipes = totalRecipes;
    }

    public int getTotalRecipes() {
        return totalRecipes;
    }

    public void setTotalRecipes(int totalRecipes) {
        this.totalRecipes = totalRecipes;
    }

    public void increateTotalRecipes(){
        this.totalRecipes++;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public Integer getUserId() {
        return userId;
    }

    public void setUserId(Integer userId) {
        this.userId = userId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getImageId() {
        return imageId;
    }

    public void setImageId(String imageId) {
        this.imageId = imageId;
    }
}
