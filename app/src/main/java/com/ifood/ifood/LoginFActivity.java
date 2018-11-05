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
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONException;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class LoginFActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    private Model_User responseUser = null;
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
        callCheckLog(email,password);
        SqliteUserController sqlite = new SqliteUserController(getApplicationContext());
//      caan save user data vao sqlite
    }

    private void callCheckLog(String email, String password){

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("email",email);
            jsonParams.put("password",password);
            StringEntity entity = new StringEntity(jsonParams.toString());
            HttpUtils.post(this,"/user/checklogin", entity,new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        JSONObject serverResp = new JSONObject(response.toString());
                        responseUser = new Model_User(serverResp);
                        logUserCheckedToApp();
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }
                }
            });
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    private void logUserCheckedToApp(){
        if (responseUser != null){
            SessionLoginController session = new SessionLoginController(this);

            session.setUserId(responseUser.getId());
            session.setName(responseUser.getName());
            session.setEmail(responseUser.getEmail());

            startActivity(new Intent(this, mainMenuActivity.class));
            finish();
        }else{
            Toast.makeText(this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
        }
    }
}
