package com.ifood.ifood.ultil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Category;

import java.util.ArrayList;
import java.util.List;

public class SessionCategoryController {
    private SharedPreferences prefs;

    public SessionCategoryController(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setCurrentCategory(String categoryId) {
        prefs.edit().putString("current_category", categoryId).commit();
    }

    public String getCurrentCategory() {
        String categoryId = prefs.getString("current_category","0");
        return categoryId;
    }

    public void setCategories(List<Model_Category> categories){
        Gson gson = new Gson();
        String categoryJson = gson.toJson(categories);
        prefs.edit().putString("categories_json", categoryJson).commit();
    }

    public List<Model_Category> getCategories(){
        List<Model_Category> categories = new ArrayList<>();
        Gson gson = new Gson();
        String categoryJson = prefs.getString("categories_json", null);
        categories = new Gson().fromJson(categoryJson,  new TypeToken<List<Model_Category>>(){}.getType());
        return categories;
    }

    public void clearSession(){
        prefs.edit().putString("current_category", "0").commit();
    }
}
