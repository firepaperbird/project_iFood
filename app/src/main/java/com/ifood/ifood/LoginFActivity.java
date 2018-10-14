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

public class LoginFActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginf);
    }

    public void onClickLoginF(View v){
        EditText edEmail = findViewById(R.id.edtEmail);
        String email = edEmail.getText().toString();
        EditText edPassword = findViewById(R.id.edtPassword);
        String password = edPassword.getText().toString();

        if (email.equals("tuananh@gmail.com") && password.equals("123456")){
            startActivity(new Intent(this, mainMenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Login fail ", Toast.LENGTH_SHORT).show();
        }
    }

}
