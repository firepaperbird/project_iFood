package com.ifood.ifood.data;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Integer id;
    private int userId;
    private String name;
    private String phone;
    private String address;
    private int typeOfTransaction;
    private String orderTime;
    private int status;


    public Transaction() {
    }

    public Transaction(int userId, String name, String phone, String address, int typeOfTransaction, String orderTime, int status) {
        this.userId = userId;
        this.name = name;
        this.phone = phone;
        this.address = address;
        this.typeOfTransaction = typeOfTransaction;
        this.orderTime = orderTime;
        this.status = status;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }


    public int getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(int typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getOrderTime() {
        return orderTime;
    }

    public void setOrderTime(String orderTime) {
        this.orderTime = orderTime;
    }
}
