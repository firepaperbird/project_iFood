package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;

import com.ifood.ifood.ultil.SessionLoginController;

public class TransactionHistoryActivity extends AppCompatActivity {

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

    public void viewDetailHistorySuccessful(View view) {
        Intent intent = new Intent(this, TransactionHistoryDetailActivity.class);
        intent.putExtra("STATUS", true);
        startActivity(intent);
    }

    public void viewDetailHistoryCancel(View view) {
        Intent intent = new Intent(this, TransactionHistoryDetailActivity.class);
        intent.putExtra("STATUS", false);
        startActivity(intent);
    }
}
