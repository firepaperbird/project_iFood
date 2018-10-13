package com.ifood.ifood.data;

import android.graphics.drawable.Drawable;

import java.util.ArrayList;
import java.util.List;

public class Menu {
    private String title;
    private String description;
    private Drawable image;
    private List<String> tags;

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

    public Drawable getImage() {
        return image;
    }

    public void setImage(Drawable image) {
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
