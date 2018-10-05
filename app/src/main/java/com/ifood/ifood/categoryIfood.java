package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.View;
import android.view.ViewTreeObserver;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

public class categoryIfood extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_ifood);

        final LinearLayout layout = (LinearLayout) findViewById(R.id.layout);
        final ViewTreeObserver observer= layout.getViewTreeObserver();
        observer.addOnGlobalLayoutListener(
                new ViewTreeObserver.OnGlobalLayoutListener() {
                    @Override
                    public void onGlobalLayout() {
                        setCateImgSize(layout.getHeight());
                    }
                });


    }

    private void setCateImgSize(int hight){
        ImageView top = findViewById(R.id.topCate);
        ImageView mid = findViewById(R.id.midCate);
        ImageView bot = findViewById(R.id.botCate);
        int categoryH = (hight-60)/3;
        top.getLayoutParams().height = categoryH;
        mid.getLayoutParams().height = categoryH;
        bot.getLayoutParams().height = categoryH;
    }

    public void clickGym(View view) {

    }

    public void clickHealthy(View view) {
    }

    public void clickDaily(View view) {
    }

    public void clickClose(View view) {
        Intent intent = new Intent(categoryIfood.this, mainMenuActivity.class);
        startActivity(intent);

        finish();
    }
}
