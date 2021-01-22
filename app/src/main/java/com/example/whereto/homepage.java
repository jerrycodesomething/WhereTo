package com.example.whereto;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Homepage components
    FloatingActionButton menuIcon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton checkInIcon;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);
        //Check in

//        checkInIcon = findViewById(R.id.check_in_button);
//        checkInIcon.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Intent intent = new Intent(homepage.this,CheckinActivity.class);
//                startActivity(intent);
//            }
//        });

        // Loads the map fragment
        Fragment fragment = null;
        fragment = new FragmentMap();
        getSupportFragmentManager().beginTransaction().replace(R.id.map_output, fragment).commit();

        //button finders
        menuIcon = findViewById(R.id.sidebarMenu);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        navigationDrawer();
    }

    //Navigation drawer method
    private void navigationDrawer() {
        //Navigation drawer
        navigationView.bringToFront();  //want to interact with the navigation drawer
        navigationView.setNavigationItemSelectedListener(this);
        navigationView.setCheckedItem(R.id.nav_home);

        menuIcon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerVisible(GravityCompat.START))
                    drawerLayout.closeDrawer(GravityCompat.START);
                else {
                    drawerLayout.openDrawer(GravityCompat.START);
                }
            }
        });
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        switch(item.getItemId()){
            case R.id.nav_home:
                Intent intent = new Intent(homepage.this,homepage.class);
                startActivity(intent);
                break;
            case R.id.nav_profile:
                Intent intent2 = new Intent(homepage.this,Profile.class);
                startActivity(intent2);
                break;
            case R.id.nav_settings:
                Intent intent3 = new Intent(homepage.this,Settings.class);
                startActivity(intent3);
                break;
            case R.id.nav_help:
                Intent intent4 = new Intent(homepage.this,Help.class);
                startActivity(intent4);
                break;
            case R.id.nav_logout:
                Intent intent5 = new Intent(homepage.this,trytest.class);
                startActivity(intent5);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true; //there is some item will be selected
    }
}