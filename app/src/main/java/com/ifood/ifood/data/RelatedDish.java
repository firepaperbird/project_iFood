package com.ifood.ifood.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class RelatedDish implements Serializable {
    private String id;
    private String name;
    private String imageLink;

    public RelatedDish() {
    }

    public RelatedDish(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.imageLink = jsonObject.getString("imageLink");
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImageLink() {
        return imageLink;
    }

    public void setImageLink(String imageLink) {
        imageLink = imageLink;
    }
}
