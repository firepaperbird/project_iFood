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

import com.google.gson.Gson;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Ingredient;
import com.ifood.ifood.data.Model_ShoppingList;
import com.ifood.ifood.data.Transaction;
import com.ifood.ifood.ultil.ConfigImageQuality;
import com.ifood.ifood.ultil.ConstantManager;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteShoppingListController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.image.SmartImageView;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import cz.msebera.android.httpclient.Header;
import cz.msebera.android.httpclient.entity.StringEntity;

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

    private List<Dish> dishList;
    private Transaction transaction;
    private double total;

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
                    /*case R.id.rbtATM: visaForm.setVisibility(View.GONE);
                        visaFlag = false;
                        setVisaFormToDefault();
                        break;
                    case R.id.rbtVisa: visaForm.setVisibility(View.VISIBLE);
                        visaFlag = true;
                        break;*/
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

        dishList = (List<Dish>)intent.getSerializableExtra("LISTDISHORDER");
        transaction = (Transaction)intent.getSerializableExtra("TRANSACTION");

        txtName.setText(transaction.getName());
        txtPhone.setText(transaction.getPhone());
        txtAddress.setText(transaction.getAddress() + ", " + transaction.getDistrict() + ", " + transaction.getCity());

        total = 0;

        for (Dish dish:dishList) {
            LinearLayout layout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction_detail_dish, null);
            LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
            layoutParams.setMargins(0,20,0,20);
            layout.setLayoutParams(layoutParams);

            SmartImageView imageDish = layout.findViewWithTag("imageDish");
            imageDish.setImageUrl(dish.getImageLink());

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
                txtIngredientUnit.setText(ConstantManager.getUnitById(ingredient.getUnitId()));
                txtIngredientUnit.setTag("IngredientUnit_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientName = ingredientLayout.findViewWithTag("txtIngredientName");
                txtIngredientName.setText(ingredient.getName());
                txtIngredientName.setTag("IngredientName_" + dish.getId() + "_" + ingredient.getId());

                TextView txtIngredientPrice = ingredientLayout.findViewWithTag("txtIngredientPrice");
                Double price = ingredient.getPricePerUnit() * ingredient.getAmount();
                txtIngredientPrice.setText(String.format("%s $", price.intValue()));
                txtIngredientPrice.setTag("IngredientPrice_" + dish.getId() + "_" + ingredient.getId());

                tableLayout.addView(ingredientLayout);

                total += price;
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


        try {
            final SessionLoginController session = new SessionLoginController(this);
            Map<String, String> map = new HashMap<>();
            map.put("userId",  session.getUserId());
            map.put("transaction", new JSONObject(new Gson().toJson(transaction)).toString());
            map.put("totalPrice", total + "");
            List<Model_ShoppingList> shoppingLists = new ArrayList<>();
            for (Dish dish : dishList){
                for (Ingredient ingredient : dish.getIngredients()){
                    Model_ShoppingList shoppingList = new Model_ShoppingList();
                    shoppingList.setUserId(session.getUserId());
                    shoppingList.setDishId(dish.getId());
                    shoppingList.setIngredientId(ingredient.getId());
                    shoppingList.setAmount(ingredient.getAmount() + "");
                    shoppingLists.add(shoppingList);
                }
            }
            map.put("shoppingLists", new JSONArray(new Gson().toJson(shoppingLists)).toString());
            JSONObject jsonObject = new JSONObject(new Gson().toJson(map, HashMap.class));
            StringEntity entity = new StringEntity(jsonObject.toString(), "UTF-8");
            HttpUtils.put(this, "/transaction", entity, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONObject response) {
                    super.onSuccess(statusCode, headers, response);

                    SqliteShoppingListController sqlite = new SqliteShoppingListController(getApplicationContext());
                    sqlite.deleteData_From_Table(sqlite.getTableName(), "userId = ?", new String[]{session.getUserId()});

                    Intent intent = new Intent(TransactionConfirmActivity.this, mainMenuActivity.class);
                    startActivity(intent);
                    finish();
                    Toast.makeText(TransactionConfirmActivity.this, "Order successful", Toast.LENGTH_SHORT).show();
                }

                @Override
                public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                    super.onFailure(statusCode, headers, throwable, errorResponse);

                    int a = 1;
                }
            });

        } catch (JSONException e) {
            e.printStackTrace();
        }
    }
}
