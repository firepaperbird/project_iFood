package com.ifood.ifood.data;

public class Model_ShoppingList {
    private Integer id;
    private int transactionId;
    private String userId;
    private String dishId;
    private String dishName;
    private String dishImage;
    private String ingredientId;
    private String ingredientName;
    private String amount;
    private String ingredientUnit;
    private double pricePerUnit;
    private int status;

    public Model_ShoppingList() {
    }

    public Model_ShoppingList(int transactionId, String userId, String dishId, String dishName, String dishImage, String ingredientId, String ingredientName, String amount, String ingredientUnit, double pricePerUnit, int status) {
        this.transactionId = transactionId;
        this.userId = userId;
        this.dishId = dishId;
        this.dishName = dishName;
        this.dishImage = dishImage;
        this.ingredientId = ingredientId;
        this.ingredientName = ingredientName;
        this.amount = amount;
        this.ingredientUnit = ingredientUnit;
        this.pricePerUnit = pricePerUnit;
        this.status = status;
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public int getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(int transactionId) {
        this.transactionId = transactionId;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getDishId() {
        return dishId;
    }

    public void setDishId(String dishId) {
        this.dishId = dishId;
    }

    public String getDishName() {
        return dishName;
    }

    public String getDishImage() {
        return dishImage;
    }

    public void setDishImage(String dishImage) {
        this.dishImage = dishImage;
    }

    public void setDishName(String dishName) {
        this.dishName = dishName;
    }

    public String getIngredientId() {
        return ingredientId;
    }

    public void setIngredientId(String ingredientId) {
        this.ingredientId = ingredientId;
    }

    public String getIngredientName() {
        return ingredientName;
    }

    public void setIngredientName(String ingredientName) {
        this.ingredientName = ingredientName;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getIngredientUnit() {
        return ingredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        this.ingredientUnit = ingredientUnit;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }
}
