package com.example.whereto;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import com.google.android.material.floatingactionbutton.FloatingActionButton;

public class PinLocation extends AppCompatActivity {
    private Button navigation_btn;

    protected  void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_pin_location);

        navigation_btn=findViewById(R.id.navigation);

        navigation_btn.setOnClickListener(new View.OnClickListener(){
            public void onClick(View v) {
                Intent intent = new Intent(Intent.ACTION_VIEW);
                intent.setData(Uri.parse("geo:47.4925,19.0513"));
                Intent chooser = Intent.createChooser(intent,"launch maps");
                startActivity(chooser);
            }
        });
    }
}
