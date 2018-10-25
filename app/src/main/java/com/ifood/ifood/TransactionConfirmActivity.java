package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteShoppingListController;

import java.util.List;

public class TransactionConfirmActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_confirm);

        setContent();

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

    private void setContent(){
        LinearLayout content = findViewById(R.id.detail);

        SqliteShoppingListController shoppingList = new SqliteShoppingListController(this);
        SessionLoginController session = new SessionLoginController(this);
        Intent intent = getIntent();

        List<Dish> dishList = (List<Dish>)intent.getSerializableExtra("LISTORDERS");

        for (Dish dish:dishList) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_dish, null);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView txtDishName = layout.findViewWithTag("txtDishName");
            txtDishName.setText(dish.getTitle());

            for (Ingredient ingredient: dish.getIngredients()) {
                LinearLayout ingredientLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_dish, null);
                ingredientLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

                EditText edtIngredientAmount = ingredientLayout.findViewWithTag("edtIngredientAmount");
                edtIngredientAmount.setText(ingredient.getAmount());
                edtIngredientAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                edtIngredientAmount.setTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientUnit = ingredientLayout.findViewWithTag("txtIngredientUnit");
                txtIngredientUnit.setText(ingredient.getUnit());
                txtIngredientUnit.setTag("IngredientUnit_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientName = ingredientLayout.findViewWithTag("txtIngredientName");
                txtIngredientName.setText(ingredient.getName());
                txtIngredientName.setTag("IngredientName_" + dish.getId() + "_" + ingredient.getId());

                layout.addView(ingredientLayout);
            }
            content.addView(layout);
        }
    }
}
