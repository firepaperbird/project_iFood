package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Point;
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

import com.ifood.ifood.ultil.SessionCategoryController;

public class categoryIfood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_category_ifood);
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
        ImageView top = findViewById(R.id.topCate);
        ImageView mid = findViewById(R.id.midCate);
        ImageView bot = findViewById(R.id.botCate);
        int categoryH = (hight-60)/3;
        top.getLayoutParams().height = categoryH;
        mid.getLayoutParams().height = categoryH;
        bot.getLayoutParams().height = categoryH;

        AbsoluteLayout.LayoutParams params = ((AbsoluteLayout.LayoutParams) findViewById(R.id.btnClose).getLayoutParams());
        params.x = (width)-(findViewById(R.id.btnClose).getWidth())+20;
//        params.y = hight-500;
//        findViewById(R.id.btnClose);
    }

    public void clickGym(View view) {
        nextToMainMenu(Integer.parseInt(getResources().getString(R.string.category_gym_food_id)));
    }

    public void clickHealthy(View view) {
        nextToMainMenu(Integer.parseInt(getResources().getString(R.string.category_healthy_food_id)));
    }

    public void clickDaily(View view) {
        nextToMainMenu(Integer.parseInt(getResources().getString(R.string.category_daily_food_id)));
    }

    public void clickClose(View view) {
        nextToMainMenu(Integer.parseInt(getResources().getString(R.string.category_none_choice_id)));
    }

    private  void nextToMainMenu(int categoryId){
        SessionCategoryController sessionCategoryController = new SessionCategoryController(this);
        sessionCategoryController.setCurrentCategory(categoryId);
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }
}
