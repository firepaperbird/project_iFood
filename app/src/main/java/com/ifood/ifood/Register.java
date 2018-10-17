package com.ifood.ifood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;

public class Register extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textView = (TextView) findViewById(R.id.loginText);
        SpannableString content = new SpannableString("Already have an account ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);
    }

    public void OnClickLogin_Signup(View view) {
        startActivity(new Intent(this, LoginFActivity.class));
        finish();
    }

    public void onClickSignup(View v) {
        EditText edEmail = findViewById(R.id.edtEmail_Signup);
        String email = edEmail.getText().toString().toLowerCase();
        EditText edPassword = findViewById(R.id.edtPassword_Signup);
        String password = edPassword.getText().toString();
        EditText edConfirmPassword = findViewById(R.id.edtConfirmPassword_Signup);
        String confirmPassword = edConfirmPassword.getText().toString();
        EditText edName = findViewById(R.id.edtUsername_Signup);
        String username = edPassword.getText().toString();

        if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Password does not match the confirm password! ", Toast.LENGTH_SHORT).show();
        }
        Model_User user = new Model_User(username, email, "", "", password);
        SqliteUserController sqlite = new SqliteUserController(getApplicationContext());
        sqlite.insertDataIntoTable(sqlite.getTableName(), user);

        SessionLoginController session = new SessionLoginController(this);
        session.setUsername(username);
        session.setEmail(email);

        Intent intent = new Intent(this, mainMenuActivity.class);
        intent.putExtra("LOGIN_SUCCESSFUL", true);
        startActivity(intent);
        finish();
    }

    public void OnClickLogin(View view){
        startActivity(new Intent(this, LoginFActivity.class));
    }
}
