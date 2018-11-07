package com.ifood.ifood.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class Model_Cookbook implements Serializable {
    private String id;
    private String name;
    private String userId;
    private String description;
    private String createOn;
    private List<Dish> dishesInCookBook;
    public Model_Cookbook() {
    }

    public int getTotalRecipes() {
        if (this.dishesInCookBook == null){
            return 0;
        }
        return this.dishesInCookBook.size();
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

    public Model_Cookbook(String id, String name, String userId, String description, String createOn, List<Dish> dishesInCookBook) {
        this.id = id;
        this.name = name;
        this.userId = userId;
        this.description = description;
        this.createOn = createOn;
        this.dishesInCookBook = dishesInCookBook;
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

    public String getCreateOn() {
        return createOn;
    }

    public void setCreateOn(String createOn) {
        this.createOn = createOn;
    }

    public List<Dish> getDishesInCookBook() {
        return dishesInCookBook;
    }

    public void setDishesInCookBook(List<Dish> dishesInCookBook) {
        this.dishesInCookBook = dishesInCookBook;
    }
}
