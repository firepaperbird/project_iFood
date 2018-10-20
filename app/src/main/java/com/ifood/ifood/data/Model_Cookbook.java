package com.ifood.ifood.data;

public class Model_Cookbook {
    private int id;
    private int dishId;
    private String dishName;
    private int userId;

    public Model_Cookbook() {
    }

    public Model_Cookbook(int dishId, String dishName, int userId) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.userId = userId;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public int getDishId() {
        return dishId;
    }

    public void setDishId(int dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }
}
