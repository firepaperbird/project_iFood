package com.ifood.ifood;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.FrameLayout;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifood.ifood.Dialog.NewCookbookDialog;
import com.ifood.ifood.data.Model_Cookbook;
import com.ifood.ifood.ultil.SessionLoginController;
import com.ifood.ifood.ultil.SqliteCookbookController;

import java.util.List;

public class ViewCookbooksActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_cookbooks);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
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
        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        SessionLoginController session = new SessionLoginController(this);
        if (session.getEmail().isEmpty()){
            Intent intent = new Intent(this, LoginActivity.class);
            startActivity(intent);
            finish();
            return;
        }
        int layoutCookbookWidth = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 150, getResources().getDisplayMetrics());
        int layoutCookbookHeight = (int) TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP, 200, getResources().getDisplayMetrics());

        LinearLayout mainLayoutCookbooks = findViewById(R.id.mainLayoutCookbook);

        List<Model_Cookbook> listCookbook = sqlite.getCookbookByUserId(session.getUserId());
        if (listCookbook.size() != 0){
            for (int i = 0; i < listCookbook.size(); i++){
                FrameLayout newLayoutCookbook = (FrameLayout) LayoutInflater.from(this).inflate(R.layout.cookbook_layout, null);
                LinearLayout.LayoutParams layoutParamsCookbook = new LinearLayout.LayoutParams(layoutCookbookWidth, layoutCookbookHeight);
                layoutParamsCookbook.setMargins(20, 0,0,0);
                newLayoutCookbook.setLayoutParams(layoutParamsCookbook);
                newLayoutCookbook.setTag(listCookbook.get(i).getId());

                ImageView newImageCookbook = newLayoutCookbook.findViewById(R.id.imageCookbook);
                newImageCookbook.setScaleType(ImageView.ScaleType.CENTER_CROP);
                newImageCookbook.setImageResource(Integer.parseInt(listCookbook.get(i).getImageId()));

                TextView newTxtCookbookTitle = newLayoutCookbook.findViewById(R.id.txtCookbookTitle);
                newTxtCookbookTitle.setText(listCookbook.get(i).getTitle());

                TextView newTxtTotalRecipes = newLayoutCookbook.findViewById(R.id.txtTotalRecipes);
                newTxtTotalRecipes.setText(listCookbook.get(i).getTotalRecipes() + " recipes");

                int totalLayoutCookbook = mainLayoutCookbooks.getChildCount();
                if (i % 2 == 0){
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

    public void viewDetailCookbook(View view) {
        View cookbookLayout = (View) view.getParent();
        String cookbookId = cookbookLayout.getTag().toString();

        SqliteCookbookController sqlite = new SqliteCookbookController(getApplicationContext());
        Model_Cookbook cookbook = sqlite.getCookbookById(Integer.parseInt(cookbookId));
        if (cookbook != null){
            Intent intent = new Intent(this, DetailCookbook.class);
            intent.putExtra("COOKBOOK_INFO", cookbook);
            startActivity(intent);
        }
    }
}
