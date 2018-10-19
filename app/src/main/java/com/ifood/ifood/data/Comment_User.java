package com.ifood.ifood.data;

import java.io.Serializable;
import java.util.Date;

public class Comment_User implements Serializable{
    private String name;
    private String review;
    private float star;
    private String time;

    public Comment_User(String name, String review, float star, String time) {
        this.name = name;
        this.review = review;
        this.star = star;
        this.time = time;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getReview() {
        return review;
    }

    public void setReview(String review) {
        this.review = review;
    }

    public float getStar() {
        return star;
    }

    public void setStar(float star) {
        this.star = star;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
