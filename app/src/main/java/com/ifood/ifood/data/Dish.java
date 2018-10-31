package com.ifood.ifood.data;

import android.graphics.drawable.Drawable;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Dish implements Serializable{
    private Integer id;
    private String title;
    private String description;
    private String author;
    private Integer image;
    private int ratingStar;
    private List<String> tags;
    private String filterType;
    private List<Ingredient> ingredients;

    public Dish() {
    }

    public Dish(Integer id, String title, String description, String author, Integer image, List<String> tags, String filterType, int ratingStar) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.image = image;
        this.tags = tags;
        this.filterType = filterType;
        this.ratingStar = ratingStar;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
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

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }
    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public List<String> getTags() {
        if (tags == null){
            tags = new ArrayList<>();
        }
        return tags;
    }

    public void setTags(List<String> tags) {
        this.tags = tags;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public void increaseIngredient(Ingredient ingredient){
        if (ingredients == null){
            ingredients = new ArrayList<>();
        }

        ingredients.add(ingredient);
    }

    public String getFilterType() {
        return filterType;
    }

    public void setFilterType(String filterType) {
        this.filterType = filterType;
    }

    public int getRatingStar() {
        return ratingStar;
    }

    public void setRatingStar(int ratingStar) {
        this.ratingStar = ratingStar;
    }
}
