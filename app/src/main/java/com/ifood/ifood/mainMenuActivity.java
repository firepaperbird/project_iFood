package com.ifood.ifood;

import android.app.SearchManager;
import android.content.Intent;
import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.Drawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.SearchView;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.FilterDialog;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_Category;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_Course;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.MoveToDetailView;
import com.ifood.ifood.ultil.BottomNavigationViewHelper;
import com.ifood.ifood.ultil.SessionCategoryController;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteCookbookDishController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.Serializable;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

public class mainMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    private com.ifood.ifood.data.Menu menu;
    private List<Model_Category> categories;

    private boolean isLogin = false;

    final int LAYOUT_DISH_HEIGHT = 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setDrawerLayout();

        setUserLoginOrSignUp();

        SessionCategoryController sessionCategoryController = new SessionCategoryController(this);
        String categoryId = sessionCategoryController.getCurrentCategory();
        setMainMenuByCategoryId(categoryId);

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
        getMenuInflater().inflate(R.menu.main_actions, menu);
        MenuItem searchItem = (MenuItem) (menu.findItem(R.id.btnSearch));
        if (searchItem != null) {
            SearchView searchView = (SearchView) MenuItemCompat.getActionView(searchItem);
            searchView.setOnCloseListener(new SearchView.OnCloseListener() {
                @Override
                public boolean onClose() {
                    return false;
                }
            });
            EditText searchPlate = (EditText) searchView.findViewById(android.support.v7.appcompat.R.id.search_src_text);
            searchPlate.setHint("Search");
            View searchPlateView = searchView.findViewById(android.support.v7.appcompat.R.id.search_plate);
            searchPlateView.setBackgroundColor(ContextCompat.getColor(this, android.R.color.transparent));
            // use this method for search process
            searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
                @Override
                public boolean onQueryTextSubmit(String query) {
                    // use this method when query submitted
                    Intent intent = new Intent(mainMenuActivity.this, mainMenuActivity.class);
                    intent.putExtra("SEARCH_DISH_ITEM", query);
                    intent.putExtra("SEARCH_TYPE", "Searches");
                    startActivity(intent);
                    return false;
                }

                @Override
                public boolean onQueryTextChange(String newText) {
                    // use this method for auto complete search process
                    return false;
                }
            });
            SearchManager searchManager = (SearchManager) getSystemService(SEARCH_SERVICE);
            searchView.setSearchableInfo(searchManager.getSearchableInfo(getComponentName()));
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if (drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.btnSearch:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    public void moveToUserDetail(View view) {
        Intent intent = new Intent(mainMenuActivity.this, UserDetailActivity.class);
        startActivity(intent);
    }

    public void moveToUserDetail(MenuItem item) {
        Intent intent;
        if (isLogin == false) {
            intent = new Intent(this, LoginActivity.class);
        } else {
            intent = new Intent(this, UserDetailActivity.class);
        }
        startActivity(intent);
    }

    public void moveToMainMenuByCategoryId(View view) {
        String categoryId = view.getTag().toString();
        SessionCategoryController sessionCategoryController = new SessionCategoryController(this);
        sessionCategoryController.setCurrentCategory(categoryId);
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }

    private void setDrawerLayout() {
        drawerLayout = findViewById(R.id.activity_main_drawer);
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

    private void setMainMenuByCategoryId(String categoryId) {
        TextView txtTitle = findViewById(R.id.action_bar_title);

        SessionCategoryController categorySession = new SessionCategoryController(this);
        categories = categorySession.getCategories();
        menu = new com.ifood.ifood.data.Menu(categoryId, txtTitle.getText().toString());
        LinearLayout drawerCategoryGym = findViewById(R.id.gym_category_menu);
        LinearLayout drawerCategoryHealthy = findViewById(R.id.healthy_category_menu);
        LinearLayout drawerCategoryDaily = findViewById(R.id.daily_category_menu);
        for (Model_Category category : categories) {
            if (category.getId().equals(categoryId)) {
                txtTitle.setText(category.getName());
                getListMenuFromServer(categoryId);
            }
            switch (category.getDisplayOrder()){
                case 1: drawerCategoryGym.setTag(category.getId());
                    break;
                case 2: drawerCategoryHealthy.setTag(category.getId());
                    break;
                case 3: drawerCategoryDaily.setTag(category.getId());
                    break;
                    default: drawerCategoryGym.setTag(category.getId());
                        break;
            }
        }
    }

    private void getListMenuFromServer(String categoryId) {

        HttpUtils.get(this, "/dish/getByCategory?categoryId=" + categoryId, null, new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                super.onSuccess(statusCode, headers, response);
                try {
                    List<Dish> dishList = new ArrayList<>();
                    for (int i = 0; i < response.length(); i++) {
                        JSONObject jsonObject = response.getJSONObject(i);
                        Dish dish = new Dish(jsonObject);
                        dishList.add(dish);
                    }
                    menu.setListDish(dishList);
                    setListMenu();
                } catch (Exception ex) {
                    ex.printStackTrace();
                }
            }
        });

    }

    private void setListMenu() {
        LinearLayout.LayoutParams layoutMenu = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                LAYOUT_DISH_HEIGHT);

        LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.MATCH_PARENT);

        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsText.setMargins(0, 0, 10, 10);

        LinearLayout.LayoutParams layoutParamsDivider = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                3);
        layoutParamsDivider.setMargins(0, 10, 0, 20);

        RelativeLayout.LayoutParams layoutParamsTag = new RelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTag.setMargins(0, 0, 10, 0);
        layoutParamsTag.addRule(RelativeLayout.ALIGN_PARENT_END);
        layoutParamsTag.addRule(RelativeLayout.ALIGN_PARENT_LEFT);

        LinearLayout.LayoutParams layoutParamsSearchTag = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsSearchTag.gravity = Gravity.CENTER_VERTICAL;
        layoutParamsSearchTag.setMargins(0, 0, 5, 0);

        listMenu = findViewById(R.id.listMenu);

        final String search_dish_item = getIntent().getStringExtra("SEARCH_DISH_ITEM");
        final String search_type = getIntent().getStringExtra("SEARCH_TYPE");

        List<Dish> dishListSearch = menu.getListDish();
        if (search_dish_item != null && search_type != null) {
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
                final List<String> tags = Arrays.asList(search_dish_item.split("-"));
                dishListSearch.removeIf(new Predicate<Dish>() {
                    @Override
                    public boolean test(Dish dish) {
                        boolean isFound = false;
                        if (search_type.equals("Tags")) {
                            for (Model_Course course : dish.getCourses()){
                                for (String tag : tags){
                                    isFound = tag.toLowerCase().equals(course.getName().toLowerCase());
                                }
                                if (isFound) {
                                    return false;
                                }
                            }
                            return true;
                        } else {
                            isFound = !dish.getName().toLowerCase().contains(search_dish_item.toLowerCase());
                        }
                        return isFound;
                    }
                });
                LinearLayout layout_tags_search = findViewById(R.id.layout_tags_search);
                TextView txtSearching_type = findViewById(R.id.type_of_search);
                txtSearching_type.setText(search_type);
                TextView txtSearchValue = new TextView(this);
                txtSearchValue.setLayoutParams(layoutParamsSearchTag);
                txtSearchValue.setBackgroundResource(R.drawable.border_tag);

                if (search_type.equals("Tags") && tags.size() > 1) {
                    for (String tagStr : tags) {
                        TextView anotherTag = new TextView(this);
                        anotherTag.setLayoutParams(layoutParamsSearchTag);
                        anotherTag.setBackgroundResource(R.drawable.border_tag);
                        anotherTag.setText(tagStr);
                        layout_tags_search.addView(anotherTag, 0);
                    }
                } else {
                    txtSearchValue.setText(search_dish_item);
                    layout_tags_search.addView(txtSearchValue, 0);
                }

                if (!search_type.equals("Tags")) {
                    LinearLayout add_new_tag_layout = findViewById(R.id.add_new_tag_layout);
                    layout_tags_search.removeView(add_new_tag_layout);
                } else {
                    ImageView btnAddOtherTag = findViewById(R.id.btn_add_other_tag);
                    btnAddOtherTag.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            EditText otherTag = findViewById(R.id.edt_other_tag);
                            String SearchingTags = search_dish_item + "-" + otherTag.getText().toString();
                            Intent intent = new Intent(mainMenuActivity.this, mainMenuActivity.class);
                            intent.putExtra("SEARCH_DISH_ITEM", SearchingTags);
                            intent.putExtra("SEARCH_TYPE", "Tags");
                            startActivity(intent);
                        }
                    });
                }
            }
        } else {
            LinearLayout searchingLayout = findViewById(R.id.searching_layout);
            listMenu.removeView(searchingLayout);
        }
        menu.setListDish(dishListSearch);

        if (menu.getListDish().size() > 0) {
            TextView noResultsWereFound = findViewById(R.id.txtNoResultsWereFound);
            listMenu.removeView(noResultsWereFound);
        } else {
            return;
        }

        for (final Dish dish : menu.getListDish()) {
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutMenu);
            //BitmapDrawable image = ConfigImageQuality.getBitmapImage(this, getResources(), dish.getImageLink());

            LinearLayout layoutInfo = new LinearLayout(this);
            layoutInfo.setLayoutParams(layoutParamsInfo);
            layoutInfo.setOrientation(LinearLayout.VERTICAL);
            layoutInfo.setGravity(Gravity.BOTTOM);
            layoutInfo.setPadding(25, 25, 25, 5);

            /*Title*/
            TextView txtTitle = new TextView(this);
            txtTitle.setLayoutParams(layoutParamsText);
            txtTitle.setTextSize(20);
            Typeface font = ResourcesCompat.getFont(this, R.font.courgette_regular);
            txtTitle.setTypeface(font, Typeface.BOLD);
            txtTitle.setTextColor(Color.WHITE);
            txtTitle.setText(dish.getName());

            /*Rating bar*/
            RatingBar ratingFood = new RatingBar(this, null, android.R.attr.ratingBarStyleSmall);
            ratingFood.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            LayerDrawable stars = (LayerDrawable) ratingFood.getProgressDrawable();
            stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
            ratingFood.setNumStars(5);
            ratingFood.setRating(dish.getRatingStar());
            ratingFood.setClickable(false);

            /*Divider*/
            View divider = new View(this);
            divider.setLayoutParams(layoutParamsDivider);
            divider.setBackgroundColor(Color.WHITE);

            /*Tags*/
            LinearLayout tagLayout = new LinearLayout(this);
            tagLayout.setLayoutParams(layoutParamsTag);

            for (int k = 0; k < dish.getCourses().size() && k < 3; k++) {
                TextView tag = new TextView(this);
                tag.setLayoutParams(layoutParamsText);
                tag.setText(dish.getCourses().get(k).getName());
                tag.setBackgroundResource(R.drawable.border_tag);
                tag.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        TextView tagOnclick = (TextView) v;
                        Intent intent = new Intent(mainMenuActivity.this, mainMenuActivity.class);
                        intent.putExtra("SEARCH_DISH_ITEM", tagOnclick.getText().toString());
                        intent.putExtra("SEARCH_TYPE", "Tags");
                        startActivity(intent);
                    }
                });
                tagLayout.addView(tag);
            }

            layoutInfo.addView(txtTitle);
            layoutInfo.addView(ratingFood);
            layoutInfo.addView(divider);
            layoutInfo.addView(tagLayout);

            /*Shadow layout of dish image*/
            LinearLayout shadowLayout = new LinearLayout(this);
            shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    LAYOUT_DISH_HEIGHT));
            shadowLayout.setBackground(getResources().getDrawable(R.drawable.shadow));
            shadowLayout.getBackground().setAlpha(175);

            /*cookbook_icon show when that dish was add into cookbook*/
            LinearLayout cookbookLayout = enableCookbookIcon();

            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM));

            SmartImageView image = new SmartImageView(this);
            image.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            image.setImageUrl(dish.getImageLink());
            image.setScaleType(ImageView.ScaleType.FIT_XY);

            frameLayout.addView(image);
            frameLayout.addView(shadowLayout);

            if (isLogin) {
                SqliteCookbookDishController sqlite = new SqliteCookbookDishController(getApplicationContext());
                SessionLoginController session = new SessionLoginController(this);
                SqliteCookbookController sqliteCookbookController = new SqliteCookbookController(getApplicationContext());
                List<Model_Cookbook> listCookbook = sqliteCookbookController.getCookbookByUserId(session.getUserId());
                boolean isExist = sqlite.checkDishIsAdded(listCookbook, dish.getId());
                if (isExist) {
                    frameLayout.addView(cookbookLayout);
                }
                //code cuar quan
                /*LinearLayout addDishRow = findViewById(R.id.add_dish);
                addDishRow.setVisibility(View.VISIBLE);
                ViewGroup.LayoutParams dishRowLayoutParams = addDishRow.getLayoutParams();
                final float scale = getApplicationContext().getResources().getDisplayMetrics().density;
                dishRowLayoutParams.height = (int) (48 * scale + 0.5f);
                addDishRow.setLayoutParams(dishRowLayoutParams);*/

            }

            /*Ribbon Filter*/
            /*TextView filter = new TextView(this);
            LinearLayout.LayoutParams layoutParamsFilter = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, 100);
            layoutParamsFilter.setMargins(30, 30, 0, 0);
            filter.setLayoutParams(layoutParamsFilter);
            filter.setPadding(50, 10, 20, 10);
            filter.setTypeface(null, Typeface.BOLD);
            filter.setGravity(Gravity.CENTER_VERTICAL);
            filter.setTextColor(Color.WHITE);
            switch (dish.getFilterType()) {
                case "recommend":
                    filter.setText("RECOMMENDED FOR YOU");
                    filter.setBackground(getResources().getDrawable(R.drawable.filter_tag_recommend));
                    filter.getBackground().setAlpha(150);
                    break;
                case "popular":
                    filter.setText("POPULAR");
                    filter.setBackground(getResources().getDrawable(R.drawable.filter_tag_popular));
                    filter.getBackground().setAlpha(150);
                    break;
                case "new":
                    filter.setText("NEW");
                    filter.setBackground(getResources().getDrawable(R.drawable.filter_tag_new));
                    filter.getBackground().setAlpha(150);
                    break;
                default:
                    break;
            }*/

            //frameLayout.addView(filter);

            frameLayout.addView(layoutInfo);

            layout.addView(frameLayout);
            //set Onclick event
            layout.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    MoveToDetailView.moveToDetail(mainMenuActivity.this, detailFoodActivity.class, dish.getId());
                    overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                }
            });

            listMenu.addView(layout);
        }
    }

    private LinearLayout enableCookbookIcon() {
        LinearLayout cookbookLayout = new LinearLayout(this);
        cookbookLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT));
        cookbookLayout.setGravity(Gravity.RIGHT);
        cookbookLayout.setPadding(0, 10, 10, 0);
        ImageButton cookbook_icon = new ImageButton(this);
        cookbook_icon.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_cook_book_icon));
        cookbook_icon.setBackgroundColor(Color.TRANSPARENT);
        cookbookLayout.addView(cookbook_icon);

        return cookbookLayout;
    }

    private void setUserLoginOrSignUp() {
        SessionLoginController session = new SessionLoginController(this);
        TextView userName = findViewById(R.id.userName);
        TextView userEmail = findViewById(R.id.userEmail);
        LinearLayout btnSignin = findViewById(R.id.btn_signin_category);
        LinearLayout btnSignout = findViewById(R.id.btn_signout_category);

        LinearLayout iconUser = findViewById(R.id.userIcon);
        if (!session.getName().isEmpty()) {
            userName.setText(session.getName());
            userEmail.setText(session.getEmail());
            btnSignout.setVisibility(View.VISIBLE);
            btnSignin.setVisibility(View.INVISIBLE);

            boolean isSignUpSuccessful = getIntent().getBooleanExtra("LOGIN_SUCCESSFUL", false);
            if (isSignUpSuccessful) {
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

    public void moveToLoginView(View view) {
        Intent intent = new Intent(mainMenuActivity.this, LoginActivity.class);
        startActivity(intent);
    }

    public void clickToSignOut(View view) {
        SessionLoginController session = new SessionLoginController(this);
        session.clearSession();
        Intent intent = new Intent(mainMenuActivity.this, mainMenuActivity.class);
        startActivity(intent);
        finish();;
    }

    public void moveToShoppingList(MenuItem item) {
        Intent intent = new Intent(this, ShoppingList.class);
        startActivity(intent);

    }

    public void moveToTransactionHistory(View view) {
        Intent intent = new Intent(this, TransactionHistoryActivity.class);
        startActivity(intent);
    }

    public void clickToAddNewDish(View view) {

        Intent intent = new Intent(this, CreateDishActivity.class);
        startActivity(intent);
    }

    public void moveToCookbook(MenuItem item) {
        Intent intent = new Intent(this, ViewCookbooksActivity.class);
        startActivity(intent);
    }

    public void showFilterDialog(View view) {
        FilterDialog dialog = new FilterDialog();
        dialog.show(getFragmentManager(), "");
    }
}
