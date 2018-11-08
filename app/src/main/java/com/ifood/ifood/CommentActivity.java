package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.MoveToDetailView;
import com.ifood.ifood.ultil.SessionLoginController;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.HashMap;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class CommentActivity extends AppCompatActivity {
    private String dishId;
    private EditText edtComment;
    private float rating = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_comment);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        edtComment = (EditText) findViewById(R.id.txtCommentInput);
        rating = getIntent().getFloatExtra("rating", 0);
        dishId = getIntent().getStringExtra("dishId");
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

    public void submitReview(View view) {
        SessionLoginController session = new SessionLoginController(this);

        Map<String, String> requestJson = new HashMap<>();
        requestJson.put("userId", session.getUserId());
        requestJson.put("dishId", dishId);
        requestJson.put("comment", edtComment.getText().toString());
        requestJson.put("rate", Math.floor(rating) + "");

        try {
            JSONObject jsonObject = new JSONObject(new Gson().toJson(requestJson, HashMap.class));
            StringEntity entity = new StringEntity(jsonObject.toString());
            HttpUtils.post(this, "/review", entity, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    Toast.makeText(CommentActivity.this, "Submit review successful", Toast.LENGTH_SHORT).show();
                    MoveToDetailView.moveToDetail(CommentActivity.this, detailFoodActivity.class, dishId);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
