package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.ConfirmRemoveDishShoppingListDialog;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.ConstantManager;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteShoppingListController;
import com.loopj.android.image.SmartImageView;

import java.io.Serializable;
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
        
        boolean isAddSuccessful = getIntent().getBooleanExtra("ADD_SHOPPINGLIST_SUCCESSFUL", false);
        if (isAddSuccessful){
            Toast.makeText(this, "Add shopping list successful", Toast.LENGTH_SHORT).show();
            getIntent().removeExtra("ADD_SHOPPINGLIST_SUCCESSFUL");
        }
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
            finish();
            return;
        }
        LinearLayout layoutDish = findViewById(R.id.layoutDishShoppingList);
        dishList = sqlite.getDishInShoppingList(session.getUserId());

        if (dishList.size() > 0) {
            TextView txtShoppingListIsEmpty = findViewById(R.id.txtShoppingListIsEmpty);
            txtShoppingListIsEmpty.setVisibility(View.INVISIBLE);
            Button btnCheckout = findViewById(R.id.btnOrder);
            btnCheckout.setVisibility(View.VISIBLE);
            for (Dish dish : dishList){
                LinearLayout newLayoutDishAndIngredient = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_dish, null);
                LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                newLayoutDishAndIngredient.setLayoutParams(layoutParams);
                newLayoutDishAndIngredient.setTag("Dish_" + dish.getId());

                LinearLayout layoutDishInfo = newLayoutDishAndIngredient.findViewWithTag("layoutDishInfo");
                LinearLayout.LayoutParams layoutParamsDishInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                layoutDishInfo.setLayoutParams(layoutParamsDishInfo);

                SmartImageView imageDish = layoutDishInfo.findViewWithTag("imageDish");
                imageDish.setImageUrl(dish.getImageLink());

                TextView txtDishName = layoutDishInfo.findViewWithTag("txtDishName");
                txtDishName.setText(dish.getName());

                ImageView btnRemoveDish = layoutDishInfo.findViewWithTag("btnRemoveDish");
                btnRemoveDish.setTag("btnRemoveDish_" + dish.getId());

                int ingredientCount = 0;
                for (final Ingredient ingredient : dish.getIngredients()){
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
                            String txtPriceTag = chkTag.replace("checkbox_", "IngredientPrice_");
                            TextView txtPrice = newLayoutIngredient.findViewWithTag(txtPriceTag);
                            if (isChecked){
                                edtAmount.setEnabled(true);
                                txtUnit.setEnabled(true);
                                txtName.setEnabled(true);
                                txtPrice.setEnabled(true);
                            } else {
                                edtAmount.setEnabled(false);
                                txtUnit.setEnabled(false);
                                txtName.setEnabled(false);
                                txtPrice.setEnabled(false);
                            }
                        }
                    });

                    final EditText edtIngredientAmount = newLayoutIngredient.findViewWithTag("edtIngredientAmount");
                    Double amount = ingredient.getAmount();
                    edtIngredientAmount.setText(String.format("%s", amount.intValue()));
                    edtIngredientAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                    edtIngredientAmount.setTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());
                    edtIngredientAmount.setFocusableInTouchMode(false);
                    edtIngredientAmount.setOnClickListener(new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            v.setFocusableInTouchMode(true);
                        }
                    });
                    edtIngredientAmount.addTextChangedListener(new TextWatcher() {
                        @Override
                        public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                        }

                        @Override
                        public void onTextChanged(CharSequence s, int start, int before, int count) {
                            String tag = edtIngredientAmount.getTag().toString();
                            TextView txtPrice = newLayoutIngredient.findViewWithTag(tag.replace("IngredientAmount_","IngredientPrice_"));
                            double currentAmount = 0;
                            try {
                                currentAmount = Double.parseDouble(edtIngredientAmount.getText().toString());
                                if (currentAmount > 0){
                                    Double price = currentAmount * ingredient.getPricePerUnit();
                                    txtPrice.setText(String.format("%s $", price.intValue()));
                                } else {
                                    txtPrice.setText(String.format("%s $", 0));
                                }
                            } catch (Exception ex){
                                txtPrice.setText(String.format("%s $", 0));
                            }

                        }

                        @Override
                        public void afterTextChanged(Editable s) {

                        }
                    });

                    TextView txtIngredientUnit = newLayoutIngredient.findViewWithTag("txtIngredientUnit");
                    txtIngredientUnit.setText(ConstantManager.getUnitById(ingredient.getUnitId()));
                    txtIngredientUnit.setTag("IngredientUnit_" + dish.getId() + "_" + ingredient.getId());

                    TextView txtIngredientName = newLayoutIngredient.findViewWithTag("txtIngredientName");
                    txtIngredientName.setText(ingredient.getName());
                    txtIngredientName.setTag("IngredientName_" + dish.getId() + "_" + ingredient.getId());

                    TextView txtIngredientPrice = newLayoutIngredient.findViewWithTag("txtIngredientPrice");
                    Double price = ingredient.getPricePerUnit() * ingredient.getAmount();
                    txtIngredientPrice.setText(String.format("%s $", price.intValue()));
                    txtIngredientPrice.setTag("IngredientPrice_" + dish.getId() + "_" + ingredient.getId());

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
            return;
        } else {
            for (Dish dish : dishList){
                listIngredientsChoice = new ArrayList<Ingredient>();
                for (Ingredient ingredient : dish.getIngredients()){
                    CheckBox checkBoxChoice = layoutDish.findViewWithTag("checkbox_" + dish.getId() + "_" + ingredient.getId());
                    if (checkBoxChoice.isChecked()){
                        EditText edtAmount = layoutDish.findViewWithTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());
                        TextView txtPrice = layoutDish.findViewWithTag("IngredientPrice_" + dish.getId() + "_" + ingredient.getId());
                        ingredient.setAmount(Double.parseDouble(edtAmount.getText().toString()));
                        listIngredientsChoice.add(ingredient);
                    }
                }
                dish.setIngredients(listIngredientsChoice);
                listDishOrder.add(dish);
            }
            Intent intent = new Intent(this, TransactionAddressActivity.class);
            intent.putExtra("LISTDISHORDER", (Serializable) listDishOrder);
            startActivity(intent);
        }
    }

    public void removeDishOutShoppingList(View view) {
        String dishIdRemove = view.getTag().toString().substring(14);
        ConfirmRemoveDishShoppingListDialog dialog = new ConfirmRemoveDishShoppingListDialog();
        dialog.setDishIdRemove(dishIdRemove, session.getUserId());
        dialog.show(getFragmentManager(), "");
    }

    public void addMoreDishIntoShoppingList(View view) {
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }
}
