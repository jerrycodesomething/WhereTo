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

        buttonLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(!validateEmail() | !validatePass()){
                    return;
                }
                else{
                    fAuth.signInWithEmailAndPassword(userEmail.getEditText().getText().toString(), userPass.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {

                                startActivity(new Intent(LogIn.this, homepage.class));
                            } else {
                                Toast.makeText(LogIn.this, "Sorry, your credential is not in our database :(", Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }

    private boolean validateEmail() {
        //create string to get user's value of fullname
        //as we are using material design and assign id to layout, need to define getedittext,
        // trim to ensure there's no spaces that will be stored into the database
        String val = userEmail.getEditText().getText().toString().trim();
        //check spaces
        //"\\A\\w{4,20}\\z" means the username should have minimum of 4 & max of 20 char
        // w/o spaces at the start or in the end
        //Any capital A to small z
        String checkEmail = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        //check the value is empty or not
        if (val.isEmpty()) {
            //seterror is build in fx
            userEmail.setError("Field cannot be empty");
            return false;
        }
        //if value entered is not matched with the define method, error will appear
        else if(!val.matches(checkEmail)){
            userEmail.setError("Invalid Email");
            return false;
        }
        else {
            //null will automatically remove the error
            userEmail.setError(null);
            //the error for the material design is going to take some space, remove the field and spaces
            userEmail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePass() {
        //create string to get user's value of fullname
        //as we are using material design and assign id to layout, need to define getedittext,
        // trim to ensure there's no spaces that will be stored into the database
        String val = userPass.getEditText().getText().toString().trim();
        //check spaces
        //"\\A\\w{4,20}\\z" means the username should have minimum of 4 & max of 20 char
        // w/o spaces at the start or in the end
        //Any capital A to small z
        String checkPass = "^"+ //"(?=.*[0-9])" +         //at least 1 digit
                //"(?=.*[a-z])" +         //at least 1 lower case letter
                //"(?=.*[A-Z])" +         //at least 1 upper case letter
                //"(?=.*[a-zA-Z])" +      //any letter
                //"(?=.*[@#$%^&+=])" +    //at least 1 special character
                //"(?=S+$)" +           //no white spaces
                ".{4,20}";               //at least 4 characters
        // "$";;

        //check the value is empty or not
        if (val.isEmpty()) {
            //seterror is build in fx
            userPass.setError("Field cannot be empty");
            return false;
        }
        //if value entered is not matched with the define method, error will appear
        else if(!val.matches(checkPass)){
            userPass.setError("Password should contain 4 characters");
            return false;
        }
        else {
            //null will automatically remove the error
            userPass.setError(null);
            //the error for the material design is going to take some space, remove the field and spaces
            userPass.setErrorEnabled(false);
            return true;
        }
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