package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SqliteDataController;
import com.ifood.ifood.ultil.SqliteUserController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.sql.SQLException;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        SqliteUserController sqliteControl = new SqliteUserController(getApplicationContext());
        Model_User user = null;
        try {
            user = sqliteControl.getUserById("1");
        }catch (Exception e) {
            e.printStackTrace();
        } finally {
            sqliteControl.close();
        }

        TextView txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(user.getUsername());

        Button editProfileBtn = findViewById(R.id.btnEditProfile);
        final Model_User finalUser = user;
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, EditProfileActivity.class);
                intent.putExtra("USERINFO", finalUser);
                startActivity(intent);
            }
        });
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }
}
