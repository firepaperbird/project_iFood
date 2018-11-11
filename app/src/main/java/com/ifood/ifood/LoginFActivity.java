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

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_Cookbook_Dish;
import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteCookbookDishController;
import com.ifood.ifood.ultil.SqliteUserController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.List;

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

    public void onClickLoginF(View v) {
        EditText edEmail = findViewById(R.id.edtLoginEmail);
        String email = edEmail.getText().toString().toLowerCase();
        EditText edPassword = findViewById(R.id.edtLoginPassword);
        String password = edPassword.getText().toString();
        callCheckLog(email, password);
//      caan save user data vao sqlite
    }

    private void callCheckLog(String email, String password) {

        try {
            JSONObject jsonParams = new JSONObject();
            jsonParams.put("email", email);
            jsonParams.put("password", password);
            StringEntity entity = new StringEntity(jsonParams.toString());
            HttpUtils.post(this, "/user/checklogin", entity, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        if (response != null) {

                            JSONObject serverResp = new JSONObject(response.toString());
                            responseUser = new Model_User(serverResp);
                            logUserCheckedToApp();
                        } else {
                            Toast.makeText(LoginFActivity.this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
                        }
                    } catch (JSONException e) {
                        // TODO Auto-generated catch block
                        e.printStackTrace();
                    }

                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);
                    Toast.makeText(LoginFActivity.this, "Email or password is incorrect!", Toast.LENGTH_SHORT).show();
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void callGetCookBookOfUser(final String userId) {
        try {
            RequestParams params = new RequestParams();
            params.add("userId", userId);
            HttpUtils.get(this, "/cookbook/byUserId", params, new JsonHttpResponseHandler() {
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    try {
                        List<Model_Cookbook> cbList = new Gson().fromJson(response.toString(), new TypeToken<List<Model_Cookbook>>() {}.getType());
                        if (!cbList.isEmpty()){
                            for (int i = 0; i < response.length(); i++) {
                                List<Dish> dishesInCookbook = new Gson().fromJson(response.getJSONObject(i).get("dishesInCookbook").toString(), new TypeToken<List<Dish>>() {
                                }.getType());
                                cbList.get(i).setDishesInCookBook(dishesInCookbook);
                            }
                            SqliteCookbookController sqliteCookbookController = new SqliteCookbookController(getApplicationContext());
                            SqliteCookbookDishController sqliteCookbookDishController = new SqliteCookbookDishController(getApplicationContext());
                            clearCookbookDataInSqlite(userId, sqliteCookbookController, sqliteCookbookDishController);
                            for (Model_Cookbook cb : cbList) {
                                sqliteCookbookController.insertDataIntoTable(sqliteCookbookController.getTableName(), cb);
                                for (Dish dishInCookbook : cb.getDishesInCookBook()) {
                                    Model_Cookbook_Dish cookbook_dish = new Model_Cookbook_Dish();
                                    cookbook_dish.setCookbookId(cb.getId());
                                    cookbook_dish.setDishId(dishInCookbook.getId());
                                    cookbook_dish.setDishName(dishInCookbook.getName());
                                    cookbook_dish.setDishImageLink(dishInCookbook.getImageLink());
                                    sqliteCookbookDishController.insertDataIntoTable(sqliteCookbookDishController.getTableName(), cookbook_dish);
                                }
                            }
                        }

                        startActivity(new Intent(LoginFActivity.this, mainMenuActivity.class));
                        finish();
                    } catch (JSONException e) {
                        e.printStackTrace();
                    }
                }
            });
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void clearCookbookDataInSqlite(String userId, SqliteCookbookController sqlCookbook, SqliteCookbookDishController sqlCookbookDish) {
        List<Model_Cookbook> cookbooksInSqlite = sqlCookbook.getCookbookByUserId(userId);
        for (Model_Cookbook cookbook : cookbooksInSqlite) {
            sqlCookbookDish.deleteData_From_Table(sqlCookbookDish.getTableName(),
                    "cookbookId = ?", new String[]{cookbook.getId()});
        }
        sqlCookbook.deleteData_From_Table(sqlCookbook.getTableName(), "UserId = ?", new String[]{userId});
    }

    private void logUserCheckedToApp() {
        if (responseUser != null) {
            SessionLoginController session = new SessionLoginController(this);

            session.setUserId(responseUser.getId());
            session.setName(responseUser.getName());
            session.setEmail(responseUser.getEmail());

            callGetCookBookOfUser(responseUser.getId());
        }
    }
}
