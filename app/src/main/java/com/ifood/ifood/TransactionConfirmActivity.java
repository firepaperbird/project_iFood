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
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.RadioGroup;
import android.widget.ScrollView;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.data.Transaction;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteShoppingListController;

import java.util.List;

public class TransactionConfirmActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtPhone;
    TextView txtAddress;
    LinearLayout visaForm;
    ScrollView mScrollview;
    EditText edtCardNumber;
    EditText edtName;
    EditText edtExpDate;
    EditText edtSecCode;
    boolean visaFlag = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_confirm);

        txtName = findViewById(R.id.txtNameConfirm);
        txtPhone = findViewById(R.id.txtPhoneConfirm);
        txtAddress = findViewById(R.id.txtAddressConfirm);
        visaForm = findViewById(R.id.visaForm);
        mScrollview = findViewById(R.id.mScrollview);
        edtCardNumber = findViewById(R.id.edtCardNumber);
        edtName = findViewById(R.id.edtName);
        edtExpDate = findViewById(R.id.edtExpDate);
        edtSecCode = findViewById(R.id.edtSecCode);

        final RadioGroup rpgTrans = findViewById(R.id.rgpTransactionType);
        rpgTrans.setOnCheckedChangeListener(new RadioGroup.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(RadioGroup radioGroup, int i) {
                switch (i){
                    case R.id.rbtCOD: visaForm.setVisibility(View.GONE);
                        visaFlag = false;
                        setVisaFormToDefault();
                        break;
                    case R.id.rbtATM: visaForm.setVisibility(View.GONE);
                        visaFlag = false;
                        setVisaFormToDefault();
                        break;
                    case R.id.rbtVisa: visaForm.setVisibility(View.VISIBLE);
                        visaFlag = true;
                        break;
                }
            }
        });

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

    private void setVisaFormToDefault(){
        edtCardNumber.setText("");
        edtName.setText("");
        edtExpDate.setText("");
        edtSecCode.setText("");
    }



    private void setContent(){
        LinearLayout content = findViewById(R.id.detail);

        SqliteShoppingListController shoppingList = new SqliteShoppingListController(this);
        SessionLoginController session = new SessionLoginController(this);
        Intent intent = getIntent();

        List<Dish> dishList = (List<Dish>)intent.getSerializableExtra("LISTDISHORDER");
        Transaction transaction = (Transaction)intent.getSerializableExtra("TRANSACTION");

        txtName.setText(transaction.getName().toString());
        txtPhone.setText(transaction.getPhone().toString());
        txtAddress.setText(transaction.getAddress().toString());

        double total = 0;

        for (Dish dish:dishList) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_dish, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,20,0,20);
            layout.setLayoutParams(layoutParams);

            ImageView imageDish = layout.findViewWithTag("imageDish");
            imageDish.setImageDrawable(ConfigImageQuality.getBitmapImage(this, getResources(), dish.getImageLink()));

            TextView txtDishName = layout.findViewWithTag("txtDishName");
            txtDishName.setText(dish.getName());

            TableLayout tableLayout = new TableLayout(this);
            tableLayout.setLayoutParams(new LinearLayout.LayoutParams(LinearLayout.LayoutParams.WRAP_CONTENT, LinearLayout.LayoutParams.WRAP_CONTENT));
            for (Ingredient ingredient: dish.getIngredients()) {
                TableRow ingredientLayout = (TableRow) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_ingredient, null);
                ingredientLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, 100));

                TextView edtIngredientAmount = ingredientLayout.findViewWithTag("edtIngredientAmount");
                edtIngredientAmount.setText(ingredient.getAmount() + "");
                edtIngredientAmount.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
                edtIngredientAmount.setTag("IngredientAmount_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientUnit = ingredientLayout.findViewWithTag("txtIngredientUnit");
                txtIngredientUnit.setText(ingredient.getUnitId());
                txtIngredientUnit.setTag("IngredientUnit_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientName = ingredientLayout.findViewWithTag("txtIngredientName");
                txtIngredientName.setText(ingredient.getName());
                txtIngredientName.setTag("IngredientName_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientPrice = ingredientLayout.findViewWithTag("txtIngredientPrice");
                txtIngredientPrice.setText(5+"$");
                txtIngredientPrice.setTag("IngredientPrice_" + dish.getId() + "_" + ingredient.getId());

                tableLayout.addView(ingredientLayout);

                total += 5;
            }
            layout.addView(tableLayout);
            content.addView(layout);
        }

        TextView txtTotal = findViewById(R.id.txtTotal);
        txtTotal.setText(total + "$");
    }

    public void onCLick(View view) {
        if(visaFlag){
            if(edtCardNumber.getText().toString().equals("")){
                Toast.makeText(this, "Card number must fill", Toast.LENGTH_SHORT).show();
                return;
            }
            if(edtName.getText().toString().equals("")){
                Toast.makeText(this, "Name must fill", Toast.LENGTH_SHORT).show();
                return;
            }
            if(edtExpDate.getText().toString().equals("")){
                Toast.makeText(this, "Expiration date must fill", Toast.LENGTH_SHORT).show();
                return;
            }
            if(edtSecCode.getText().toString().equals("")){
                Toast.makeText(this, "Security code must fill", Toast.LENGTH_SHORT).show();
                return;
            }
        }
        Toast.makeText(this, "Order successful", Toast.LENGTH_SHORT).show();
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }
}
