package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.google.android.material.navigation.NavigationView;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
//    TextView name , mail;
//    Button logout;

    LinearLayout contentView;
    static final float END_SCALE = 0.7f;
    ImageView menuIcon,backtoLoginPage;


    //Drawer menu
    DrawerLayout drawerLayout;
    NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        backtoLoginPage = findViewById(R.id.backtoLogin);
        contentView = findViewById(R.id.content);
        menuIcon = findViewById(R.id.sidebarMenu);
        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        backtoLoginPage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(homepage.this,LogIn.class));
            }
        });


        navigationDrawer();

//        logout =findViewById(R.id.logout);
//        name =findViewById(R.id.name);
//        mail =findViewById(R.id.mail);
//
//        GoogleSignInAccount signInAccount = GoogleSignIn.getLastSignedInAccount(this);
//        if(signInAccount != null){
//            name.setText(signInAccount.getDisplayName());
//            mail.setText(signInAccount.getEmail());
//        }

//        logout.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                FirebaseAuth.getInstance().signOut();
//                Intent intent = new Intent(getApplicationContext(),WelcomeScreen.class);
//                startActivity(intent);
//            }
//        });
    }
    //Navigation Drawer Function
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

        animationForNavigationDrawer();
    }

    private void animationForNavigationDrawer() {
        drawerLayout.addDrawerListener(new DrawerLayout.SimpleDrawerListener() {
            @Override
            public void onDrawerSlide(@NonNull View drawerView, float slideOffset) {

                //scale the view based on current slide offset
                final float diffScaledOffset = slideOffset*(1 - END_SCALE);
                final float offsetScale = 1 - diffScaledOffset;
                contentView.setScaleX(offsetScale);
                contentView.setScaleY(offsetScale);

                //translate the view, accounting for the scaled width
                final float xOffset = drawerView.getWidth()*slideOffset;
                final float xOffsetDiff = contentView.getWidth()*diffScaledOffset/2;
                final float xTranslation = xOffset - xOffsetDiff;
                contentView.setTranslationX(xTranslation);

            }

        });
    }

//    @Override
//    public void onBackPressed() {
//
//        if(drawerLayout.isDrawerVisible(GravityCompat.START)){
//            drawerLayout.closeDrawer(GravityCompat.START);
//        }else
//            super.onBackPressed();
//    }

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
        }

        drawerLayout.closeDrawer(GravityCompat.START);
        return true; //there is some item will be selected
    }
}