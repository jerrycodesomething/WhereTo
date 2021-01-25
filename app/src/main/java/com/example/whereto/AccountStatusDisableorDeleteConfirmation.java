package com.example.whereto;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class AccountStatusDisableorDeleteConfirmation extends AppCompatActivity {

    EditText usersAnswer;
    Button button;
    String mText;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status_disableor_delete_confirmation);

        usersAnswer = (EditText) findViewById(R.id.editTextTextPersonName);
        button = (Button) findViewById(R.id.button);


        button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                 mText = usersAnswer.getText().toString().trim();

                 if(mText.isEmpty()){
                     Toast.makeText(AccountStatusDisableorDeleteConfirmation.this, "Please enter your confirmation again", Toast.LENGTH_SHORT).show();
                 }
                 else {
                     Toast.makeText(AccountStatusDisableorDeleteConfirmation.this, "Your account has been : " + mText, Toast.LENGTH_SHORT).show();
                     AccountStatusDisableorDeleteConfirmation.this.finish();
                     System.exit(0);
                 }
            }
        });


    }
}