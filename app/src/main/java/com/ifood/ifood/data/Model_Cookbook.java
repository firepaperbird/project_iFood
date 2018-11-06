package com.ifood.ifood.data;

import android.support.annotation.Nullable;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Model_Cookbook implements Serializable {
    private String id;
    private String name;
    private String userId;
    private String description;
    private Timestamp createOn;
    private Boolean isDelete;
//    private int totalRecipes;
    private List<Model_Cookbook_Dish> dishOfCookBook;
    public Model_Cookbook() {
    }



    public int getTotalRecipes() {
        return this.dishOfCookBook.size();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public Model_Cookbook(String id, String name, String userId, String description, Timestamp createOn, List<Model_Cookbook_Dish> dishOfCookBook) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.description = description;
        this.createOn = createOn;
        this.dishOfCookBook = dishOfCookBook;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Timestamp getCreateOn() {
        return createOn;
    }

    public void setCreateOn(Timestamp createOn) {
        this.createOn = createOn;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }

    public List<Model_Cookbook_Dish> getDishOfCookBook() {
        return dishOfCookBook;
    }

    public void setDishOfCookBook(List<Model_Cookbook_Dish> dishOfCookBook) {
        this.dishOfCookBook = dishOfCookBook;
    }
}
