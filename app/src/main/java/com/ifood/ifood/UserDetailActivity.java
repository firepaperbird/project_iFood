package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.NewCookbookDialog;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteDataController;
import com.ifood.ifood.ultil.SqliteUserController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean isEditSuccessful = getIntent().getBooleanExtra("EDIT_SUCCESSFUL", false);
        if (isEditSuccessful){
            Toast.makeText(this, "Edit successful. ", Toast.LENGTH_SHORT).show();
            getIntent().removeExtra("EDIT_SUCCESSFUL");
        }

        SqliteUserController sqliteControl = new SqliteUserController(getApplicationContext());
        Model_User user = null;
        try {
            SessionLoginController session = new SessionLoginController(this);
            user = sqliteControl.getUserByEmail(session.getEmail());
        }catch (Exception e) {
            e.printStackTrace();
        }

        TextView txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(user.getName());
        TextView txtEmail = findViewById(R.id.txtEmail);
        txtEmail.setText(user.getEmail());

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
