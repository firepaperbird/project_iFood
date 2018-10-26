package com.ifood.ifood;

import android.app.Activity;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.Toast;

import java.io.FileNotFoundException;
import java.io.InputStream;

public class CreateDishActivity extends AppCompatActivity {
    private final int PICK_IMAGE = 1832;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_dish);

        ActionBar actionBar = this.getSupportActionBar();
        if(actionBar!=null)
        actionBar.setDisplayHomeAsUpEnabled(true);
    }

    public void clickToAddImage(View view) {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);
    }
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data)
    {
        if (requestCode == PICK_IMAGE && resultCode == Activity.RESULT_OK) {
            if (data != null) {
                try {
                    InputStream inputStream = getApplicationContext().getContentResolver().openInputStream(data.getData());

                    ImageView imageView = findViewById(R.id.topCamera);

                    imageView.setImageBitmap(BitmapFactory.decodeStream(inputStream));
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }

            }
        }
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                if (true){
                    Intent intent = new Intent(this, mainMenuActivity.class);
                    intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP|Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                }
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    public void clickToSaveDish(View view) {
        Toast.makeText(this,"Yor dish has save", Toast.LENGTH_LONG).show();
        Intent intent = new Intent(this, mainMenuActivity.class);
        startActivity(intent);
    }

    public void clickToAddStep(View view) {
        LinearLayout linearLayout = findViewById(R.id.layoutRecipes);
        EditText myEditText = new EditText(this);
        myEditText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        myEditText.setHint("Describe the cook step");
        linearLayout.addView(myEditText);
    }

    public void clickToAddIngredient(View view) {
        LinearLayout linearLayout = findViewById(R.id.layoutIngredients);
        EditText myEditText = new EditText(this);
        myEditText.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,ViewGroup.LayoutParams.WRAP_CONTENT));
        myEditText.setHint("Describe ingredient");
        linearLayout.addView(myEditText);
    }
}
