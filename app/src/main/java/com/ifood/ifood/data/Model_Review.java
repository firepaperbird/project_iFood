package com.ifood.ifood.data;

import com.google.gson.Gson;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.sql.Date;
import java.sql.Timestamp;

public class Model_Review implements Serializable {
    private int id;
    private String dishId;
    private String userId;
    private String userName;
    private String comment;
    private int rate;
    private Timestamp reviewOn;
    private Boolean isDelete;

    public Model_Review() {
    }

    public Model_Review(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getInt("id");
        this.dishId = jsonObject.getString("dishId");
        this.comment = jsonObject.getString("comment");
        this.rate = (int) jsonObject.getDouble("rate");
        this.reviewOn = Timestamp.valueOf(jsonObject.getString("reviewOn").substring(0,10) + " 00:00:00");
        this.isDelete = jsonObject.getBoolean("delete");
        Gson gson = new Gson();
        Model_User user = gson.fromJson(jsonObject.getString("userReview"), Model_User.class);
        this.userName = user.getName();
        this.userId = user.getId();
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
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

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public int getRate() {
        return rate;
    }

    public void setRate(int rate) {
        this.rate = rate;
    }

    public Timestamp getReviewOn() {
        return reviewOn;
    }

    public void setReviewOn(Timestamp reviewOn) {
        this.reviewOn = reviewOn;
    }

    public Boolean getDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
