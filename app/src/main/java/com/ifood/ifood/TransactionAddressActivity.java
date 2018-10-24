package com.ifood.ifood;

import android.graphics.Color;
import android.graphics.Typeface;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.DeleveryInfo;
import com.ifood.ifood.data.Transaction;

import java.util.ArrayList;
import java.util.List;

public class TransactionAddressActivity extends AppCompatActivity {

    Transaction transaction = new Transaction();
    List<DeleveryInfo> deleveryInfoList = new ArrayList<DeleveryInfo>();
    RadioButton previousChecked;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_address);

        RadioButton radio1 = findViewById(R.id.radioButton1);


        previousChecked = radio1;

        radio1.setChecked(true);

        RadioButton.OnClickListener onClick = new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                RadioButton radioButton = (RadioButton)view;
                if(!radioButton.isChecked())
                    return;
                if (previousChecked != null) {
                    previousChecked.setChecked(false);
                    previousChecked = radioButton;
                }else {
                    previousChecked = radioButton;
                }
            }
        };

        radio1.setOnClickListener(onClick);


        deleveryInfoList.add(new DeleveryInfo("Bùi Quang Huy","01665169262", "193, Phạm Văn Hai "));
    }

    private void setContent(){
        RadioGroup radioGroup = findViewById(R.id.addressInfos);

    }


    public void onClick(View view) {
        Toast.makeText(this, "aaaaaaa", Toast.LENGTH_SHORT).show();
    }
}
