package com.ifood.ifood.data;

public class Model_Cookbook_Dish {
    private Integer id;
    private String dishId;
    private String dishName;
    private String cookbookId;

    public Model_Cookbook_Dish() {
    }

    public Model_Cookbook_Dish(String dishId, String dishName, String cookbookId) {
        this.dishId = dishId;
        this.dishName = dishName;
        this.cookbookId = cookbookId;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getCookbookId() {
        return cookbookId;
    }

    public void setCookbookId(String userId) {
        this.cookbookId = userId;
    }
}
