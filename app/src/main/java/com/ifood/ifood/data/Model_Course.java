package com.ifood.ifood.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Model_Course implements Serializable {
    private String id;
    private String name;
    private String description;
    private Boolean isActive;
    private Boolean isDelete;

    public Model_Course() {
    }

    public Model_Course(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.name = jsonObject.getString("name");
        this.description = jsonObject.getString("description");
        this.isActive = jsonObject.getBoolean("active");
        this.isDelete = jsonObject.getBoolean("delete");
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

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Boolean getActive() {
        return isActive;
    }

    public void setActive(Boolean active) {
        isActive = active;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
