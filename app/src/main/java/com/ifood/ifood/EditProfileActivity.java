package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteUserController;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        Intent intent = getIntent();
        final Model_User user = (Model_User)intent.getSerializableExtra("USERINFO");

        final EditText edtName = findViewById(R.id.editName);
        edtName.setText(user.getUsername());

        final EditText edtAddress = findViewById(R.id.editAddress);
        edtAddress.setText(user.getAddress());

        final EditText edtPhoneNumber = findViewById(R.id.editPhoneNumber);
        edtPhoneNumber.setText(user.getPhoneNumber());

        Button btnSave = findViewById(R.id.btnSaveUserInfo);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( EditProfileActivity.this, UserDetailActivity.class);
                Model_User edtUser = user;
                edtUser.setUsername(edtName.getText().toString());
                edtUser.setAddress(edtAddress.getText().toString());
                edtUser.setPhoneNumber(edtPhoneNumber.getText().toString());

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
