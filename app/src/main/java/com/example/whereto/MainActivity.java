package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.WindowManager;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.TextView;

public class MainActivity extends AppCompatActivity {

    //move to the next screen after the splash screen appears,create variable

    private static int SLASH_CREEN=5000; //capital letter bcs its a static, 5000 means 5sec


    //add some variables for the animation
    Animation topAnim, bottAnim;

    //call image view
    ImageView image;
    TextView slogan;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //hide the status bar,above the window bar
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN,WindowManager.LayoutParams.FLAG_FULLSCREEN);
        setContentView(R.layout.activity_main);

        //Animations
        topAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);
        bottAnim = AnimationUtils.loadAnimation(this,R.anim.top_animation);

        //hooks
        image = findViewById(R.id.imageView);
        slogan = findViewById(R.id.textView);

        //assign the animation to image and slogan
        image.setAnimation(topAnim);
        slogan.setAnimation(bottAnim);

        //call intent to interact to next activity, intent will be called inside the delay method
        new Handler().postDelayed(new Runnable() { //handle the delay process
            @Override
            public void run() {
                Intent intent = new Intent(MainActivity.this, WelcomeScreen.class);
                startActivity(intent); //start the activity and pass the intent
                finish(); //have to put finish method bcs without it, when user wants to exit the application and press the back button to exit the applcation, user will be redirected to the splash screen activity


            }
        },SLASH_CREEN);


    }
}