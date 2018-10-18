package com.ifood.ifood;

import android.content.Intent;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;

public class LoginFActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_loginf);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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

    public void onClickLoginF(View v){
        EditText edEmail = findViewById(R.id.edtLoginEmail);
        String email = edEmail.getText().toString().toLowerCase();
        EditText edPassword = findViewById(R.id.edtLoginPassword);
        String password = edPassword.getText().toString();

        SqliteUserController sqlite = new SqliteUserController(getApplicationContext());
        Model_User user = sqlite.getUserByEmailAndPassword(email, password);
        if (user != null){
            SessionLoginController session = new SessionLoginController(this);

            session.setUsername(user.getUsername());
            session.setEmail(user.getEmail());

            startActivity(new Intent(this, mainMenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }

}
