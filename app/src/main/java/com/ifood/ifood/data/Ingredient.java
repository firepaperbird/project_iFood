package com.ifood.ifood.data;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;

public class Ingredient implements Serializable {
    private String id;
    private Integer typeId;
    private String name;
    private String description;
    private double amount;
    private int unitId;
    private double pricePerUnit;
    private Boolean isDelete;

    public Ingredient() {
    }

    public Ingredient(JSONObject jsonObject) throws JSONException {
        this.id = jsonObject.getString("id");
        this.typeId = jsonObject.getInt("typeId");
        this.name = jsonObject.getString("name");
        this.description = jsonObject.getString("description");
        this.amount = jsonObject.getDouble("amount");
        this.unitId = jsonObject.getInt("unitId");
        this.pricePerUnit = jsonObject.getDouble("pricePerUnit");
        this.isDelete = jsonObject.getBoolean("delete");
    }

    public double getPricePerUnit() {
        return pricePerUnit;
    }

    public void setPricePerUnit(double pricePerUnit) {
        this.pricePerUnit = pricePerUnit;
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

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public int getUnitId() {
        return unitId;
    }

    public void setUnitId(int unitId) {
        this.unitId = unitId;
    }

    /*public List<Ingredient> getListIngredient(){
        List<Ingredient> ingredientList = new ArrayList<>();
        Ingredient ingredient = new Ingredient(1, "potatoes", "454", "grams", 45000);
        ingredientList.add(ingredient);
        ingredient = new Ingredient(2,"garlic powder", "0.5", "tps", 30000);
        ingredientList.add(ingredient);
        ingredient = new Ingredient(3,"milk", "100", "ml", 16000);
        ingredientList.add(ingredient);
        ingredient = new Ingredient(4,"pork", "200", "grams",27000);
        ingredientList.add(ingredient);

        return ingredientList;
    }*/
}
