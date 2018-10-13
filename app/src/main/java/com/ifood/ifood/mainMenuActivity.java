package com.ifood.ifood;

import android.content.res.Configuration;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Build;
import android.support.annotation.Nullable;
import android.support.annotation.RequiresApi;
import android.support.design.widget.BottomNavigationView;
import android.support.v4.content.res.ResourcesCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.view.ViewTreeObserver;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.ifood.ifood.ultil.BottomNavigationViewHelper;

public class mainMenuActivity extends AppCompatActivity {
    private DrawerLayout drawerLayout;
    private ActionBarDrawerToggle drawerToggle;
    private LinearLayout listMenu;
    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_menu);

        setDrawerLayout();

        setListMenu();

        LinearLayout userIcon = findViewById(R.id.userIcon);
        userIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });


        ActionBar actionBar = this.getSupportActionBar();
        actionBar.setDisplayOptions(ActionBar.DISPLAY_SHOW_CUSTOM);
        actionBar.setCustomView(R.layout.actionbar_layout);
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setHomeButtonEnabled(true);
        actionBar.setDisplayHomeAsUpEnabled(true);
        int categoryId = 1;
        //Integer.parseInt(actionBar.getTitle() + "")
        TextView txtTitle = findViewById(R.id.action_bar_title);
        switch (categoryId) {
            case 1:
                txtTitle.setText(getResources().getString(R.string.menu_gym_food));
                break;
            case 2:
                txtTitle.setText(getResources().getString(R.string.menu_healthy_food));
                break;
            case 3:
                txtTitle.setText(getResources().getString(R.string.menu_daily_food));
                break;
            default:
                txtTitle.setText("Home");
                break;
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
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        if(drawerToggle.onOptionsItemSelected(item)) {
            return true;
        }

        /*switch (item.getItemId()) {
            case R.id.btnSearch:
                Toast.makeText(this, "Search button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnAbout:
                Toast.makeText(this, "About button selected", Toast.LENGTH_SHORT).show();
                return true;
            case R.id.btnHelp:
                Toast.makeText(this, "Help button selected", Toast.LENGTH_SHORT).show();
                return true;
        }*/
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

    @RequiresApi(api = Build.VERSION_CODES.LOLLIPOP)
    private void setListMenu(){

        LinearLayout.LayoutParams layoutMenu = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                400);

        LinearLayout.LayoutParams layoutParamsInfo = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.FILL_PARENT);

        LinearLayout.LayoutParams layoutParamsText = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsText.setMargins(0, 0,20,20);

        LinearLayout.LayoutParams layoutParamsDivider = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT,
                3);
        layoutParamsDivider.setMargins(0,0,0,20);

        LinearLayout.LayoutParams layoutParamsTag = new LinearLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT);
        layoutParamsTag.setMargins(0, 0, 10, 0);

        listMenu = findViewById(R.id.listMenu);

        for (int i = 0; i < 5; i++){
            LinearLayout layout = new LinearLayout(this);
            layout.setOrientation(LinearLayout.VERTICAL);
            layout.setLayoutParams(layoutMenu);
            layout.setBackground(getDrawable(R.drawable.mon_ca_ri_ga));


            /*Image*/
            /*
            final ImageView imageMenu = new ImageView(this);
            final ViewTreeObserver observer = listMenu.getViewTreeObserver();
            observer.addOnGlobalLayoutListener(
                    new ViewTreeObserver.OnGlobalLayoutListener() {
                        @Override
                        public void onGlobalLayout() {
                            imageMenu.setLayoutParams(new LinearLayout.LayoutParams(listMenu.getWidth(), 400));
                        }
                    });
            imageMenu.setScaleType(ImageView.ScaleType.FIT_XY);
            imageMenu.setImageResource(R.drawable.mon_ca_ri_ga);
*/

            LinearLayout layoutInfo = new LinearLayout(this);
            layoutInfo.setLayoutParams(layoutParamsInfo);
            layoutInfo.setOrientation(LinearLayout.VERTICAL);
            layoutInfo.setGravity(Gravity.BOTTOM);
            layoutInfo.setPadding(25,25,25,25);
            /*Title*/
            TextView txtTitle = new TextView(this);
            txtTitle.setLayoutParams(layoutParamsText);
            txtTitle.setTextSize(20);
            Typeface font = ResourcesCompat.getFont(this, R.font.arrusb);
            txtTitle.setTypeface(font, Typeface.BOLD);
            txtTitle.setTextColor(Color.WHITE);
            txtTitle.setGravity(Gravity.BOTTOM);
            txtTitle.setText("BBQ and Vegatable");

            /*Divider*/
            View divider = new View(this);
            divider.setLayoutParams(layoutParamsDivider);
            txtTitle.setGravity(Gravity.BOTTOM);
            divider.setBackgroundColor(Color.WHITE);

            /*Tags*/
            LinearLayout tagLayout = new LinearLayout(this);
            txtTitle.setGravity(Gravity.BOTTOM);
            tagLayout.setLayoutParams(layoutParamsTag);

            for (int tagIndex = 0; tagIndex < 3; tagIndex++){
                TextView tag = new TextView(this);
                tag.setLayoutParams(layoutParamsTag);
                tag.setText("India");
                tag.setBackgroundResource(R.drawable.border_tag);
                tagLayout.addView(tag);
            }

            layoutInfo.addView(txtTitle);
            layoutInfo.addView(divider);
            layoutInfo.addView(tagLayout);

            LinearLayout shadowLayout = new LinearLayout(this);
            shadowLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    400));
            shadowLayout.setBackground(getDrawable(R.drawable.shadow));
            shadowLayout.getBackground().setAlpha(175);
            shadowLayout.setGravity(Gravity.BOTTOM);

            FrameLayout frameLayout = new FrameLayout(this);
            frameLayout.setLayoutParams(new LinearLayout.LayoutParams(ViewGroup.LayoutParams.FILL_PARENT,
                    ViewGroup.LayoutParams.FILL_PARENT, Gravity.BOTTOM));

            frameLayout.addView(shadowLayout);
            frameLayout.addView(layoutInfo);

            layout.addView(frameLayout);

            listMenu.addView(layout);
        }
    }
}
