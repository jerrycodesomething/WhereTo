package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

public class AccountStatus extends AppCompatActivity {
    TextView tv1,tv2;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status);

        tv1 = findViewById(R.id.accountstatus_tv1);
        tv2 = findViewById(R.id.accountstatus_tv2);

        tv1.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent disable = new Intent(AccountStatus.this,AccountStatusDisableorDeleteConfirmation.class);
                startActivity(disable);
            }
        });

        tv2.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent delete = new Intent(AccountStatus.this,AccountStatusDisableorDeleteConfirmation.class);
                startActivity(delete);
            }
        });
        
    }


}