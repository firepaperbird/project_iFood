package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.ifood.ifood.data.Model_User;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        final Model_User user = (Model_User)intent.getSerializableExtra("USERINFO");

        final EditText edtName = findViewById(R.id.editName);
        edtName.setText(user.getUsername());

        final EditText edtAddress = findViewById(R.id.editAddress);
        edtAddress.setText(user.getAddress());

        final EditText edtDescription = findViewById(R.id.editDescription);
        edtDescription.setText(user.getDescription());

        Button btnSave = findViewById(R.id.btnSaveUserInfo);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent( EditProfileActivity.this, UserDetailActivity.class);

                Model_User edtUser = new Model_User();
                edtUser.setUsername(edtName.getText().toString());
                edtUser.setAddress(edtAddress.getText().toString());
                edtUser.setDescription(edtDescription.getText().toString());

                intent.putExtra("USERINFO", edtUser);
                startActivity(intent);

                finish();
            }
        });
    }
}
