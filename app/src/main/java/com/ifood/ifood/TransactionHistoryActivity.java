package com.ifood.ifood;

import android.content.Intent;
import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.ifood.ifood.data.ConstantStatusTransaction;
import com.ifood.ifood.data.Transaction;
import com.ifood.ifood.data.TransactionDetail;
import com.ifood.ifood.data.TransactionHistory;
import com.ifood.ifood.ultil.HttpUtils;
import com.ifood.ifood.ultil.SessionLoginController;
import com.loopj.android.http.JsonHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class TransactionHistoryActivity extends AppCompatActivity {
    private List<TransactionHistory> transactions;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        SessionLoginController session = new SessionLoginController(this);
        if (session.getEmail().isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }

        getTransactionHistory(session.getUserId());
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

    private void getTransactionHistory(String userId){
            RequestParams requestParams = new RequestParams();
            requestParams.put("userId", userId);
            HttpUtils.get(this,"/transaction/getByUserId", requestParams, new JsonHttpResponseHandler(){
                @Override
                public void onSuccess(int statusCode, Header[] headers, JSONArray response) {
                    super.onSuccess(statusCode, headers, response);
                    try{
                        transactions = new ArrayList<>();
                        if (response != null && response.length() > 0){
                            for (int i = 0; i < response.length(); i++) {
                                TransactionHistory transaction = new Gson().fromJson(response.getJSONObject(i).toString(), TransactionHistory.class);
                                List<TransactionDetail> transactionDetails = new Gson().fromJson(response.getJSONObject(i).get("shoppingLists").toString(), new TypeToken<List<TransactionDetail>>() {}.getType());
                                transaction.setTransactionDetails(transactionDetails);
                                transactions.add(transaction);
                            }
                            setTransactionLayout(transactions);
                        }
                    } catch (Exception ex){
                        ex.printStackTrace();
                    }
                }
            });
    }

    private void setTransactionLayout(List<TransactionHistory> transactions){
        LinearLayout transactionMainLayout = findViewById(R.id.transactionLayout);

        for (TransactionHistory transaction : transactions){
            LinearLayout newTransactionLayout = (LinearLayout) LayoutInflater.from(this).inflate(R.layout.layout_transaction, null);
            newTransactionLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT));
            newTransactionLayout.setTag(transaction.getId());

            TextView transactionName = newTransactionLayout.findViewWithTag("transactionName");
            transactionName.setText("Order " + transaction.getCreatedOn().toString());

            TextView transactionId = newTransactionLayout.findViewWithTag("transactionId");
            transactionId.setText(transaction.getId());

            TextView transactionCreateOn = newTransactionLayout.findViewWithTag("transactionCreateOn");
            transactionCreateOn.setText(transaction.getCreatedOn().toString());

            TextView transactionStatus = newTransactionLayout.findViewWithTag("transactionStatus");
            switch (transaction.getStatus()){
                case ConstantStatusTransaction.PENDING:
                    transactionStatus.setText("Pending");
                    transactionStatus.setTextColor(Color.parseColor("#f9c700"));
                    break;
                case ConstantStatusTransaction.SUCCESSFUL:
                default:
                    transactionStatus.setText("Successful");
                    transactionStatus.setTextColor(Color.parseColor("#08e01e"));
                    break;
                case ConstantStatusTransaction.CANCEL:
                    transactionStatus.setText("Cancel");
                    transactionStatus.setTextColor(Color.parseColor("#EA0B0B"));
                    break;
            }
            transactionMainLayout.addView(newTransactionLayout);
        }

    }

    public void viewDetailHistory(View view) {
        String id = view.getTag().toString();
        TransactionHistory transactionHistory = null;
        for (TransactionHistory transaction : transactions){
            if (transaction.getId().equals(id)){
                transactionHistory = transaction;
                break;
            }
        }
        if (transactionHistory != null){
            Intent intent = new Intent(this, TransactionHistoryDetailActivity.class);
            intent.putExtra("TRANSACTION_DETAIL", transactionHistory);
            startActivity(intent);
        }
    }
}
