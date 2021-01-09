package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class WelcomeScreen extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome_screen);
    }

    public void goToLogin(View view){
        Intent goLogin = new Intent(this,LogIn.class);
        startActivity(goLogin);
    }

    public void goToSignUp(View view){
        Intent goSignUp = new Intent(this,SUPorSUPWG.class);
        startActivity(goSignUp);
    }
}