package com.ifood.ifood.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Model_Recipe implements Serializable {
    private int id;
    private String dishId;
    private String title;
    private String description;
    private String imageSource;

    public Model_Recipe() {
    }

    public Model_Recipe(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.dishId = jsonObject.getString("dishId");
        this.title = jsonObject.getString("title");
        this.description = jsonObject.getString("description");
        this.imageSource = jsonObject.getString("imageSource");
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getImageSource() {
        return imageSource;
    }

    public void setImageSource(String imageSource) {
        this.imageSource = imageSource;
    }
}
