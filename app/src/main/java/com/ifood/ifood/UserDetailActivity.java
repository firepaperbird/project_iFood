package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.ifood.ifood.data.Model_User;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        final Model_User user = new Model_User("Alibaba", "Alibaba@gmail.com", "1/23 Cong vien phan mem Quang Trung", "Tui la Alibaba");
        TextView txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(user.getUsername());

        Button editProfileBtn = findViewById(R.id.btnEditProfile);
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, EditProfileActivity.class);
                intent.putExtra("USERINFO", user);
                startActivity(intent);
            }
        });
    }

}
