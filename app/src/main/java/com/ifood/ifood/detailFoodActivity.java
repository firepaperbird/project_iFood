package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.LinearLayout;
import android.widget.ListView;
import android.widget.TextView;

import com.ifood.ifood.data.Dish;

import java.util.ArrayList;
import java.util.List;

public class detailFoodActivity extends AppCompatActivity {

    private List<String> listIngredient = new ArrayList<String>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        setDetail();
    }

    private void setDetail(){
        //Set image food
        Intent intent = getIntent();
        Dish dish = (Dish)intent.getSerializableExtra("dish");

        TextView imgMain = findViewById(R.id.imgMain);
        imgMain.setBackgroundResource(dish.getImage());

        LinearLayout detail = findViewById(R.id.layout);

        //Ingredient Title
        TextView ing = new TextView(this);
        ing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        ing.setGravity(Gravity.CENTER_VERTICAL);
        ing.setText("Nguyên liệu");
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
//            TextView border = new TextView(this);
//            border.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT, 1));
//            border.setBackgroundColor(Color.LTGRAY);

            ingredient.addView(quantity,0);
            ingredient.addView(name,1);
//            ingredient.addView(border,2);

            detail.addView(ingredient);
        }

        //Recipe Title
        TextView rec = new TextView(this);
        rec.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        rec.setGravity(Gravity.CENTER_VERTICAL);
        rec.setText("Các bước thực hiện");
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
    }
}
