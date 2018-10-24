package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.ConfirmRemoveDishShoppingListDialog;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteShoppingListController;

import java.util.ArrayList;
import java.util.List;

public class ShoppingList extends AppCompatActivity {
    private List<Dish> dishList;
    private SessionLoginController session;
    private SqliteShoppingListController sqlite;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_shopping_list);

        session = new SessionLoginController(this);
        sqlite = new SqliteShoppingListController(getApplicationContext());

        setListMenuWithIngredients();

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

    private void setListMenuWithIngredients(){
        if (session.getEmail().isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            return;
        }
        LinearLayout layoutDish = findViewById(R.id.layoutDishShoppingList);
        dishList = sqlite.getDishInShoppingList(session.getUserId());

        if (dishList.size() > 0) {
            TextView txtShoppingListIsEmpty = findViewById(R.id.txtShoppingListIsEmpty);
            txtShoppingListIsEmpty.setVisibility(View.INVISIBLE);
            for (Dish dish : dishList){
                LinearLayout newLayoutDishAndIngredient = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_dish, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newLayoutDishAndIngredient.setLayoutParams(layoutParams);
                newLayoutDishAndIngredient.setTag("Dish_" + dish.getId());

                LinearLayout layoutDishInfo = newLayoutDishAndIngredient.findViewWithTag("layoutDishInfo");
                LinearLayout.LayoutParams layoutParamsDishInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutDishInfo.setLayoutParams(layoutParamsDishInfo);

                TextView txtDishName = layoutDishInfo.findViewWithTag("txtDishName");
                txtDishName.setText(dish.getTitle());

                Button btnRemoveDish = layoutDishInfo.findViewWithTag("btnRemoveDish");
                btnRemoveDish.setId(dish.getId());
                btnRemoveDish.setTag("btnRemoveDish_" + dish.getId());

                int ingredientCount = 0;
                for (Ingredient ingredient : dish.getIngredients()){
                    ingredientCount++;
                    final LinearLayout newLayoutIngredient = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_ingredient, null);
                    LinearLayout.LayoutParams layoutParamsIngredient = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 150);
                    if (ingredientCount % 2 != 0){
                        newLayoutIngredient.setBackgroundColor(Color.parseColor("#f0eded"));
                    } else {
                        newLayoutIngredient.setBackgroundColor(Color.WHITE);
                    }
                    newLayoutIngredient.setLayoutParams(layoutParamsIngredient);
                    newLayoutIngredient.setTag("Ingredient_" + dish.getId());

                    CheckBox checkBox = newLayoutIngredient.findViewWithTag("checkboxChoice");
                    checkBox.setTag("checkbox_" + dish.getId() + "_" + ingredient.getId());
                    checkBox.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
                        @Override
                        public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                            String chkTag = buttonView.getTag().toString();
                            String edtAmountTag = chkTag.replace("checkbox_", "IngredientAmount_");
                            EditText edtAmount = newLayoutIngredient.findViewWithTag(edtAmountTag);
                            String txtUnitTag = chkTag.replace("checkbox_", "IngredientUnit_");
                            TextView txtUnit = newLayoutIngredient.findViewWithTag(txtUnitTag);
                            String txtNameTag = chkTag.replace("checkbox_", "IngredientName_");
                            TextView txtName = newLayoutIngredient.findViewWithTag(txtNameTag);
                            if (isChecked){
                                edtAmount.setEnabled(true);
                                txtUnit.setEnabled(true);
                                txtName.setEnabled(true);
                            } else {
                                edtAmount.setEnabled(false);
                                txtUnit.setEnabled(false);
                                txtName.setEnabled(false);
                            }
                        }
                    });

                    EditText edtIngredientAmount = newLayoutIngredient.findViewWithTag("edtIngredientAmount");
                    edtIngredientAmount.setText(ingredient.getAmount());
                    edtIngredientAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    edtIngredientAmount.setTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());
                    edtIngredientAmount.setFocusableInTouchMode(false);
                    edtIngredientAmount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setFocusableInTouchMode(true);
                        }
                    });

                    TextView txtIngredientUnit = newLayoutIngredient.findViewWithTag("txtIngredientUnit");
                    txtIngredientUnit.setText(ingredient.getUnit());
                    txtIngredientUnit.setTag("IngredientUnit_" + dish.getId() + "_" + ingredient.getId());

                    TextView txtIngredientName = newLayoutIngredient.findViewWithTag("txtIngredientName");
                    txtIngredientName.setText(ingredient.getName());
                    txtIngredientName.setTag("IngredientName_" + dish.getId() + "_" + ingredient.getId());

                    newLayoutDishAndIngredient.addView(newLayoutIngredient);
                }
                layoutDish.addView(newLayoutDishAndIngredient);
            }
        }


    }

    public void orderDishes(View view) {
        List<Dish> listDishOrder = new ArrayList<>();
        List<Ingredient> listIngredientsChoice = new ArrayList<>();
        LinearLayout layoutDish = findViewById(R.id.layoutDishShoppingList);
        if (dishList.size() == 0){
            Toast.makeText(this,"Shopping list is empty, please add more dishes", Toast.LENGTH_SHORT).show();
        } else {
            for (Dish dish : dishList){
                for (Ingredient ingredient : dish.getIngredients()){
                    CheckBox checkBoxChoice = layoutDish.findViewWithTag("checkbox_" + dish.getId() + "_" + ingredient.getId());
                    if (checkBoxChoice.isChecked()){
                        EditText edtAmount = layoutDish.findViewWithTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());
                        ingredient.setAmount(edtAmount.getText().toString());
                        listIngredientsChoice.add(ingredient);
                    }
                }
                dish.setIngredients(listIngredientsChoice);
                listDishOrder.add(dish);
            }
        }

        int a = 1;
    }

    public void removeDishOutShoppingList(View view) {
        int dishIdRemove = view.getId();
        ConfirmRemoveDishShoppingListDialog dialog = new ConfirmRemoveDishShoppingListDialog();
        dialog.setDishIdRemove(dishIdRemove, session.getUserId());
        dialog.show(getFragmentManager(), "");
    }

    public void addMoreDishIntoShoppingList(View view) {
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }
}
