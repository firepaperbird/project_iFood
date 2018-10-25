package com.ifood.ifood;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.preference.PreferenceManager;
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
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.MoveToDetailView;
import com.ifood.ifood.ultil.BottomNavigationViewHelper;
import com.ifood.ifood.ultil.SessionCategoryController;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteCookbookDishController;

import java.util.List;

public class mainMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    private com.ifood.ifood.data.Menu menu;

    private boolean isLogin = false;

    final int LAYOUT_DISH_HEIGHT = 1000;

    final int GYM_FOOD_CATEGORY_ID = 1;
    final int HEALTHY_FOOD_CATEGORY_ID = 2;
    final int DAILY_FOOD_CATEGORY_ID = 3;
    final int NONE_FOOD_CATEGORY_ID = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setDrawerLayout();

        setUserLoginOrSignUp();

        setListMenu();

    }

    @Override
    protected void onResume() {
        super.onResume();
        setUserLoginOrSignUp();
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
        Intent intent = new Intent();
        if (isLogin == false){
            intent = new Intent(this,LoginActivity.class);
        } else {
            intent = new Intent(this,UserDetailActivity.class);
        }
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
        SessionCategoryController sessionCategoryController = new SessionCategoryController(this);
        sessionCategoryController.setCurrentCategory(categoryId);
        Intent intent = new Intent(this, mainMenuActivity.class);
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
                txtTitle.setText(getResources().getString(R.string.menu_daily_food));
                menu.setListDish(menu.getDalyMenu());
                break;
        }
    }

    private void setListMenu(){
        SessionCategoryController sessionCategoryController = new SessionCategoryController(this);
        int categoryId = sessionCategoryController.getCurrentCategory();
        setMainMenuByCategoryId(categoryId);

        LinearLayout.LayoutParams layoutMenu = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LAYOUT_DISH_HEIGHT);

        LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

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

        for ( final Dish dish: menu.getListDish()){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutMenu);
            BitmapDrawable image = ConfigImageQuality.getBitmapImage(getResources(), dish.getImage());
            layout.setBackground(image);
            layout.setAlpha(Float.parseFloat("150"));

            LinearLayout layoutInfo = new LinearLayout(this);
            layoutInfo.setLayoutParams(layoutParamsInfo);
            layoutInfo.setOrientation(LinearLayout.VERTICAL);
            layoutInfo.setGravity(Gravity.BOTTOM);
            layoutInfo.setPadding(25,25,25,5);

            /*Title*/
            TextView txtTitle = new TextView(this);
            txtTitle.setLayoutParams(layoutParamsText);
            txtTitle.setTextSize(20);
            Typeface font = ResourcesCompat.getFont(this, R.font.courgette_regular);
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

            /*Shadow layout of dish image*/
            LinearLayout shadowLayout = new LinearLayout(this);
            shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LAYOUT_DISH_HEIGHT));
            shadowLayout.setBackground(getResources().getDrawable(R.drawable.shadow));

            /*cookbook_icon show when that dish was add into cookbook*/
            LinearLayout cookbookLayout = enableCookbookIcon();

            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM));

            frameLayout.addView(shadowLayout);

            if (isLogin) {
                SqliteCookbookDishController sqlite = new SqliteCookbookDishController(getApplicationContext());
                SessionLoginController session = new SessionLoginController(this);
                SqliteCookbookController sqliteCookbookController = new SqliteCookbookController(getApplicationContext());
                List<Model_Cookbook> listCookbook = sqliteCookbookController.getCookbookByUserId(session.getUserId());
                boolean isExist = sqlite.checkDishIsAdded(listCookbook, dish.getId());
                if (isExist){
                    frameLayout.addView(cookbookLayout);
                }
            }

            frameLayout.addView(layoutInfo);

            layout.addView(frameLayout);
            //set Onclick event
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MoveToDetailView.moveToDetail(mainMenuActivity.this,detailFoodActivity.class,dish,menu.getListDish());
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            listMenu.addView(layout);
        }
    }

    private LinearLayout enableCookbookIcon(){
        LinearLayout cookbookLayout = new LinearLayout(this);
        cookbookLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        cookbookLayout.setGravity(Gravity.RIGHT);
        cookbookLayout.setPadding(0,10,10,0);
        ImageButton cookbook_icon = new ImageButton(this);
        cookbook_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_cook_book_icon));
        cookbook_icon.setBackgroundColor(Color.TRANSPARENT);
        cookbookLayout.addView(cookbook_icon);

        return cookbookLayout;
    }

    private void setUserLoginOrSignUp (){
        SessionLoginController session = new SessionLoginController(this);
        TextView userName = findViewById(R.id.userName);
        TextView userEmail = findViewById(R.id.userEmail);
        LinearLayout btnSignin = findViewById(R.id.btn_signin_category);
        LinearLayout btnSignout = findViewById(R.id.btn_signout_category);

        LinearLayout iconUser = findViewById(R.id.userIcon);
        if (!session.getUsername().isEmpty()){
            userName.setText(session.getUsername());
            userEmail.setText(session.getEmail());
            btnSignout.setVisibility(View.VISIBLE);
            btnSignin.setVisibility(View.INVISIBLE);

            boolean isSignUpSuccessful = getIntent().getBooleanExtra("LOGIN_SUCCESSFUL", false);
            if (isSignUpSuccessful){
                Toast.makeText(this, "Sign up successful. ", Toast.LENGTH_SHORT).show();
                getIntent().removeExtra("LOGIN_SUCCESSFUL");
            }

            iconUser.setClickable(true);
            isLogin = true;
        } else {
            userName.setVisibility(View.INVISIBLE);
            userEmail.setVisibility(View.INVISIBLE);
            btnSignout.setVisibility(View.INVISIBLE);
            btnSignin.setVisibility(View.VISIBLE);
            iconUser.setClickable(false);

            isLogin = false;
        }
    }

    private LinearLayout getRandomTag(){
        LinearLayout tagLayout = new LinearLayout(this);
        tagLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        tagLayout.setGravity(Gravity.LEFT);
        tagLayout.setPadding(0,10,10,0);
        ImageButton tagImg = new ImageButton(this);
        tagImg.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_cook_book_icon));
        tagImg.setBackgroundColor(Color.TRANSPARENT);
        tagLayout.addView(tagImg);

        return tagLayout;
    }

    public void moveToLoginView(View view) {
        Intent intent = new Intent(mainMenuActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickToSignOut(View view) {
        SessionLoginController session = new SessionLoginController(this);
        session.clearSession();

        Intent intent = new Intent(mainMenuActivity.this, mainMenuActivity.class);
        startActivity(intent);
        finish();
    }

    public void moveToShoppingList(MenuItem item) {
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);

    }

    public void moveToTransactionHistory(View view) {
        Intent intent = new Intent(this, TransactionHistoryActivity.class);
        startActivity(intent);
    }

    public void moveToCookbook(MenuItem item) {
        Intent intent = new Intent(this, ViewCookbooksActivity.class);
        startActivity(intent);
    }
}
