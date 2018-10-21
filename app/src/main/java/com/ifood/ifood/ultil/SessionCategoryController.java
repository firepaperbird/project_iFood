package com.ifood.ifood.ultil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionCategoryController {
    private SharedPreferences prefs;

    public SessionCategoryController(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setCurrentCategory(int categoryId) {
        prefs.edit().putString("current_category", categoryId + "").commit();
    }

    public int getCurrentCategory() {
        String categoryId = prefs.getString("current_category","0");
        return Integer.parseInt(categoryId);
    }

    public void clearSession(){
        prefs.edit().putString("current_category", "0").commit();
    }
}
