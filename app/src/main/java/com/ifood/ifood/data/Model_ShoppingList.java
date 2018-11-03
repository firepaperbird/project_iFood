package com.ifood.ifood.data;

public class Model_ShoppingList {
    private Integer Id;
    private int TransactionId;
    private String UserId;
    private int DishId;
    private String DishName;
    private int DishImage;
    private int IngredientId;
    private String IngredientName;
    private String IngredientAmount;
    private String IngredientUnit;
    private int Status;

    public Model_ShoppingList() {
    }

    public Model_ShoppingList(int transactionId, String userId, int dishId, String dishName, int dishImage, int ingredientId, String ingredientName, String ingredientAmount, String ingredientUnit, int status) {
        TransactionId = transactionId;
        UserId = userId;
        DishId = dishId;
        DishName = dishName;
        DishImage = dishImage;
        IngredientId = ingredientId;
        IngredientName = ingredientName;
        IngredientAmount = ingredientAmount;
        IngredientUnit = ingredientUnit;
        Status = status;
    }

    public Integer getId() {
        return Id;
    }

    public void setId(Integer id) {
        Id = id;
    }

    public int getTransactionId() {
        return TransactionId;
    }

    public void setTransactionId(int transactionId) {
        TransactionId = transactionId;
    }

    public String getUserId() {
        return UserId;
    }

    public void setUserId(String userId) {
        UserId = userId;
    }

    public int getDishId() {
        return DishId;
    }

    public void setDishId(int dishId) {
        DishId = dishId;
    }

    public String getDishName() {
        return DishName;
    }

    public int getDishImage() {
        return DishImage;
    }

    public void setDishImage(int dishImage) {
        DishImage = dishImage;
    }

    public void setDishName(String dishName) {
        DishName = dishName;
    }

    public int getIngredientId() {
        return IngredientId;
    }

    public void setIngredientId(int ingredientId) {
        IngredientId = ingredientId;
    }

    public String getIngredientName() {
        return IngredientName;
    }

    public void setIngredientName(String ingredientName) {
        IngredientName = ingredientName;
    }

    public String getIngredientAmount() {
        return IngredientAmount;
    }

    public void setIngredientAmount(String ingredientAmount) {
        IngredientAmount = ingredientAmount;
    }

    public String getIngredientUnit() {
        return IngredientUnit;
    }

    public void setIngredientUnit(String ingredientUnit) {
        IngredientUnit = ingredientUnit;
    }

    public int getStatus() {
        return Status;
    }

    public void setStatus(int status) {
        Status = status;
    }
}
