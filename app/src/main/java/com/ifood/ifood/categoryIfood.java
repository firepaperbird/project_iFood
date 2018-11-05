package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Point;
import android.net.Uri;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Display;
import android.view.View;
import android.view.ViewTreeObserver;
import android.view.Window;
import android.widget.AbsoluteLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.ifood.ifood.data.Model_Category;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionCategoryController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class categoryIfood extends AppCompatActivity {
    private List<Model_Category> categories = new ArrayList();
    SmartImageView top;
    SmartImageView mid;
    SmartImageView bot;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_category_ifood);
        getCategoryFromServer();
        Display display = getWindowManager().getDefaultDisplay();
        final Point size = new Point();
        display.getSize(size);
        final LinearLayout layout = (LinearLayout) findViewById(R.id.layout);

        final ViewTreeObserver observer= layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        setCateImgSize(size);
                    }
                });
    }

    private void setCateImgSize(Point size){
        int hight = size.y;
        int width = size.x;
        top = (SmartImageView) findViewById(R.id.topCate);
        mid = (SmartImageView) findViewById(R.id.midCate);
        bot = (SmartImageView) findViewById(R.id.botCate);
        int categoryH = (hight-60)/3;
        top.getLayoutParams().height = categoryH;
        mid.getLayoutParams().height = categoryH;
        bot.getLayoutParams().height = categoryH;

        AbsoluteLayout.LayoutParams params = ((AbsoluteLayout.LayoutParams) findViewById(R.id.btnClose).getLayoutParams());
        params.x = (width)-(findViewById(R.id.btnClose).getWidth())+20;

    }

    private void getCategoryFromServer(){
        HttpUtils.get(this,"/category/getCategories", null, new JsonHttpResponseHandler(){
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    for (int i = 0; i < response.length(); i++){
                        JSONObject jsonObject = response.getJSONObject(i);
                        Model_Category category = new Model_Category(jsonObject);
                        categories.add(category);
                    }
                    if (categories.size() > 0){
                        for (Model_Category category : categories){
                            switch (category.getDisplayOrder()){
                                case 1 : top.setImageUrl(category.getImageLink());
                                    top.setTag(category.getId());
                                    break;
                                case 2 : mid.setImageUrl(category.getImageLink());
                                    mid.setTag(category.getId());
                                    break;
                                case 3 : bot.setImageUrl(category.getImageLink());
                                    bot.setTag(category.getId());
                                    break;
                            }
                        }
                    }
                } catch (Exception ex){
                    ex.printStackTrace();
                }
            }

            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                super.onFailure(statusCode, headers, throwable, errorResponse);

                int a = 1;
            }
        });
    }

    public void clickGym(View view) {
        nextToMainMenu(view.getTag().toString());
    }

    public void clickHealthy(View view) {
        nextToMainMenu(view.getTag().toString());
    }

    public void clickDaily(View view) {
        nextToMainMenu(view.getTag().toString());
    }

    public void clickClose(View view) {
        nextToMainMenu(view.getTag().toString());
    }

    private  void nextToMainMenu(String categoryId){
        SessionCategoryController sessionCategoryController = new SessionCategoryController(this);
        sessionCategoryController.setCurrentCategory(categoryId);
        Intent intent = new Intent(this, mainMenuActivity.class);
        intent.putExtra("CATEGORIES", (Serializable) categories);
        startActivity(intent);
    }
}
