package com.ifood.ifood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.LinearLayout;

public class ShoppingList extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.ShoppingList);


    }

    public void OnClickLogin(View view){
        startActivity(new Intent(this, LoginFActivity.class));
        finish();
    }

    public void OnClickSignUp_Login(View view){
        startActivity(new Intent(this, Register.class));
        finish();
    }


}
