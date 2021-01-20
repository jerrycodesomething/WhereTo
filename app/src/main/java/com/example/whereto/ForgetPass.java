package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.FirebaseAuth;

public class ForgetPass extends AppCompatActivity {

    //variables
    TextInputLayout userforgotEmail;
    Button sendpasswordRecovery;

    FirebaseAuth fAuth;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_pass);

        userforgotEmail = findViewById(R.id.forgotPassEmail);
        sendpasswordRecovery = findViewById(R.id.sendPassRecovery);

        fAuth =FirebaseAuth.getInstance();

        sendpasswordRecovery.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                fAuth.sendPasswordResetEmail(userforgotEmail.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) { //check the status
                        if(task.isSuccessful()){
                            Toast.makeText(ForgetPass.this, "Password send to your email", Toast.LENGTH_SHORT).show();
                        }else{
                            Toast.makeText(ForgetPass.this, task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }

    public void goToLogin(View view) {
        Intent intent = new Intent(this, LogIn.class);
        startActivity(intent);
    }
}