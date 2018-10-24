package com.ifood.ifood.data;

import java.util.ArrayList;
import java.util.List;

public class Transaction {
    private Integer id;
    private int userId;
    private List<DeleveryInfo> deleveryInfoList;
    private int typeOfTransaction;
    private String time;

    public Transaction(int userId, List<DeleveryInfo> deleveryInfoList, int typeOfTransaction, String time) {
        this.userId = userId;
        this.deleveryInfoList = deleveryInfoList;
        this.typeOfTransaction = typeOfTransaction;
        this.time = time;
    }

    public Transaction() {
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

    public List<DeleveryInfo> getDeleveryInfoList() {
        if(deleveryInfoList == null){
            deleveryInfoList = new ArrayList<DeleveryInfo>();
        }
        return deleveryInfoList;
    }

    public void setDeleveryInfoList(List<DeleveryInfo> deleveryInfoList) {
        this.deleveryInfoList = deleveryInfoList;
    }

    public int getTypeOfTransaction() {
        return typeOfTransaction;
    }

    public void setTypeOfTransaction(int typeOfTransaction) {
        this.typeOfTransaction = typeOfTransaction;
    }

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
