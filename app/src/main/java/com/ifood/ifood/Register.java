package com.ifood.ifood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

public class Register extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);
    }

    public void OnClickLogin_Signup(View view) {
        startActivity(new Intent(this, LoginFActivity.class));
        finish();
    }

    public void onClickSignup(View v) {
        EditText edEmail = findViewById(R.id.edtEmail_Signup);
        String email = edEmail.getText().toString();
        EditText edPassword = findViewById(R.id.edtPassword_Signup);
        String password = edPassword.getText().toString();
        EditText edName = findViewById(R.id.edtName);
        String name = edPassword.getText().toString();

        Toast.makeText(this, "Success ", Toast.LENGTH_SHORT).show();

        startActivity(new Intent(this, mainMenuActivity.class));
        finish();
    }

}
