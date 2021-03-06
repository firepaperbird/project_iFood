package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.ifood.ifood.Dialog.NewCookbookDialog;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.sql.Timestamp;
import java.util.List;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class ViewCookbooksActivity extends AppCompatActivity {
    SessionLoginController session;
    private List<Model_Cookbook> listCookbook;
    ImageView animationTarget;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cookbooks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        session = new SessionLoginController(this);
        animationTarget = findViewById(R.id.syncImage);
        setCookbooksLayout();
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

    private void setCookbooksLayout(){
        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        if (session.getEmail().isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        int layoutCookbookWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int layoutCookbookHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

        LinearLayout mainLayoutCookbooks = findViewById(R.id.mainLayoutCookbook);

        listCookbook = sqlite.getCookbookByUserId(session.getUserId());
        if (listCookbook.size() != 0){
            for (int i = 0; i < listCookbook.size(); i++){
                FrameLayout newLayoutCookbook = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.cookbook_layout, null);
                LinearLayout.LayoutParams layoutParamsCookbook = new LinearLayout.LayoutParams(layoutCookbookWidth, layoutCookbookHeight);
                layoutParamsCookbook.setMargins(20, 0,0,0);
                newLayoutCookbook.setLayoutParams(layoutParamsCookbook);
                newLayoutCookbook.setTag(listCookbook.get(i).getId());

                SmartImageView newImageCookbook = newLayoutCookbook.findViewById(R.id.imageCookbook);
                newImageCookbook.setScaleType(ImageView.ScaleType.FIT_XY);
                //newImageCookbook.setImageResource(Integer.parseInt(listCookbook.get(i).getImageId()));
                if (listCookbook.get(i).getTotalRecipes() > 0){
                    newImageCookbook.setImageUrl(listCookbook.get(i).getDishesInCookBook().get(0).getImageLink());
                } else {
                    newImageCookbook.setImageResource(R.drawable.cookbook_image_blank);
                }

                TextView newTxtCookbookTitle = newLayoutCookbook.findViewById(R.id.txtCookbookTitle);
                newTxtCookbookTitle.setText(listCookbook.get(i).getName());

                TextView newTxtTotalRecipes = newLayoutCookbook.findViewById(R.id.txtTotalRecipes);
                newTxtTotalRecipes.setText(listCookbook.get(i).getTotalRecipes() + " dishes");

                int totalLayoutCookbook = mainLayoutCookbooks.getChildCount();
                if (i % 2 == 0){
                    LinearLayout layoutCookbook = mainLayoutCookbooks.findViewWithTag("layoutCookbooks_" + (totalLayoutCookbook - 1));
                    layoutCookbook.addView(newLayoutCookbook);

                } else {
                    LinearLayout newLayoutCookbooks = new LinearLayout(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,10,0,0);
                    newLayoutCookbooks.setLayoutParams(layoutParams);
                    newLayoutCookbooks.setOrientation(LinearLayout.HORIZONTAL);
                    newLayoutCookbooks.setTag("layoutCookbooks_" + totalLayoutCookbook);
                    newLayoutCookbooks.addView(newLayoutCookbook);
                    mainLayoutCookbooks.addView(newLayoutCookbooks);
                }
            }
        }
    }

    public void addNewCookBook(View view){
        NewCookbookDialog dialog = new NewCookbookDialog();
        dialog.setListCookbook(listCookbook);
        dialog.show(getFragmentManager(), "");
    }

    public void viewDetailCookbook(View view) {
        View cookbookLayout = (View) view.getParent();
        String cookbookId = cookbookLayout.getTag().toString();

        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        Model_Cookbook cookbook = sqlite.getCookbookById(cookbookId);
        if (cookbook != null){
            Intent intent = new Intent(this, DetailCookbook.class);
            intent.putExtra("COOKBOOK_INFO", cookbook);
            startActivity(intent);
        }
    }

    public void syncCookbookToServer(View view) {
        try {
            for (Model_Cookbook cookbook : listCookbook){
                cookbook.setCreateOn(null);
            }
            JSONArray cookbooksJson = new JSONArray(new Gson().toJson(listCookbook));
            StringEntity entity = new StringEntity(cookbooksJson.toString());
            HttpUtils.post(this,"/cookbook/syncCookbook?userId=" + session.getUserId(), entity, new JsonHttpResponseHandler(){
                @Override
                public void onStart() {
                    super.onStart();
                    Animation animation = AnimationUtils.loadAnimation(ViewCookbooksActivity.this, R.anim.rotate_around_center_point);
                    animationTarget.startAnimation(animation);
                }

                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);

                    Toast.makeText(ViewCookbooksActivity.this, "Sync cookbooks successful", Toast.LENGTH_SHORT).show();
                    animationTarget.clearAnimation();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, String responseString, Throwable throwable) {
                    super.onFailure(statusCode, headers, responseString, throwable);

                    int a = 1;
                }
            });
        } catch (JSONException e) {
            e.printStackTrace();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
    }
}
