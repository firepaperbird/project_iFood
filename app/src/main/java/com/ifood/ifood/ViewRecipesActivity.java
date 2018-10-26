package com.ifood.ifood;

import android.media.Image;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.MenuItem;
import android.widget.ImageView;

import com.ifood.ifood.ultil.ConfigImageQuality;

public class ViewRecipesActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_recipes);

        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        ImageView imageView = new ImageView(this);
        for (int i = 0; i < 3; i++){
            switch (i){
                case 0:
                    imageView = findViewById(R.id.image_recipe_1);
                    imageView.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), R.drawable.step_1));
                    break;
                case 1:
                    imageView = findViewById(R.id.image_recipe_2);
                    imageView.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), R.drawable.step_2));
                    break;
                case 2:
                    imageView = findViewById(R.id.image_recipe_3);
                    imageView.setImageDrawable(ConfigImageQuality.getBitmapImage(getResources(), R.drawable.step_3));
                    break;
            }
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
