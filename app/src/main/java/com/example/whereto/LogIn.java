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
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;


public class LogIn extends AppCompatActivity {

    Button buttonLogin;
    TextInputLayout userEmail, userPass;
    FirebaseAuth fAuth;
    String userId;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_log_in);

        //hooks
        buttonLogin = findViewById(R.id.butt_LogIn);
        userEmail = findViewById(R.id.userEmailLogIn);
        userPass = findViewById(R.id.userPassLogIn);

        fAuth = FirebaseAuth.getInstance();

        buttonLogin.setOnClickListener(v -> fAuth.signInWithEmailAndPassword(userEmail.getEditText().getText().toString(), userPass.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if(task.isSuccessful()){
                    userId = fAuth.getCurrentUser().getUid();

                    startActivity(new Intent(LogIn.this,homepage.class));
                }else{
                    Toast.makeText(LogIn.this, "Sorry, your credential is not in our database :(", Toast.LENGTH_SHORT).show();
                }
            }
        }));
    }

    public void goToWelcomeScreen(View view){
        Intent intent = new Intent(this,WelcomeScreen.class);
        startActivity(intent);
    }

    public void goToForgetPassword(View view){
        Intent intent = new Intent(this, ForgetPass.class);
        startActivity(intent);
    }
}