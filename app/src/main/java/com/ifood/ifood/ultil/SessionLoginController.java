package com.ifood.ifood.ultil;

import android.content.Context;
import android.content.SharedPreferences;
import android.preference.PreferenceManager;

public class SessionLoginController {
    private SharedPreferences prefs;

    public SessionLoginController(Context cntx) {
        // TODO Auto-generated constructor stub
        prefs = PreferenceManager.getDefaultSharedPreferences(cntx);
    }

    public void setUsername(String username) {
        prefs.edit().putString("username", username).commit();
    }

    public String getUsername() {
        String username = prefs.getString("username","");
        return username;
    }

    public void setEmail(String email) {
        prefs.edit().putString("email", email).commit();
    }

    public String getEmail() {
        String email = prefs.getString("email","");
        return email;
    }

    public void setUserId(int id) {
        prefs.edit().putString("userId", id + "").commit();
    }

    public int getUserId() {
        String id = prefs.getString("userId","0");
        if (id.isEmpty() || id == null){
            return -1;
        }
        return Integer.parseInt(id);
    }

    public void clearSession(){
        prefs.edit().putString("email", "").commit();
        prefs.edit().putString("username", "").commit();
        prefs.edit().putString("userId", "").commit();
    }
}
