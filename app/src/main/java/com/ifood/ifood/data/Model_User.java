package com.ifood.ifood.data;

import java.io.Serializable;

public class Model_User implements Serializable {
    private Integer id;
    private String username;
    private String email;
    private String address;
    private String description;
    private String password;
    private String phoneNumber;

    public Model_User() {
    }

    public Model_User(String username, String email, String address, String description, String password, String phoneNumber) {
        this.username = username;
        this.email = email;
        this.address = address;
        this.description = description;
        this.password = password;
        this.phoneNumber = phoneNumber;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }
}
