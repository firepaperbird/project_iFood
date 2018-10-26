package com.ifood.ifood.data;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Ingredient implements Serializable {
    private int id;
    private String name;
    private String amount;
    private String unit;
    private double price;

    public Ingredient() {
    }

    public Ingredient(int id, String name, String amount, String unit, double price) {
        this.id = id;
        this.name = name;
        this.amount = amount;
        this.unit = unit;
        this.price = price;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getUnit() {
        return unit;
    }

    public void setUnit(String unit) {
        this.unit = unit;
    }

    public List<Ingredient> getListIngredient(){
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
    }
}
