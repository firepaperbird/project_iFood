package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.ifood.ifood.data.Transaction;

public class EditAddressActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_address);

        Intent intent = getIntent();
        Transaction transaction = (Transaction)intent.getSerializableExtra("transaction");

    }

    public void onCLick(View view) {
    }
}
