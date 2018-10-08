package com.ifood.ifood;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.ultil.BottomNavigationViewHelper;

public class mainMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setDrawerLayout();

        setListMenu();
    }

    @Override
    protected void onPostCreate(@Nullable Bundle savedInstanceState) {
        super.onPostCreate(savedInstanceState);
        drawerToggle.syncState();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
        drawerToggle.onConfigurationChanged(newConfig);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main_actions, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        switch (item.getItemId()) {
            case R.id.btnSearch:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnAbout:
                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnHelp:
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private void setDrawerLayout(){
        drawerLayout = (DrawerLayout) findViewById(R.id.activity_main_drawer);
        drawerToggle = new ActionBarDrawerToggle(this, drawerLayout, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(drawerToggle);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setHomeButtonEnabled(true);
        BottomNavigationView bottomNavigationView = (BottomNavigationView) findViewById(R.id.bottom_navigation_bar);
        BottomNavigationViewHelper.disableShiftMode(bottomNavigationView);

    }

    private void setListMenu(){

        LinearLayout.LayoutParams layoutMenu = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutMenu.setMargins(0,0,0,50);

        LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);

        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsText.setMargins(0, 0,20,20);

        LinearLayout.LayoutParams layoutParamsDivider = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                3);
        layoutParamsDivider.setMargins(0,0,0,20);

        LinearLayout.LayoutParams layoutParamsTag = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTag.setMargins(0, 0, 10, 0);

        listMenu = (LinearLayout) findViewById(R.id.listMenu);

        for (int i = 0; i < 5; i++){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutMenu);
            layout.setPadding(2,2,2,0);
            layout.setBackgroundResource(R.drawable.border_menu);

            /*Image*/
            ImageView imageMenu = new ImageView(this);
            imageMenu.setLayoutParams(new LinearLayout.LayoutParams(800, 400));
            imageMenu.setScaleType(ImageView.ScaleType.FIT_XY);
            imageMenu.setImageResource(R.drawable.mon_ca_ri_ga);


            LinearLayout layoutInfo = new LinearLayout(this);
            layoutInfo.setLayoutParams(layoutParamsInfo);
            layoutInfo.setOrientation(LinearLayout.VERTICAL);
            layoutInfo.setPadding(25,25,25,25);
            /*Title*/
            TextView txtTitle = new TextView(this);
            txtTitle.setLayoutParams(layoutParamsText);
            txtTitle.setTextSize(20);
            Typeface font = ResourcesCompat.getFont(this, R.font.arrusb);
            txtTitle.setTypeface(font, Typeface.BOLD);
            txtTitle.setText("Cà ri gà");

            /*Description*/
            TextView txtDescription = new TextView(this);
            txtDescription.setLayoutParams(layoutParamsText);
            txtDescription.setText("Cà ri gà công thức : 2 muỗng knor, 3 muỗng muối, 4 muỗng đường");

            /*Divider*/
            View divider = new View(this);
            divider.setLayoutParams(layoutParamsDivider);
            divider.setBackgroundColor(Color.GRAY);

            /*Tags*/
            LinearLayout tagLayout = new LinearLayout(this);
            tagLayout.setLayoutParams(layoutParamsTag);

            for (int tagIndex = 0; tagIndex < 3; tagIndex++){
                TextView tag = new TextView(this);
                tag.setLayoutParams(layoutParamsTag);
                tag.setText("India");
                tag.setBackgroundResource(R.drawable.border_tag);
                tagLayout.addView(tag);
            }

            layoutInfo.addView(txtTitle);
            layoutInfo.addView(txtDescription);
            layoutInfo.addView(divider);
            layoutInfo.addView(tagLayout);


            layout.addView(imageMenu);
            layout.addView(layoutInfo);

            listMenu.addView(layout);
        }
    }
}
