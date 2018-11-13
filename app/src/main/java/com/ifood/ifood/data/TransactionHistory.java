package com.ifood.ifood.data;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.List;

public class TransactionHistory implements Serializable {
    private String id;
    private String userId;
    private String city;
    private String district;
    private String address;
    private Integer status;
    private String description;
    private Double totalPrice;
    private Double discount;
    private Timestamp createdOn;

    private List<TransactionDetail> transactionDetails;

    public List<TransactionDetail> getTransactionDetails() {
        return transactionDetails;
    }

    public void setTransactionDetails(List<TransactionDetail> transactionDetails) {
        this.transactionDetails = transactionDetails;
    }

    public TransactionHistory() {
    }

    public TransactionHistory(String id, String userId, String city, String district, String address, Integer status, String description, Double totalPrice, Double discount, Timestamp createdOn, List<TransactionDetail> transactionDetails) {
        this.id = id;
        this.userId = userId;
        this.city = city;
        this.district = district;
        this.address = address;
        this.status = status;
        this.description = description;
        this.totalPrice = totalPrice;
        this.discount = discount;
        this.createdOn = createdOn;
        this.transactionDetails = transactionDetails;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Integer getStatus() {
        return status;
    }

    public void setStatus(Integer status) {
        this.status = status;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Double getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(Double totalPrice) {
        this.totalPrice = totalPrice;
    }

    public Double getDiscount() {
        return discount;
    }

    public void setDiscount(Double discount) {
        this.discount = discount;
    }

    public Timestamp getCreatedOn() {
        return createdOn;
    }

    public void setCreatedOn(Timestamp createdOn) {
        this.createdOn = createdOn;
    }
}
