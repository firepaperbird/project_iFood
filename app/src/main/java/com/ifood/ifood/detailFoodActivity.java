package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Point;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.InputType;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.FrameLayout;
import android.widget.GridLayout;
import android.widget.HorizontalScrollView;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.RatingBar;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.AddToCookbookDialog;
import com.ifood.ifood.data.Comment_User;
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
            default:
                return super.onOptionsItemSelected(item);
        }
    }


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
        ratingFood.setNumStars(5);
        ratingFood.setRating(4);
        ratingFood.setClickable(false);

        content.addView(nameFood);
        content.addView(ratingFood);

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
        for (Ingredient ingredientItem : listIngredient){
            GridLayout ingredient = new GridLayout(this);
            ingredient.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            ingredient.setColumnCount(3);

            TextView quantity = new TextView(this);
            quantity.setLayoutParams(new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/5, 60));
            quantity.setText(ingredientItem.getAmount());
            quantity.setTextSize(10);
            quantity.setPadding(30,0,0,0);
            quantity.setGravity(Gravity.CENTER_VERTICAL);

            TextView unit = new TextView(this);
            unit.setLayoutParams(new LinearLayout.LayoutParams(getWindowManager().getDefaultDisplay().getWidth()/5, 60));
            unit.setText(ingredientItem.getUnit());
            unit.setGravity(Gravity.CENTER_VERTICAL);

            TextView name = new TextView(this);
            name.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 60));
            name.setText(ingredientItem.getName());
            name.setGravity(Gravity.CENTER_VERTICAL);

            //border
            TextView border = new TextView(this);
            border.setLayoutParams(borderParams);
            border.setBackgroundColor(Color.LTGRAY);

            ingredient.addView(quantity,0);
            ingredient.addView(unit, 1);
            ingredient.addView(name,2);

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
            step.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
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
            if(dish.getId() == dishItem.getId()){
                continue;
            }

            FrameLayout item = new FrameLayout(this);
            item.setLayoutParams(new FrameLayout.LayoutParams(300, 300));
            BitmapDrawable imageDrawable = ConfigImageQuality.getBitmapImage(getResources(), dishItem.getImage());
            item.setBackground(imageDrawable);

            TextView itemName = new TextView(this);
            itemName.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            itemName.setTextSize(15);
            itemName.setTypeface(null,Typeface.BOLD);
            itemName.setGravity(Gravity.BOTTOM);
            itemName.setTextColor(Color.LTGRAY);
            itemName.setPadding(25,0,25,25);
            itemName.setText(dishItem.getTitle());

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
        cookbookLayout.setLayoutParams(new LinearLayout.LayoutParams(size.x / 2 - 20, ViewGroup.LayoutParams.MATCH_PARENT));
        cookbookLayout.setGravity(Gravity.CENTER);
        LinearLayout orderLayout = new LinearLayout(this);
        orderLayout.setLayoutParams(new LinearLayout.LayoutParams(size.x / 2, ViewGroup.LayoutParams.MATCH_PARENT));
        orderLayout.setGravity(Gravity.CENTER);

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
        borderLayout.setLayoutParams(new LinearLayout.LayoutParams(2, ViewGroup.LayoutParams.MATCH_PARENT));
        borderLayout.setBackgroundColor(Color.DKGRAY);
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
            shoppingList.setIngredientId(ingredient.getId());
            shoppingList.setIngredientName(ingredient.getName());
            shoppingList.setIngredientAmount(ingredient.getAmount());
            shoppingList.setIngredientUnit(ingredient.getUnit());

            sqlite.insertDataIntoTable(sqlite.getTableName(), shoppingList);
        }
        Toast.makeText(this,"Add to shopping list successful", Toast.LENGTH_SHORT).show();
    }

    private List<Model_Cookbook> getListCookbookByUserId(SessionLoginController session){
        List<Model_Cookbook> listCookbook = new ArrayList<>();
        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        listCookbook = sqlite.getCookbookByUserId(session.getUserId());
        return listCookbook;
    }
}
