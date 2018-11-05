package com.ifood.ifood;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.text.SpannableString;
import android.text.style.UnderlineSpan;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class Register extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    private Model_User responseUser = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        TextView textView = (TextView) findViewById(R.id.loginText);
        SpannableString content = new SpannableString("Already have an account ?");
        content.setSpan(new UnderlineSpan(), 0, content.length(), 0);
        textView.setText(content);

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

    public void onClickSignup(View v) {
        EditText edEmail = findViewById(R.id.edtEmail_Signup);
        String email = edEmail.getText().toString().toLowerCase();
        EditText edPassword = findViewById(R.id.edtPassword_Signup);
        String password = edPassword.getText().toString();
        EditText edConfirmPassword = findViewById(R.id.edtConfirmPassword_Signup);
        String confirmPassword = edConfirmPassword.getText().toString();
        EditText edName = findViewById(R.id.edtUsername_Signup);
        String username = edName.getText().toString();

        if (!password.equals(confirmPassword)){
            Toast.makeText(this, "Password does not match the confirm password! ", Toast.LENGTH_SHORT).show();
        }
        Model_User user = new Model_User(username, email, password);
        callRegisAPI(user);

    }
    private void callRegisAPI(Model_User ur){

        try {
            JSONObject jsonParams = ur.toJSON();
            StringEntity entity = new StringEntity(jsonParams.toString());
            HttpUtils.put(this,"/user", entity,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONObject serverResp = new JSONObject(response.toString());
                        setUserToSession(new Model_User(serverResp));
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }
    public void OnClickLogin(View view){
        startActivity(new Intent(this, LoginFActivity.class));
    }

    private void setUserToSession(Model_User newUser){
        SqliteUserController sqlite = new SqliteUserController(getApplicationContext());
        sqlite.insertDataIntoTable(sqlite.getTableName(), newUser);

        SessionLoginController session = new SessionLoginController(this);
        session.setUserId(newUser.getId());
        session.setName(newUser.getName());
        session.setEmail(newUser.getEmail());

        Intent intent = new Intent(this, mainMenuActivity.class);
        intent.putExtra("LOGIN_SUCCESSFUL", true);
        startActivity(intent);
        finish();
    }
}
