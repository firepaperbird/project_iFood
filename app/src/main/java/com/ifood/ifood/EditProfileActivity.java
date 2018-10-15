package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.EditText;

import com.ifood.ifood.data.Model_User;

public class EditProfileActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_edit_profile);

        Intent intent = getIntent();
        Model_User user = (Model_User)intent.getSerializableExtra("USERINFO");

        EditText edtName = findViewById(R.id.editName);
        edtName.setText(user.getUsername());

        EditText edtAddress = findViewById(R.id.editAddress);
        edtAddress.setText(user.getAddress());

        EditText edtDescription = findViewById(R.id.editDescription);
        edtDescription.setText(user.getDescription());
    }
}
