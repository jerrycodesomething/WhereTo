package com.example.whereto;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import androidx.appcompat.app.AppCompatActivity;


public class Social_layout_friends extends AppCompatActivity{
    private ImageButton btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_layout_friends);
        btn = (ImageButton) findViewById (R.id.backProfileButton);
    }
        public void BTP(View view){
         Intent intent =new Intent();
         intent.setClass(Social_layout_friends.this,Profile.class);
         startActivity(intent);
        }
}
