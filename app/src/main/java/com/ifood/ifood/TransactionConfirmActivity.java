package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.RelativeLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.data.Transaction;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteShoppingListController;

import java.util.ArrayList;
import java.util.List;

public class TransactionConfirmActivity extends AppCompatActivity {

//    Ingredient ingredient = new Ingredient(1, "Hanh cu", "1", "qua", 20000);
//    List<Ingredient> ingredientList = new ArrayList<Ingredient>();
//    Dish dish = new Dish(1, "Mon ngon chua ngot ", " ngon", "huy", R.drawable.black_bean_bowl, null);
//    List<Dish> dishList = new ArrayList<Dish>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_confirm);

//        ingredientList = ingredient.getListIngredient();
//        dish.setIngredients(ingredientList);
//        dishList.add(dish);

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

        List<Dish> dishList = (List<Dish>)intent.getSerializableExtra("LISTDISHORDER");
        intent.removeExtra("LISTDISHORDER");

        double total = 0;

        for (Dish dish:dishList) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_dish, null);
            layout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));

            TextView txtDishName = layout.findViewWithTag("txtDishName");
            txtDishName.setText(dish.getTitle());



            for (Ingredient ingredient: dish.getIngredients()) {
                RelativeLayout ingredientLayout = (RelativeLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_ingredient, null);
                ingredientLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                TextView edtIngredientAmount = ingredientLayout.findViewWithTag("edtIngredientAmount");
                edtIngredientAmount.setText(ingredient.getAmount());
                edtIngredientAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                edtIngredientAmount.setTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientUnit = ingredientLayout.findViewWithTag("txtIngredientUnit");
                txtIngredientUnit.setText(ingredient.getUnit());
                txtIngredientUnit.setTag("IngredientUnit_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientName = ingredientLayout.findViewWithTag("txtIngredientName");
                txtIngredientName.setText(ingredient.getName());
                txtIngredientName.setTag("IngredientName_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientPrice = ingredientLayout.findViewWithTag("txtIngredientPrice");
                txtIngredientPrice.setText(5+"$");
                txtIngredientPrice.setTag("IngredientPrice_" + dish.getId() + "_" + ingredient.getId());

                layout.addView(ingredientLayout);

                total += 5;
            }
            content.addView(layout);
        }

        TextView txtTotal = findViewById(R.id.txtTotal);
        txtTotal.setText(total + "$");
    }

    public void onCLick(View view) {
        Toast.makeText(this, "Order successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }
}
