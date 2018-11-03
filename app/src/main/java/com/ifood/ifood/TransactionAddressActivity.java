package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.data.DeleveryInfo;
import com.ifood.ifood.data.Dish;
import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.data.Transaction;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class TransactionAddressActivity extends AppCompatActivity {
    TextView txtName;
    TextView txtPhone;
    TextView txtAddress;
    Spinner spinnerCity;
    Spinner spinnerDistrict;
    String city;
    String district;
    boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_transaction_address);

        txtName = findViewById(R.id.txtName);
        txtPhone = findViewById(R.id.txtPhone);
        txtAddress = findViewById(R.id.txtAddress);
        spinnerCity = findViewById(R.id.spnCity);
        spinnerDistrict = findViewById(R.id.spnDistrict);

        SessionLoginController session = new SessionLoginController(this);
        SqliteUserController sqliteUser = new SqliteUserController(getApplicationContext());
        final Model_User user = sqliteUser.getUserByEmail(session.getEmail());
        txtName.setText(user.getName());
        txtPhone.setText(user.getPhoneNumber());
        txtAddress.setText(user.getAddress());

        //set list city
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.list_city_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerCity.setAdapter(adapter);
        if(user.getCity() != null){
            int pos = adapter.getPosition(user.getCity());
            spinnerCity.setSelection(pos);
        }

        //set Event
        spinnerCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: setListDistrict(spinnerDistrict,R.array.list_District_SG_array,user);
                    break;
                    case 1: setListDistrict(spinnerDistrict,R.array.list_District_HN_array,user);
                    break;
                    case 2: setListDistrict(spinnerDistrict,R.array.list_District_DN_array,user);
                    break;
                    default:break;
                }
                city = adapterView.getItemAtPosition(i).toString();
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {

            }
        });

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    //set list district
    private void setListDistrict(Spinner spnDistrict, int listDistrict, Model_User user){
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, listDistrict, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnDistrict.setAdapter(adapter);
        if(!flag){
            return;
        }
        if(user.getDistrict() != null){
            int pos = adapter.getPosition(user.getDistrict());
            spnDistrict.setSelection(pos);
            flag = false;
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

    public void onCLick(View view) {
        if(txtName.getText().toString().equals("")){
            Toast.makeText(this, "Name must be fill", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtPhone.getText().toString().equals("")){
            Toast.makeText(this, "Phone must be fill", Toast.LENGTH_SHORT).show();
            return;
        }
        if(txtAddress.getText().toString().equals("")){
            Toast.makeText(this, "Address must be fill", Toast.LENGTH_SHORT).show();
            return;
        }

        Transaction transaction = new Transaction();
        transaction.setName(txtName.getText().toString());
        transaction.setPhone(txtPhone.getText().toString());
        transaction.setAddress(txtAddress.getText().toString() + ", " + spinnerDistrict.getSelectedItem().toString()
                + ", " + spinnerCity.getSelectedItem().toString());


        Intent intent = getIntent();
        List<Dish> listDishOrder = (List<Dish>)intent.getSerializableExtra("LISTDISHORDER");
        intent = new Intent(this, TransactionConfirmActivity.class);
        intent.putExtra("LISTDISHORDER" , (Serializable) listDishOrder);
        intent.putExtra("TRANSACTION", transaction);
        startActivity(intent);
    }
}
