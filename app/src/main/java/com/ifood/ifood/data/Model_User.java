package com.ifood.ifood.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Model_User implements Serializable {
    private String  id;
    private String name;
    private String description;
    private String email;
    private String password;
    private String phoneNumber;
    private String city;
    private String district;
    private String street;
    private String address;
    private Boolean isDelete;

    public Model_User() {
    }

    public Model_User(String id, String name, String description, String email, String password, String phoneNumber, String city, String district, String street, String address) {
        this.id = id;
        this.name = name;
        this.description = description;
        this.email = email;
        this.password = password;
        this.phoneNumber = phoneNumber;
        this.city = city;
        this.district = district;
        this.street = street;
        this.address = address;
    }

    public Model_User(String name, String email, String password) {
        this.name = name;
        this.email = email;
        this.password = password;
    }

    public Model_User(JSONObject json) throws JSONException {
        this.id = json.getString("id");
        this.email = json.getString("email");
        this.name = json.getString("name");
        this.description = json.getString("description");
        this.phoneNumber = json.getString("phoneNumber");
        this.city = json.getString("city");
        this.district = json.getString("district");
        this.street = json.getString("street");
        this.address = json.getString("address");
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

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getDistrict() {
        return district;
    }

    public void setDistrict(String district) {
        this.district = district;
    }

    public String getStreet() {
        return street;
    }

    public void setStreet(String street) {
        this.street = street;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Boolean isDelete() {
        return isDelete;
    }

    public void setDelete(Boolean delete) {
        isDelete = delete;
    }
}
