package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import android.os.Bundle;
import android.widget.GridView;

import com.google.android.material.tabs.TabItem;
import com.google.android.material.tabs.TabLayout;

public class Profile extends AppCompatActivity {


    GridView gridView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_profile);

        gridView =(GridView)findViewById(R.id.gridview);
        gridView.setAdapter(new ProfileImageAdapter(this));


    }




    }
