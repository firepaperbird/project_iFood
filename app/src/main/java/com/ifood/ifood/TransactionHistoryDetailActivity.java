package com.ifood.ifood;

import android.graphics.Color;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.TextView;

public class TransactionHistoryDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_history_detail);

        boolean status = getIntent().getBooleanExtra("STATUS", false);
        TextView txtStatus = findViewById(R.id.txtStatus);
        txtStatus.setText(status ? "Successful" : "Cancel");
        txtStatus.setTextColor(status ? Color.parseColor("#08e01e") : Color.parseColor("#ea0b0b"));
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
}
