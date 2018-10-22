package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.Dialog.NewCookbookDialog;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.data.Model_User;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;
import com.ifood.ifood.ultil.SqliteDataController;
import com.ifood.ifood.ultil.SqliteUserController;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Type;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class UserDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_detail);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);

        boolean isEditSuccessful = getIntent().getBooleanExtra("LOGIN_SUCCESSFUL", false);
        if (isEditSuccessful){
            Toast.makeText(this, "Edit successful. ", Toast.LENGTH_SHORT).show();
        }

        SqliteUserController sqliteControl = new SqliteUserController(getApplicationContext());
        Model_User user = null;
        try {
            SessionLoginController session = new SessionLoginController(this);
            user = sqliteControl.getUserByEmail(session.getEmail());
        }catch (Exception e) {
            e.printStackTrace();
        }

        TextView txtUsername = findViewById(R.id.txtUsername);
        txtUsername.setText(user.getUsername());

        Button editProfileBtn = findViewById(R.id.btnEditProfile);
        final Model_User finalUser = user;
        editProfileBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(UserDetailActivity.this, EditProfileActivity.class);
                intent.putExtra("USERINFO", finalUser);
                startActivity(intent);
            }
        });

        setCookbooksLayout();
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

    private void setCookbooksLayout(){
        int layoutCookbookWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int layoutCookbookHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

        LinearLayout mainLayoutCookbooks = findViewById(R.id.mainLayoutCookbook);

        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        SessionLoginController session = new SessionLoginController(this);

        List<Model_Cookbook> listCookbook = sqlite.getCookbookByUserId(session.getUserId());
        if (listCookbook.size() != 0){
            for (int i = 0; i < listCookbook.size(); i++){
                FrameLayout newLayoutCookbook = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.cookbook_layout, null);
                LinearLayout.LayoutParams layoutParamsCookbook = new LinearLayout.LayoutParams(layoutCookbookWidth, layoutCookbookHeight);
                layoutParamsCookbook.setMargins(20, 0,0,0);
                newLayoutCookbook.setLayoutParams(layoutParamsCookbook);

                ImageView newImageCookbook = newLayoutCookbook.findViewById(R.id.imageCookbook);
                newImageCookbook.setImageResource(Integer.parseInt(listCookbook.get(i).getImageId()));

                TextView newTxtCookbookTitle = newLayoutCookbook.findViewById(R.id.txtCookbookTitle);
                newTxtCookbookTitle.setText(listCookbook.get(i).getTitle());

                TextView newTxtTotalRecipes = newLayoutCookbook.findViewById(R.id.txtTotalRecipes);
                newTxtTotalRecipes.setText(listCookbook.get(i).getTotalRecipes() + " recipes");

                int totalLayoutCookbook = mainLayoutCookbooks.getChildCount();
                if (i % 2 == 0){
                    //int id = getResources().getIdentifier("layoutCookbooks_" + (totalLayoutCookbook - 1), "id", getApplicationContext().getPackageName());
                    LinearLayout layoutCookbook = mainLayoutCookbooks.findViewWithTag("layoutCookbooks_" + (totalLayoutCookbook - 1));
                    layoutCookbook.addView(newLayoutCookbook);

                } else {
                    LinearLayout newLayoutCookbooks = new LinearLayout(this);
                    LinearLayout.LayoutParams layoutParams = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT);
                    layoutParams.setMargins(0,10,0,0);
                    newLayoutCookbooks.setLayoutParams(layoutParams);
                    newLayoutCookbooks.setOrientation(LinearLayout.HORIZONTAL);
                    newLayoutCookbooks.setTag("layoutCookbooks_" + totalLayoutCookbook);
                    newLayoutCookbooks.addView(newLayoutCookbook);
                    mainLayoutCookbooks.addView(newLayoutCookbooks);
                }
            }
        }
    }

    public void addNewCookBook(View view){
        NewCookbookDialog dialog = new NewCookbookDialog();
        dialog.show(getFragmentManager(), "");
    }
}
