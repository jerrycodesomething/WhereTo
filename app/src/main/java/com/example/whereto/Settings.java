package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.whereto.R;

import org.w3c.dom.Text;

public class Settings extends AppCompatActivity {

    TextView disableORdeleteAcc;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_settings);

        disableORdeleteAcc = findViewById(R.id.settings_DisableorDeleteAccount);

        disableORdeleteAcc.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent confirm = new Intent(Settings.this,AccountStatus.class);
                startActivity(confirm);
            }
        });
    }
}