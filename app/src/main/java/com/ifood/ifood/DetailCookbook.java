package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.graphics.drawable.BitmapDrawable;
import android.os.Build;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.CheckBox;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.ConfirmRemoveDishInCookbookDialog;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Menu;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_Cookbook_Dish;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.MoveToDetailView;
import com.ifood.ifood.ultil.SqliteCookbookDishController;

import java.util.ArrayList;
import java.util.List;

public class DetailCookbook extends AppCompatActivity {
    private Model_Cookbook cookbook;
    private final int LAYOUT_DISH_HEIGHT = 600;
    private boolean haveAction = false;
    private List<Dish> listDishesDetail;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_detail_cookbook);

        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayHomeAsUpEnabled(true);

        setCookbookInfo();

        setListDish();
    }

    public void moveToEditingCookbookView(View view) {
        Intent intent = new Intent(this, EditCookbook.class);
        intent.putExtra("COOKBOOK_INFO", cookbook);
        startActivity(intent);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (haveAction){
                    Intent intent = new Intent(this, ViewCookbooksActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    private void setCookbookInfo(){
        Intent intent = getIntent();
        cookbook = (Model_Cookbook) intent.getSerializableExtra("COOKBOOK_INFO");
        haveAction = intent.getBooleanExtra("UPDATE_COOKBOOK_SUCCESSFUL", false);
        boolean isAddCookbookSusccessful = intent.getBooleanExtra("ADD_COOKBOOK_SUCCESSFUL", false);
        if (isAddCookbookSusccessful){
            Toast.makeText(this, "Add cookbook successful", Toast.LENGTH_SHORT).show();
        }

        ImageView imageCookbook = findViewById(R.id.detailCookbookImage);
        imageCookbook.setScaleType(ImageView.ScaleType.FIT_CENTER);
        imageCookbook.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), Integer.parseInt(cookbook.getImageId())));

        TextView txtTitle = findViewById(R.id.detailCookbookTitle);
        txtTitle.setText(cookbook.getTitle());
    }

    private void setListDish(){
        SqliteCookbookDishController sqlite = new SqliteCookbookDishController(getApplicationContext());
        final Menu menu = new Menu();
        List<Dish> allDish = new ArrayList<>(); /*= menu.getAllDish();*/

        List<Model_Cookbook_Dish> listDishInCookbook = sqlite.getDishInCookbook(cookbook.getId());
        listDishesDetail = new ArrayList<>();

        for (Dish dish : allDish){
            for (Model_Cookbook_Dish dishInCookbook : listDishInCookbook){
                if (dish.getId().equals(dishInCookbook.getDishId())){
                    listDishesDetail.add(dish);
                }
            }
        }

        if (listDishesDetail.size() == 0){
            RelativeLayout layoutRemoveButtons = findViewById(R.id.layoutRemoveButtons);
            layoutRemoveButtons.setVisibility(View.INVISIBLE);
        } else {
            TextView txtCookbookIsEmpty = findViewById(R.id.txtCookbookIsEmpty);
            txtCookbookIsEmpty.setVisibility(View.INVISIBLE);

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

            LinearLayout listMenu = findViewById(R.id.layoutDishInCookbook);

            for (final Dish dish : listDishesDetail){
                LinearLayout layout = new LinearLayout(this);
                layout.setOrientation(LinearLayout.VERTICAL);
                layout.setLayoutParams(layoutMenu);
                BitmapDrawable image = ConfigImageQuality.getBitmapImage(this,getResources(), dish.getImageLink());
                layout.setBackground(image);  ;

                LinearLayout layoutInfo = new LinearLayout(this);
                layoutInfo.setLayoutParams(layoutParamsInfo);
                layoutInfo.setOrientation(LinearLayout.VERTICAL);
                layoutInfo.setGravity(Gravity.BOTTOM);
                layoutInfo.setPadding(25,25,25,5);

                /*Title*/
                TextView txtTitle = new TextView(this);
                txtTitle.setLayoutParams(layoutParamsText);
                txtTitle.setTextSize(15);
                Typeface font = ResourcesCompat.getFont(this, R.font.arrusb);
                txtTitle.setTypeface(font, Typeface.BOLD);
                txtTitle.setTextColor(Color.WHITE);
                txtTitle.setText(dish.getName());

                layoutInfo.addView(txtTitle);

                /*Shadow layout of dish image*/
                LinearLayout shadowLayout = new LinearLayout(this);
                shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        LAYOUT_DISH_HEIGHT));
                shadowLayout.setBackground(getResources().getDrawable(R.drawable.shadow));
                shadowLayout.getBackground().setAlpha(175);

                /*Checkbox remove dishes*/
                LinearLayout checkBoxLayout = new LinearLayout(this);
                checkBoxLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.WRAP_CONTENT));
                checkBoxLayout.setGravity(Gravity.RIGHT);
                checkBoxLayout.setPadding(0,10,10,0);

                CheckBox checkBox = new CheckBox(this);
                checkBox.setTag(dish.getId());
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    checkBox.setButtonTintList(getResources().getColorStateList(R.color.colorAccent));
                }
                checkBoxLayout.addView(checkBox);

                FrameLayout frameLayout = new FrameLayout(this);
                frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                        ViewGroup.LayoutParams.MATCH_PARENT, Gravity.BOTTOM));

                frameLayout.addView(shadowLayout);
                frameLayout.addView(layoutInfo);
                frameLayout.addView(checkBoxLayout);

                layout.addView(frameLayout);

                //set Onclick event
                layout.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        MoveToDetailView.moveToDetail(DetailCookbook.this,detailFoodActivity.class, dish.getId());
                        overridePendingTransition(android.R.anim.fade_in, android.R.anim.fade_out);
                    }
                });

                listMenu.addView(layout);
            }
        }
    }

    public void removeAllDishesInCookbook(View view) {
        List<Dish> listDishRemove = listDishesDetail;
        if (listDishRemove.size() > 0){
            removeDishes(listDishRemove, "Remove all dishes?");
        }
    }

    public void removeDishesInCookbook(View view) {
        LinearLayout listMenu = findViewById(R.id.layoutDishInCookbook);
        List<Dish> listDishRemove = new ArrayList<>();
        for (Dish dish : listDishesDetail){
            CheckBox checkBox = listMenu.findViewWithTag(dish.getId());
            if (checkBox.isChecked()){
                listDishRemove.add(dish);
            }
        }

        if (listDishRemove.size() > 0){
            removeDishes(listDishRemove, "Remove dish?");
        } else {
            Toast.makeText(this,"Please select at least 1 dish", Toast.LENGTH_SHORT).show();
        }
    }

    private void removeDishes (List<Dish> listDishRemove, String warningMessage){
        ConfirmRemoveDishInCookbookDialog dialog = new ConfirmRemoveDishInCookbookDialog();
        dialog.setListDishesRemove(cookbook, listDishRemove);
        dialog.setWarningMessage(warningMessage);
        dialog.show(getFragmentManager(), "");
    }
}
