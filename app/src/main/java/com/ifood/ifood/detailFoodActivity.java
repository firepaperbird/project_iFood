package com.ifood.ifood;

import android.annotation.TargetApi;
import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.PorterDuff;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.graphics.drawable.LayerDrawable;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.AddToCookbookDialog;
import com.ifood.ifood.data.Comment_User;
import com.ifood.ifood.data.ConstantStatusTransaction;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_Cookbook_Dish;
import com.ifood.ifood.data.Model_ShoppingList;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.MoveToDetailView;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteCookbookDishController;
import com.ifood.ifood.ultil.SqliteShoppingListController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class detailFoodActivity extends AppCompatActivity {

    private List<Ingredient> listIngredient = new ArrayList<Ingredient>();
    private List<Comment_User> comment_userList = new ArrayList<Comment_User>();

    private final String ADD_COOKBOOK = "Add Cookbook";

    private final String ADD_SHOPPING_LIST = "Add Shopping List";

    private Dish dish;

    private boolean haveAction = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_food);

        comment_userList.add(new Comment_User("huy","everyone like it alot", 5, "2018-10-11"));
        comment_userList.add(new Comment_User("hoang","Taste , easy to make . Would make again", 5, "2018-10-11"));
        comment_userList.add(new Comment_User("huy","so good", Float.parseFloat("4.5"), "2018-10-11"));

        setDetail();

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);


    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.detail_dish_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (haveAction){
                    Intent intent = new Intent(this, mainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();
                return true;
            case R.id.btnShare:
                Intent sharingIntent = new Intent(android.content.Intent.ACTION_SEND);
                sharingIntent.setType("text/plain");
                String shareBody = "Here is the share content body";
                sharingIntent.putExtra(android.content.Intent.EXTRA_SUBJECT, "Subject Here");
                sharingIntent.putExtra(android.content.Intent.EXTRA_TEXT, shareBody);
                startActivity(Intent.createChooser(sharingIntent, "Share your dish to"));
            default:
                return super.onOptionsItemSelected(item);
        }
    }


    @TargetApi(Build.VERSION_CODES.LOLLIPOP)
    private void setDetail(){

        LinearLayout.LayoutParams borderParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 1);
        borderParams.setMargins(20,20,20,20);

        //Set image food
        final Intent intent = getIntent();
        dish = (Dish)intent.getSerializableExtra("dish");
        final List<Dish> dishList = (List<Dish>)intent.getSerializableExtra("listDish");

        FrameLayout imgMain = findViewById(R.id.imgMain);
        BitmapDrawable image = ConfigImageQuality.getBitmapImage(getResources(), dish.getImage());
        imgMain.setBackground(image);

        LinearLayout content = findViewById(R.id.content);

        TextView nameFood = new TextView(this);
        nameFood.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        nameFood.setText(dish.getTitle());
        nameFood.setTypeface(null, Typeface.BOLD);
        nameFood.setTextColor(Color.WHITE);
        nameFood.setTextSize(18);

        RatingBar ratingFood = new RatingBar(this,null,android.R.attr.ratingBarStyleSmall);
        ratingFood.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        LayerDrawable stars = (LayerDrawable) ratingFood.getProgressDrawable();
        stars.getDrawable(0).setColorFilter(Color.WHITE, PorterDuff.Mode.SRC_ATOP);
        ratingFood.setNumStars(5);
        ratingFood.setRating(4);
        ratingFood.setClickable(false);

        LinearLayout timeCooking = new LinearLayout(this);
        LinearLayout.LayoutParams timeCookingParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
        timeCookingParams.setMargins(0,5,0,0);
        timeCooking.setLayoutParams(timeCookingParams);
        timeCooking.setOrientation(LinearLayout.HORIZONTAL);
        ImageView clock = new ImageView(this);
        clock.setImageResource(R.drawable.ic_action_access_alarm);
        clock.setLayoutParams(new LinearLayout.LayoutParams(50, 90));
        TextView txtTimeCooking = new TextView(this);
        txtTimeCooking.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
        txtTimeCooking.setText("1 hr 5 min");
        txtTimeCooking.setTextColor(Color.WHITE);
        txtTimeCooking.setPadding(5,10,0,0);

        timeCooking.addView(clock);
        timeCooking.addView(txtTimeCooking);

        content.addView(nameFood);
        content.addView(ratingFood);
        content.addView(timeCooking);

        LinearLayout detail = findViewById(R.id.layout);

        getActionButtonLayout();

        //Ingredient Title
        TextView ing = new TextView(this);
        ing.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        ing.setGravity(Gravity.CENTER_VERTICAL);
        ing.setText("Ingredients");
        ing.setTypeface(null, Typeface.BOLD);
        ing.setTextSize(18);
        ing.setPadding(30,0,0,0);
        ing.setBackgroundColor(Color.parseColor("#F7F2EA"));

        detail.addView(ing);

        //Ingredients
        Ingredient ingredients = new Ingredient();
        listIngredient = ingredients.getListIngredient();

        TableLayout ingredient = new TableLayout(this);
        ingredient.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
        ingredient.setPadding(20,10,20,10);
        TableLayout.LayoutParams tableParams = new TableLayout.LayoutParams(TableLayout.LayoutParams.WRAP_CONTENT, TableLayout.LayoutParams.WRAP_CONTENT);
        TableRow.LayoutParams rowParams = new TableRow.LayoutParams(TableRow.LayoutParams.WRAP_CONTENT, TableRow.LayoutParams.WRAP_CONTENT);
        for (Ingredient ingredientItem : listIngredient){
            TableRow tableRow = new TableRow(this);
            tableRow.setLayoutParams(tableParams);

            TextView quantity = new TextView(this);
            quantity.setLayoutParams(rowParams);
            quantity.setText(ingredientItem.getAmount());
            quantity.setPadding(30,0,0,0);
            quantity.setGravity(Gravity.CENTER_VERTICAL);
            quantity.setPadding(5,5,5,5);

            TextView unit = new TextView(this);
            unit.setLayoutParams(rowParams);
            unit.setText(ingredientItem.getUnit());
            unit.setGravity(Gravity.CENTER_VERTICAL);
            unit.setPadding(5,5,5,5);

            TextView name = new TextView(this);
            name.setLayoutParams(rowParams);
            name.setText(ingredientItem.getName());
            name.setGravity(Gravity.CENTER_VERTICAL);
            name.setPadding(5,5,125,5);

            //border
            TextView border = new TextView(this);
            border.setLayoutParams(borderParams);
            border.setBackgroundColor(Color.LTGRAY);

            tableRow.addView(name);
            tableRow.addView(quantity);
            tableRow.addView(unit);

//            detail.addView(border);
            ingredient.addView(tableRow);
        }

        detail.addView(ingredient);

        //Recipe Title
        TextView rec = new TextView(this);
        rec.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        rec.setGravity(Gravity.CENTER_VERTICAL);
        rec.setText("Step by step");
        rec.setTypeface(null, Typeface.BOLD);
        rec.setTextSize(18);
        rec.setPadding(30,0,0,0);
        rec.setBackgroundColor(Color.parseColor("#F7F2EA"));

        detail.addView(rec);

        //Recipe
        for (int i = 0; i < 3; i++){
            GridLayout recipe = new GridLayout(this);
            recipe.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            recipe.setColumnCount(2);
            recipe.setPadding(20,20,20,0);

            ImageView imgR = new ImageView(this);
            imgR.setLayoutParams(new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/6, 180));
            imgR.setScaleType(ImageView.ScaleType.FIT_XY);
            LinearLayout step = new LinearLayout(this);
            step.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 180));
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
            switch (i){
                case 0:
                    imgR.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), R.drawable.step_1));
                    stepDetail.setText("Pierce the skin of each of the little potato, ...");
                    break;
                case 1:
                    imgR.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), R.drawable.step_2));
                    stepDetail.setText("In a large non-stick skillet, heat coconut oil (1 Tbsp) ...");
                    break;
                case 2:
                    imgR.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), R.drawable.step_3));
                    stepDetail.setText("In the same skillet, heat the remaining coconut oil (1/2 tsp) ...");
                    break;
            }
            stepDetail.setGravity(Gravity.CENTER_VERTICAL);

            step.addView(stepTitle);
            step.addView(stepDetail);

            recipe.addView(imgR,0);
            recipe.addView(step,1);

            detail.addView(recipe);
        }
        //ViewMore Recipes
        Button moreRecipes = new Button(this);
        moreRecipes.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        moreRecipes.setText("VIEW ALL STEP");
        moreRecipes.setTypeface(null,Typeface.BOLD);
        moreRecipes.setBackgroundColor(Color.parseColor("#E5EAE5"));
        moreRecipes.setPadding(0,0,30,0);
        moreRecipes.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moreRecipes = new Intent(detailFoodActivity.this,ViewRecipesActivity.class);
                startActivity(moreRecipes);
            }
        });
        detail.addView(moreRecipes);

        //Review Title
        TextView reviewTitle = new TextView(this);
        reviewTitle.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 80));
        reviewTitle.setGravity(Gravity.CENTER_VERTICAL);
        reviewTitle.setText("Review("+comment_userList.size()+")");
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

        //Comment
        for(int i = 0; i < 2 && i < comment_userList.size(); i++){
            LinearLayout comment = new LinearLayout(this);
            comment.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            comment.setOrientation(LinearLayout.HORIZONTAL);
            comment.setPadding(20,0,0,0);

            TextView user_img = new TextView(this);
            user_img.setLayoutParams(new LinearLayout.LayoutParams(80, 80));
            user_img.setBackgroundResource(R.drawable.icon_user_50);

            LinearLayout userInfo = new LinearLayout(this);
            userInfo.setLayoutParams(new LinearLayout.LayoutParams(450, ViewGroup.LayoutParams.WRAP_CONTENT));
            userInfo.setOrientation(LinearLayout.VERTICAL);

            TextView username = new TextView(this);
            username.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            username.setText(comment_userList.get(i).getName());
            username.setTypeface(null,Typeface.BOLD);

            TextView time = new TextView(this);
            time.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            time.setText(comment_userList.get(i).getTime());

            RatingBar rating = new RatingBar(this,null,android.R.attr.ratingBarStyleSmall);
            rating.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            rating.setNumStars(5);
            rating.setRating(comment_userList.get(i).getStar());
            rating.setClickable(false);


            userInfo.addView(username);
            userInfo.addView(time);

            comment.addView(user_img);
            comment.addView(userInfo);
            comment.addView(rating);

            detail.addView(comment);

            TextView comment_review = new TextView(this);
            comment_review.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            comment_review.setText(comment_userList.get(i).getReview());
            comment_review.setPadding(30,0,20,30);

            detail.addView(comment_review);
        }

        //ViewMore Comment
        Button moreComment = new Button(this);
        moreComment.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));
        moreComment.setText(comment_userList.size() - 2 + " MORE REVIEWS");
        moreComment.setTypeface(null,Typeface.BOLD);
        moreComment.setBackgroundColor(Color.parseColor("#E5EAE5"));
        moreComment.setPadding(0,0,30,0);
        moreComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent moreComment = new Intent(detailFoodActivity.this,ViewCommentActivity.class);
                moreComment.putExtra("list", (Serializable) comment_userList);
                startActivity(moreComment);
            }
        });

        detail.addView(moreComment);

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

        for (final Dish dishItem:dishList) {
            if(dish.getId().intValue() == dishItem.getId().intValue()){
                continue;
            }

            FrameLayout item = new FrameLayout(this);
            item.setLayoutParams(new FrameLayout.LayoutParams(400, 400));
            BitmapDrawable imageDrawable = ConfigImageQuality.getBitmapImage(getResources(), dishItem.getImage());
            item.setBackground(imageDrawable);

            TextView itemName = new TextView(this);
            itemName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            itemName.setTextSize(15);
            itemName.setTypeface(null,Typeface.BOLD);
            itemName.setGravity(Gravity.BOTTOM);
            itemName.setTextColor(Color.LTGRAY);
            itemName.setPadding(25,0,25,25);
            String dishTitle = dishItem.getTitle().length() > 25 ? dishItem.getTitle().substring(0, 25) + "..." : dishItem.getTitle();
            itemName.setText(dishTitle);

            LinearLayout shadowLayout = new LinearLayout(this);
            shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
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

    private void getActionButtonLayout(){

        //Action button
        LinearLayout actionLayout = findViewById(R.id.btnActionLayout);

        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);


        LinearLayout cookbookLayout = new LinearLayout(this);
        cookbookLayout.setLayoutParams(new LinearLayout.LayoutParams(size.x / 2 - 30, ViewGroup.LayoutParams.MATCH_PARENT));
        cookbookLayout.setGravity(Gravity.CENTER);
        cookbookLayout.setBackground(getResources().getDrawable(R.drawable.border_btn_actions_dish_detail));
        LinearLayout orderLayout = new LinearLayout(this);
        orderLayout.setLayoutParams(new LinearLayout.LayoutParams(size.x / 2 - 30, ViewGroup.LayoutParams.MATCH_PARENT));
        orderLayout.setGravity(Gravity.CENTER);
        orderLayout.setBackground(getResources().getDrawable(R.drawable.border_btn_actions_dish_detail));

        ImageButton btnCookbook = new ImageButton(this);
        btnCookbook.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_add_cook_book));
        btnCookbook.setBackgroundColor(Color.TRANSPARENT);

        LinearLayout.LayoutParams txtActionLayout = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.MATCH_PARENT);
        txtActionLayout.setMargins(0,0,10,5);

        final TextView txtCookbook = new TextView(this);
        txtCookbook.setLayoutParams(txtActionLayout);
        txtCookbook.setText(ADD_COOKBOOK);
        txtCookbook.setGravity(Gravity.CENTER);
        txtCookbook.setTextColor(getResources().getColor(R.color.colorDarkerGray));

        cookbookLayout.addView(txtCookbook);
        cookbookLayout.addView(btnCookbook);


        ImageButton btnOrder = new ImageButton(this);
        btnOrder.setImageDrawable(getResources().getDrawable(R.drawable.ic_action_shopping_list_icon));
        btnOrder.setBackgroundColor(Color.TRANSPARENT);

        TextView txtOrder = new TextView(this);
        txtOrder.setLayoutParams(txtActionLayout);
        txtOrder.setText(ADD_SHOPPING_LIST);
        txtOrder.setGravity(Gravity.CENTER);
        txtOrder.setTextColor(getResources().getColor(R.color.colorDarkerGray));

        orderLayout.addView(txtOrder);
        orderLayout.addView(btnOrder);

        /*Add border between 2 layout*/
        LinearLayout borderLayout = new LinearLayout(this);
        LinearLayout.LayoutParams layoutParamsBorder = new LinearLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT);
        layoutParamsBorder.setMargins(15,0,15,0);
        borderLayout.setLayoutParams(layoutParamsBorder);
        borderLayout.setBackgroundColor(ContextCompat.getColor(this,R.color.colorLightGray));
        /*===========================*/

        actionLayout.addView(cookbookLayout);
        actionLayout.addView(borderLayout);
        actionLayout.addView(orderLayout);

        boolean isAddedSuccessful = getIntent().getBooleanExtra("ADD_COOKBOOK_SUCCESSFUL", false);
        if (isAddedSuccessful){
            haveAction = true;
            Toast.makeText(this, "Add into cookbook successful", Toast.LENGTH_SHORT).show();
            getIntent().removeExtra("ADD_COOKBOOK_SUCCESSFUL");
        }

        cookbookLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishIntoCookbook();
            }
        });
        btnCookbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishIntoCookbook();
            }
        });
        txtCookbook.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishIntoCookbook();
            }
        });

        orderLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishIntoShoppingList();
            }
        });
        btnOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishIntoShoppingList();
            }
        });
        txtOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                addDishIntoShoppingList();
            }
        });
    }

    private void addDishIntoCookbook(){
        SessionLoginController session = new SessionLoginController(getApplicationContext());
        if (session.getEmail().isEmpty()){
            Intent intent = new Intent(detailFoodActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        List<Model_Cookbook> listCookbook;
        if (session.getEmail().isEmpty()){
            listCookbook = new ArrayList<>();
        } else {
            listCookbook = getListCookbookByUserId(session);
        }

        AddToCookbookDialog dialog = new AddToCookbookDialog();
        dialog.insertListCookbookAndDish(listCookbook, dish);
        dialog.show(getFragmentManager(), "");
    }

    private void addDishIntoShoppingList(){
        SessionLoginController session = new SessionLoginController(this);
        SqliteShoppingListController sqlite = new SqliteShoppingListController(getApplicationContext());

        if (session.getEmail().isEmpty()){
            Intent intent = new Intent(detailFoodActivity.this, LoginActivity.class);
            startActivity(intent);
            return;
        }

        boolean isExists = sqlite.isDishExistInShoppingList(session.getUserId(), dish.getId());
        if (isExists){
            Toast.makeText(this,"This dish already have in shopping list", Toast.LENGTH_SHORT).show();
            return;
        }

        for (Ingredient ingredient : listIngredient){
            Model_ShoppingList shoppingList = new Model_ShoppingList();
            shoppingList.setUserId(session.getUserId());
            shoppingList.setDishId(dish.getId());
            shoppingList.setDishName(dish.getTitle());
            shoppingList.setDishImage(dish.getImage());
            shoppingList.setIngredientId(ingredient.getId());
            shoppingList.setIngredientName(ingredient.getName());
            shoppingList.setIngredientAmount(ingredient.getAmount());
            shoppingList.setIngredientUnit(ingredient.getUnit());
            shoppingList.setStatus(ConstantStatusTransaction.PENDING);
            sqlite.insertDataIntoTable(sqlite.getTableName(), shoppingList);
        }
        //Toast.makeText(this,"Add to shopping list successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, ShoppingList.class);
        intent.putExtra("ADD_SHOPPINGLIST_SUCCESSFUL", true);
        startActivity(intent);
    }

    private List<Model_Cookbook> getListCookbookByUserId(SessionLoginController session){
        List<Model_Cookbook> listCookbook = new ArrayList<>();
        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        listCookbook = sqlite.getCookbookByUserId(session.getUserId());
        return listCookbook;
    }
}
