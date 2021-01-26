package com.example.whereto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PinLocation extends AppCompatActivity {
    private FloatingActionButton navigation_btn;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_location);

        navigation_btn = findViewById(R.id.navigation);
        navigation_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v){
                Intent intent= new Intent(Intent.ACTION_VIEW,
                        Uri.parse("google.navigation: q= 22.659239,88.435534&mode=1"));


                intent.setPackage("com.google.android.apps.maps");


                if(intent.resolveActivity(getPackageManager())!=null){
                    startActivity(intent);
                }
            }

        });
    }
}
