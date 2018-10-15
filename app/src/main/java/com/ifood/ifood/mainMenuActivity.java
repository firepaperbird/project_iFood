package com.ifood.ifood;

import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.ultil.BottomNavigationViewHelper;

public class mainMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    private com.ifood.ifood.data.Menu menu;

    final int GYM_FOOD_CATEGORY_ID = 1;
    final int HEALTHY_FOOD_CATEGORY_ID = 2;
    final int DAILY_FOOD_CATEGORY_ID = 3;
    final int NONE_FOOD_CATEGORY_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setDrawerLayout();

        setListMenu();

        LinearLayout userIcon = findViewById(R.id.userIcon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mainMenuActivity.this,UserDetailActivity.class);
                startActivity(intent);
            }
        });



    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        /*switch (item.getItemId()) {
            case R.id.btnSearch:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnAbout:
                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnHelp:
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
                return true;
        }*/
        return super.onOptionsItemSelected(item);
    }

    public void moveToUserDetail(View view){
        Intent intent = new Intent(mainMenuActivity.this,UserDetailActivity.class);
        startActivity(intent);
    }

    public void moveToUserDetail(MenuItem item) {
        Intent intent = new Intent(mainMenuActivity.this,UserDetailActivity.class);
        startActivity(intent);
    }

    public void moveToMainMenuByCategoryId(View view) {
        int nav_category_id = view.getId();
        int categoryId = 0;
        switch (nav_category_id){
            case R.id.gym_category_menu:
                categoryId = GYM_FOOD_CATEGORY_ID;
                break;
            case R.id.healthy_category_menu:
                categoryId = HEALTHY_FOOD_CATEGORY_ID;
                break;
            case R.id.daily_category_menu:
                categoryId = DAILY_FOOD_CATEGORY_ID;
                break;
        }

        Intent intent = new Intent(this, mainMenuActivity.class);
        intent.putExtra("categoryId", categoryId);
        startActivity(intent);
    }

    private void setDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_layout);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        Intent intent = this.getIntent();
        int categoryId = intent.getIntExtra("categoryId", 0);
        setMainMenuByCategoryId(categoryId);
    }

    private void setMainMenuByCategoryId(int categoryId){
        TextView txtTitle = findViewById(R.id.action_bar_title);

        menu = new com.ifood.ifood.data.Menu(categoryId + "", txtTitle.getText().toString());
        switch (categoryId) {
            case GYM_FOOD_CATEGORY_ID:
                txtTitle.setText(getResources().getString(R.string.menu_gym_food));
                menu.setListDish(menu.getGymMenu());
                break;
            case HEALTHY_FOOD_CATEGORY_ID:
                txtTitle.setText(getResources().getString(R.string.menu_healthy_food));
                menu.setListDish(menu.getHealthyMenu());
                break;
            case DAILY_FOOD_CATEGORY_ID:
                txtTitle.setText(getResources().getString(R.string.menu_daily_food));
                menu.setListDish(menu.getDalyMenu());
                break;
            default:
                txtTitle.setText("Home");
                txtTitle.setText(getResources().getString(R.string.menu_daily_food));
                menu.setListDish(menu.getDalyMenu());
                break;
        }
    }

    private void setListMenu(){

        LinearLayout.LayoutParams layoutMenu = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                600);

        LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);

        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsText.setMargins(0, 0,10,20);

        LinearLayout.LayoutParams layoutParamsDivider = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                3);
        layoutParamsDivider.setMargins(0,0,0,20);

        RelativeLayout.LayoutParams layoutParamsTag = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT    ,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTag.setMargins(0, 0, 10, 0);
        layoutParamsTag.addRule(RelativeLayout.ALIGN_PARENT_END);
        layoutParamsTag.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        listMenu = findViewById(R.id.listMenu);

        for ( Dish dish: menu.getListDish()){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutMenu);
            layout.setBackground(getResources().getDrawable(dish.getImage()));

            LinearLayout layoutInfo = new LinearLayout(this);
            layoutInfo.setLayoutParams(layoutParamsInfo);
            layoutInfo.setOrientation(LinearLayout.VERTICAL);
            layoutInfo.setGravity(Gravity.BOTTOM);
            layoutInfo.setPadding(25,25,25,5);

            /*Title*/
            TextView txtTitle = new TextView(this);
            txtTitle.setLayoutParams(layoutParamsText);
            txtTitle.setTextSize(20);
            Typeface font = ResourcesCompat.getFont(this, R.font.arrusb);
            txtTitle.setTypeface(font, Typeface.BOLD);
            txtTitle.setTextColor(Color.WHITE);
            txtTitle.setText(dish.getTitle());

            /*Divider*/
            View divider = new View(this);
            divider.setLayoutParams(layoutParamsDivider);
            divider.setBackgroundColor(Color.WHITE);

            /*Tags*/
            LinearLayout tagLayout = new LinearLayout(this);
            tagLayout.setLayoutParams(layoutParamsTag);

            for (int k = 0; k < dish.getTags().size() && k < 3; k++){
                TextView tag = new TextView(this);
                tag.setLayoutParams(layoutParamsText);
                tag.setText(dish.getTags().get(k));
                tag.setBackgroundResource(R.drawable.border_tag);
                tagLayout.addView(tag);
            }

            layoutInfo.addView(txtTitle);
            layoutInfo.addView(divider);
            layoutInfo.addView(tagLayout);

            LinearLayout shadowLayout = new LinearLayout(this);
            shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    600));
            shadowLayout.setBackground(getResources().getDrawable(R.drawable.shadow));
            shadowLayout.getBackground().setAlpha(175);
            shadowLayout.setGravity(Gravity.BOTTOM);

            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT, Gravity.BOTTOM));

            frameLayout.addView(shadowLayout);
            frameLayout.addView(layoutInfo);

            layout.addView(frameLayout);

            //set Onclick event
            final String id = dish.getId();
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(mainMenuActivity.this, detailFoodActivity.class);
                    intent.putExtra("id" , id);
                    startActivity(intent);
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            listMenu.addView(layout);
        }
    }

}
