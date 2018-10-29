package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;

import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;

public class EditProfileActivity extends AppCompatActivity {

    private boolean flag = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final Model_User user = (Model_User)intent.getSerializableExtra("USERINFO");

        final EditText edtName = findViewById(R.id.edtName);
        edtName.setText(user.getUsername());

        final EditText edtAddress = findViewById(R.id.edtAddress);
        edtAddress.setText(user.getAddress());

        final EditText edtPhoneNumber = findViewById(R.id.edtPhoneNumber);
        edtPhoneNumber.setText(user.getPhoneNumber());

        final Spinner spnCity = findViewById(R.id.spnCity);
        ArrayAdapter<CharSequence> adapterCity = ArrayAdapter.createFromResource(
                this, R.array.list_city_array, android.R.layout.simple_spinner_item);
        adapterCity.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spnCity.setAdapter(adapterCity);
        if(user.getCity() != null){
            int pos = adapterCity.getPosition(user.getCity());
            spnCity.setSelection(pos);
        }

        final Spinner spnDistrict = findViewById(R.id.spnDistrict);

        spnCity.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> adapterView, View view, int i, long l) {
                switch (i){
                    case 0: setListDistrict(spnDistrict, R.array.list_District_SG_array, user);
                        break;
                    case 1: setListDistrict(spnDistrict, R.array.list_District_HN_array, user);
                        break;
                    case 2: setListDistrict(spnDistrict, R.array.list_District_DN_array, user);
                        break;
                    default:break;
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> adapterView) {
            }
        });

        Button btnSave = findViewById(R.id.btnSaveUserInfo);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( EditProfileActivity.this, UserDetailActivity.class);
                Model_User edtUser = user;
                edtUser.setUsername(edtName.getText().toString());
                edtUser.setAddress(edtAddress.getText().toString());
                edtUser.setPhoneNumber(edtPhoneNumber.getText().toString());
                edtUser.setCity(spnCity.getSelectedItem().toString());
                edtUser.setDistrict(spnDistrict.getSelectedItem().toString());


                SqliteUserController sqlite = new SqliteUserController(getApplicationContext());
                sqlite.updateDataIntoTable(sqlite.getTableName(), edtUser, "Id = ?", new String[] {user.getId().toString()});

                SessionLoginController session = new SessionLoginController(EditProfileActivity.this);
                session.setUsername(edtUser.getUsername());

                intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                intent.putExtra("EDIT_SUCCESSFUL", true);
                startActivity(intent);

                finish();
            }
        });
    }

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
}
