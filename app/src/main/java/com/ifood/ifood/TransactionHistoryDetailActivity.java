package com.ifood.ifood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import com.ifood.ifood.data.ConstantStatusTransaction;
import com.ifood.ifood.data.TransactionDetail;
import com.ifood.ifood.data.TransactionHistory;
import com.ifood.ifood.ultil.ConstantManager;
import com.loopj.android.image.SmartImageView;

public class TransactionHistoryDetailActivity extends AppCompatActivity {
    private TransactionHistory transaction;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_detail);

        transaction = (TransactionHistory) getIntent().getSerializableExtra("TRANSACTION_DETAIL");
        setTransactionDetailLayout();
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

    private void setTransactionDetailLayout(){
        LinearLayout dishInfoLayout = findViewById(R.id.dishInfoLayout);

        TextView transactionId = findViewById(R.id.transactionId);
        transactionId.setText("Transaction Id: " + transaction.getId());

        TextView status = findViewById(R.id.transactionStatus);
        switch (transaction.getStatus()){
            case ConstantStatusTransaction.PENDING:
                status.setText("Pending");
                status.setTextColor(Color.parseColor("#f9c700"));
                break;
            case ConstantStatusTransaction.SUCCESSFUL:
            default:
                status.setText("Successful");
                status.setTextColor(Color.parseColor("#08e01e"));
                break;
            case ConstantStatusTransaction.CANCEL:
                status.setText("Cancel");
                status.setTextColor(Color.parseColor("#EA0B0B"));
                break;
        }

        TextView username = findViewById(R.id.transactionUserName);
        username.setText(transaction.getTransactionDetails().get(0).getUserName());

        TextView address = findViewById(R.id.transactionAddress);
        username.setText(String.format("%s %s %s", transaction.getAddress(), transaction.getDistrict(), transaction.getCity()));

        String currentDishName = transaction.getTransactionDetails().get(0).getDishName();
        LinearLayout newLayoutDishInfo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_dish_info_transaction, null);
        TableLayout tableLayout = newLayoutDishInfo.findViewWithTag("tableIngredients");
        dishInfoLayout.addView(newLayoutDishInfo);
        for (TransactionDetail transactionDetail : transaction.getTransactionDetails()){
            if (!currentDishName.equals(transactionDetail.getDishName())){
                newLayoutDishInfo = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_dish_info_transaction, null);
                tableLayout = newLayoutDishInfo.findViewWithTag("tableIngredients");
                dishInfoLayout.addView(newLayoutDishInfo);
                currentDishName = transactionDetail.getDishName();
            }

            SmartImageView dishImage = newLayoutDishInfo.findViewWithTag("imageDish");
            dishImage.setImageUrl(transactionDetail.getDishImage());

            TextView dishName = newLayoutDishInfo.findViewWithTag("dishName");
            dishName.setText(transactionDetail.getDishName());

            TableRow ingredientRow = (TableRow) LayoutInflater.from(this).inflate(R.layout.layout_row_ingredient, null);
            tableLayout.addView(ingredientRow);
            ingredientRow.setLayoutParams(new TableRow.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT));
            TextView ingredientName = ingredientRow.findViewWithTag("txtIngredientName");
            ingredientName.setText(transactionDetail.getIngredientName());

            TextView amount = ingredientRow.findViewWithTag("txtIngredientAmount");
            amount.setText(transactionDetail.getAmount());

            TextView unit = ingredientRow.findViewWithTag("txtIngredientUnit");
            try {
                unit.setText(ConstantManager.getUnitById(Integer.parseInt(transactionDetail.getUnit())));
            } catch (Exception ex){
                unit.setText("grams");
            }

            double price = 0;
            try{
                price = Double.parseDouble(transactionDetail.getPricePerUnit()) * Double.parseDouble(transactionDetail.getAmount());
            }catch (Exception ex){
                price = 1000;
            }
            TextView txtIngredientPrice = ingredientRow.findViewWithTag("txtIngredientPrice");
            txtIngredientPrice.setText(price + "$");
        }

        TextView totalPrice = findViewById(R.id.txtTotal);
        totalPrice.setText(transaction.getTotalPrice() + "");
    }
}
