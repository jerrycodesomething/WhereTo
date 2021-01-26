package com.example.whereto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


public class Social_layout_friends extends AppCompatActivity{

    //widgets and view
    private ImageButton btn;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_layout_friends);

        TabLayout tabLayout = (TabLayout) findViewById(R.id.social_tab);
        TabItem visited_tab= (TabItem) findViewById(R.id.myfriends_tab);
        TabItem favourites_tab= (TabItem) findViewById(R.id.addrequest_tab);

        Fragment fragment = null;
        fragment = new FragmentMyFriends();
        getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_friends_output, fragment).commit();

        tabLayout.addOnTabSelectedListener(new TabLayout.OnTabSelectedListener() {
            @Override
            public void onTabSelected(TabLayout.Tab tab) {
                Fragment fragment = null;
                switch (tab.getPosition()){
                    case 0:
                        fragment = new FragmentMyFriends();
                        break;
                    case 1:
                        fragment = new Fragment();
                        break;
                    default:
                }
                FragmentManager manager = getSupportFragmentManager();
                FragmentTransaction transaction = manager.beginTransaction();
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_my_friends_output, fragment).commit();
            }

            @Override
            public void onTabUnselected(TabLayout.Tab tab) {

            }

            @Override
            public void onTabReselected(TabLayout.Tab tab) {

            }
        });

        btn = (ImageButton) findViewById (R.id.backProfileButton);
    }

    public void BTP(View view){

        Intent intent =new Intent();
        intent.setClass(Social_layout_friends.this,Profile.class);
        startActivity(intent);
    }
}
