package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.NestedScrollView;
import android.support.v4.widget.SlidingPaneLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.RatingBar;
import android.widget.ScrollView;
import android.widget.TextView;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.ultil.MoveToDetailView;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class detailFoodActivity extends AppCompatActivity {

    private List<String> listIngredient = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        setDetail();

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


    private void setDetail(){

        LinearLayout.LayoutParams borderParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1);
        borderParams.setMargins(20,20,20,20);

        //Set image food
        final Intent intent = getIntent();
        final Dish dish = (Dish)intent.getSerializableExtra("dish");
        final List<Dish> dishList = (List<Dish>)intent.getSerializableExtra("listDish");

        TextView imgMain = findViewById(R.id.imgMain);
        imgMain.setBackgroundResource(dish.getImage());

        LinearLayout detail = findViewById(R.id.layout);

        //Ingredient Title
        TextView ing = new TextView(this);
        ing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        ing.setGravity(Gravity.CENTER_VERTICAL);
        ing.setText("Nguyên liệu");
        ing.setTypeface(null, Typeface.BOLD);
        ing.setTextSize(18);
        ing.setPadding(30,0,0,0);
        ing.setBackgroundColor(Color.parseColor("#F7F2EA"));

        detail.addView(ing);

        //Ingredients
        for (int i = 0; i < 5; i++){
            GridLayout ingredient = new GridLayout(this);
            ingredient.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ingredient.setColumnCount(2);
            ingredient.setRowCount(2);

            TextView quantity = new TextView(this);
            quantity.setLayoutParams(new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/3, 60));
            quantity.setText(i + "a");
            quantity.setPadding(30,0,0,0);
            quantity.setGravity(Gravity.CENTER_VERTICAL);


            TextView name = new TextView(this);
            name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 60));
            name.setText(i + "b");
            name.setGravity(Gravity.CENTER_VERTICAL);

            //border
            TextView border = new TextView(this);
            border.setLayoutParams(borderParams);
            border.setBackgroundColor(Color.LTGRAY);

            ingredient.addView(quantity,0);
            ingredient.addView(name,1);

            detail.addView(ingredient);
            detail.addView(border);
        }

        //Recipe Title
        TextView rec = new TextView(this);
        rec.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        rec.setGravity(Gravity.CENTER_VERTICAL);
        rec.setText("Các bước thực hiện");
        rec.setTypeface(null, Typeface.BOLD);
        rec.setTextSize(18);
        rec.setPadding(30,0,0,0);
        rec.setBackgroundColor(Color.parseColor("#F7F2EA"));

        detail.addView(rec);

        //Recipe


        for (int i = 0; i < 10; i++){
            GridLayout recipe = new GridLayout(this);
            recipe.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            recipe.setColumnCount(2);
            recipe.setPadding(20,20,20,0);

            TextView imgR = new TextView(this);
            imgR.setLayoutParams(new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/6, 100));
            imgR.setBackgroundResource(R.drawable.mon_ca_ri_ga);

            LinearLayout step = new LinearLayout(this);
            step.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 100));
            step.setOrientation(LinearLayout.VERTICAL);
            step.setPadding(20,10,0,0);
            step.setBackgroundColor(Color.parseColor("#C6E2FF"));

            TextView stepTitle = new TextView(this);
            stepTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            stepTitle.setText("Step " + (i +1) );
            stepTitle.setTypeface(null, Typeface.BOLD);
            stepTitle.setGravity(Gravity.CENTER_VERTICAL);

            TextView stepDetail = new TextView(this);
            stepDetail.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            stepDetail.setText("Do something");
            stepDetail.setGravity(Gravity.CENTER_VERTICAL);

            step.addView(stepTitle);
            step.addView(stepDetail);

            recipe.addView(imgR,0);
            recipe.addView(step,1);

            detail.addView(recipe);
        }

        //Review Title
        TextView reviewTitle = new TextView(this);
        reviewTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        reviewTitle.setGravity(Gravity.CENTER_VERTICAL);
        reviewTitle.setText("Review");
        reviewTitle.setTypeface(null, Typeface.BOLD);
        reviewTitle.setTextSize(18);
        reviewTitle.setPadding(30,0,0,0);
        reviewTitle.setBackgroundColor(Color.parseColor("#F7F2EA"));

        detail.addView(reviewTitle);

        //Review
        //Rate
        TextView borderTop = new TextView(this);
        borderTop.setLayoutParams(borderParams);
        borderTop.setBackgroundColor(Color.LTGRAY);

        LinearLayout rateZone = new LinearLayout(this);
        rateZone.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        rateZone.setOrientation(LinearLayout.VERTICAL);
        rateZone.setGravity(Gravity.CENTER);

        RatingBar ratingBar = new RatingBar(this);
        ratingBar.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        ratingBar.setNumStars(5);
        ratingBar.setOnRatingBarChangeListener(new RatingBar.OnRatingBarChangeListener() {
            @Override
            public void onRatingChanged(RatingBar ratingBar, float v, boolean b) {
                Intent intentRate = new Intent(detailFoodActivity.this,CommentActivity.class);
                startActivity(intentRate);
                overridePendingTransition(android.R.anim.slide_in_left, android.R.anim.slide_out_right);
            }
        });

        TextView hint = new TextView(this);
        hint.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        hint.setText("Tap to rate");

        rateZone.addView(ratingBar);
        rateZone.addView(hint);

        TextView borderBottom = new TextView(this);
        borderBottom.setLayoutParams(borderParams);
        borderBottom.setBackgroundColor(Color.LTGRAY);

        detail.addView(borderTop);
        detail.addView(rateZone);
        detail.addView(borderBottom);

        //Related menu Title
        TextView menuTitle = new TextView(this);
        menuTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        menuTitle.setGravity(Gravity.CENTER_VERTICAL);
        menuTitle.setText("Related");
        menuTitle.setTypeface(null, Typeface.BOLD);
        menuTitle.setTextSize(18);
        menuTitle.setPadding(30,0,0,0);
        menuTitle.setBackgroundColor(Color.parseColor("#F7F2EA"));

        detail.addView(menuTitle);

        //Related Menu
        HorizontalScrollView container = new HorizontalScrollView(this);
        container.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

        LinearLayout menu = new LinearLayout(this);
        menu.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        menu.setOrientation(LinearLayout.HORIZONTAL);
        //menu.setPadding(10,0,0,0);

        for (final Dish dishItem:dishList) {
            if(dish.getId().equals(dishItem.getId())){
                continue;
            }

            FrameLayout item = new FrameLayout(this);
            item.setLayoutParams(new FrameLayout.LayoutParams(300, 300));
            item.setBackgroundResource(dishItem.getImage());

            TextView itemName = new TextView(this);
            itemName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            itemName.setTextSize(15);
            itemName.setTypeface(null,Typeface.BOLD);
            itemName.setGravity(Gravity.BOTTOM);
            itemName.setTextColor(Color.LTGRAY);
            itemName.setPadding(25,0,25,25);
            itemName.setText(dishItem.getTitle());

            LinearLayout shadowLayout = new LinearLayout(this);
            shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT));
            shadowLayout.setBackground(getResources().getDrawable(R.drawable.shadow));
            shadowLayout.getBackground().setAlpha(200);

            item.addView(shadowLayout);
            item.addView(itemName);

            item.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MoveToDetailView move = new MoveToDetailView();
                    move.moveToDetail(detailFoodActivity.this, detailFoodActivity.class, dishItem, dishList);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            menu.addView(item);
        }

        container.addView(menu);

        detail.addView(container);
    }
}
