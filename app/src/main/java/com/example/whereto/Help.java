package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;

import java.util.Arrays;

public class Help extends AppCompatActivity {

    //declare variables
    ListView lv;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_help);

        lv = findViewById(R.id.help_listView);

        final String values[] ={"FAQ","About"}; //store the list in the values'array

        // Create ArrayAdapter to hold the values
        ArrayAdapter arrayAdapter = new ArrayAdapter(this,android.R.layout.simple_list_item_1, Arrays.asList(values));

        lv.setAdapter(arrayAdapter);

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
                if(position == 0 || position ==1){
                    Intent intent = new Intent(Help.this, FaqAbout.class);
                    startActivity(intent);
                }
            }
        });
    }
}