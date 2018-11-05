package com.ifood.ifood.ultil;

import android.content.Context;
import android.content.Intent;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.detailFoodActivity;
import com.ifood.ifood.mainMenuActivity;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class MoveToDetailView {
    public static void moveToDetail(final Context source, final Class destination, final String dishId) {
        HttpUtils.get(source, "/dish?id=" + dishId, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    Dish dish = new Dish(response);
                    Intent intent = new Intent(source, destination);
                    intent.putExtra("dish", dish);
                    source.startActivity(intent);

                } catch (JSONException e) {
                    e.printStackTrace();
                }
            }
        });
    }
}
