package com.ifood.ifood;

import android.content.res.Configuration;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Color;
import android.support.annotation.Nullable;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.ifood.ifood.ultil.BottomNavigationViewHelper;

import org.w3c.dom.Text;

public class mainMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setDrawerLayout();

        LinearLayout.LayoutParams layoutMenu = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutMenu.setMargins(0,0,0,50);
        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        listMenu = (LinearLayout) findViewById(R.id.listMenu);

        for (int i = 0; i < 5; i++){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutMenu);

            ImageView imageMenu = new ImageView(this);
            imageMenu.setLayoutParams(new LinearLayout.LayoutParams(800, 400));
            imageMenu.setImageResource(R.drawable.mon_ca_ri_ga);
            imageMenu.setScaleType(ImageView.ScaleType.FIT_XY);

            LinearLayout titleLayout = new LinearLayout(this);
            titleLayout.setOrientation(LinearLayout.HORIZONTAL);

            TextView txtTitle = new TextView(this);
            txtTitle.setLayoutParams(layoutParamsText);
            txtTitle.setText("Cà ri gà");

            Button btnViewDetail = new Button(this);
            btnViewDetail.setBackgroundColor(Color.YELLOW);
            btnViewDetail.setText("View Detail");
            btnViewDetail.setLayoutParams(layoutParamsText);

            titleLayout.addView(txtTitle);
            titleLayout.addView(btnViewDetail);

            TextView txtDescription = new TextView(this);
            txtDescription.setLayoutParams(layoutParamsText);
            txtDescription.setText("Cà ri gà công thức : 2 muỗng knor, 3 muỗng muối, 4 muỗng đường");

            layout.addView(imageMenu);
            layout.addView(titleLayout);
            layout.addView(txtDescription);

            listMenu.addView(layout);
        }
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
}
