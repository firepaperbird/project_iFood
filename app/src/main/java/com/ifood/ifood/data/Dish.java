package com.ifood.ifood.data;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Dish {
    private String id;
    private String title;
    private String description;
    private String author;
    private int image;
    private List<String> tags;


    public Dish(String id, String title, String description, String author, int image, List<String> tags) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.author = author;
        this.image = image;
        this.tags = tags;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
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

    public int getImage() {
        return image;
    }

    public void setImage(int image) {
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
}
