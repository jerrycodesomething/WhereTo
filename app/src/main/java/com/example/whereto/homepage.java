package com.example.whereto;

import android.content.Intent;
import android.os.Bundle;
import android.text.Layout;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.google.android.material.bottomsheet.BottomSheetDialog;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;

public class homepage extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {

    //Homepage components
    FloatingActionButton menuIcon;
    DrawerLayout drawerLayout;
    NavigationView navigationView;
    FloatingActionButton checkInIcon;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId, user_name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_homepage);

        // Loads the map fragment
        Fragment fragment = null;
        fragment = new FragmentMap();
        getSupportFragmentManager().beginTransaction().replace(R.id.map_output, fragment).commit();

        //button finders
        menuIcon = findViewById(R.id.sidebarMenu);

        //Menu Hooks
        drawerLayout = findViewById(R.id.drawer_layout);
        navigationView = findViewById(R.id.navigation_view);

        //firestore instatiation
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();

        DocumentReference documentReference = fStore.collection("users").document(userId);
        documentReference.addSnapshotListener(this, new EventListener<DocumentSnapshot>() {
            @Override
            public void onEvent(@Nullable DocumentSnapshot documentSnapshot, @Nullable FirebaseFirestoreException error) {
                user_name = documentSnapshot.getString("fName");

                Toast.makeText(homepage.this, "Welcome back " + user_name, Toast.LENGTH_SHORT).show();
            }
        });

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
                Intent intent5 = new Intent(homepage.this, WelcomeScreen.class);
                startActivity(intent5);
                break;
        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true; //there is some item will be selected
    }
}