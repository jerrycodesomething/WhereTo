package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.nfc.Tag;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;

public class SignUp extends AppCompatActivity {

    //variables
    ImageView regbackBtn;
    Button regnext, reglogin;
    TextView regtitleText;

    //Declare the variables for validation
    TextInputLayout regfullName, regusername, regpassword, regemail,regphoneNo;

    public static final String TAG = "TAG";
    FirebaseAuth fAuth;
    DatabaseReference mDatabase;

    //DatabaseReference reference;
    FirebaseFirestore fStore;
    String userID;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);

        //hooks
        regbackBtn = findViewById(R.id.signup_backButton);
        regnext = findViewById(R.id.signup_nextbutton);
        reglogin = findViewById(R.id.signuploginbutton);
        regtitleText = findViewById(R.id.signup_titlebutton);

        //hooks's variable for validation

        regfullName = findViewById(R.id.signUp_fullname);
        regusername = findViewById(R.id.signUp_username);
        regpassword = findViewById(R.id.signUp_password);
        regemail = findViewById(R.id.signUp_email);
        regphoneNo = findViewById(R.id.signUp_phoneNo);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        regnext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

//                rootNode = FirebaseDatabase.getInstance(); //will be calling to the top node
//                reference=rootNode.getReference("users");

                //get all the values
                String fullname = regfullName.getEditText().getText().toString();
                String username= regusername.getEditText().getText().toString();
                String email = regemail.getEditText().getText().toString();
                String password = regpassword.getEditText().getText().toString();
                String phoneNo = regphoneNo.getEditText().getText().toString();

//                //call the helper class and get all the values
//                UserHelperClass helperClass = new UserHelperClass(fullname, username, email, password, phoneNo);
//
//                //set the value by user's phone number
//                //assign this reference
//                reference.child(phoneNo).setValue(helperClass);








                fAuth.createUserWithEmailAndPassword(regemail.getEditText().getText().toString(),regpassword.getEditText().getText().toString()).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if(task.isSuccessful()){

                            Toast.makeText(SignUp.this, "Registered Succesfully!", Toast.LENGTH_SHORT).show();
                            userID = fAuth.getCurrentUser().getUid();
                            DocumentReference documentReference = fStore.collection("users").document(userID);
                            Map<String,Object> user = new HashMap<>();
                            user.put("fName",fullname);
                            user.put("userName",username);
                            user.put("eMail",email);
                            user.put("passWord",password);
                            user.put("phone",phoneNo);
                            documentReference.set(user).addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {
                                    Log.d(TAG, "onSuccess: User Profile is created for" + userID);
                                }
                            });
                            startActivity(new Intent(getApplicationContext(),homepage.class));
                        }else{
                            Toast.makeText(SignUp.this,"Error"+ task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });


//                if(!validateFullName() | !validateUsername() |!validateEmail() | !validatePass()){
//                    return;
//                }







//                Add Transition
//                Pair[] pairs = new Pair[4];
//
//                pairs[0] = new Pair<View, String>(regbackBtn, "transition_back_to_welcome_screen");
//                pairs[1] = new Pair<View, String>(regnext, "transition_next_button");
//                pairs[2] = new Pair<View, String>(reglogin, "transition_login_button");
//                pairs[3] = new Pair<View, String>(regtitleText, "transition_title_text");
//
//                //call the activity options
//                if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.LOLLIPOP) { //use the API level or higher
//                ActivityOptions options = ActivityOptions.makeSceneTransitionAnimation(SignUp.this, pairs);
//                startActivity(intent, options.toBundle()); //start the activity by intent and pass the animation (options.toBundle)
//                }
//                startActivity(intent);
            }
        });
    }



    //return true or false
    private boolean validateFullName() {
        //create string to get user's value of fullname
        //as we are using material design and assign id to layout, need to define getedittext,
        // trim to ensure there's no spaces that will be stored into the database
        String val = regfullName.getEditText().getText().toString().trim();

        //check the value is empty or not
        if (val.isEmpty()) {
            //seterror is build in fx
            regfullName.setError("Field cannot be empty");
            return false;
        } else {
            //null will automatically remove the error
            regfullName.setError(null);
            //the error for the material design is going to take some space, remove the field and spaces
            regfullName.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateUsername() {
        //create string to get user's value of fullname
        //as we are using material design and assign id to layout, need to define getedittext,
        // trim to ensure there's no spaces that will be stored into the database
        String val = regusername.getEditText().getText().toString().trim();
        //check spaces
        //"\\A\\w{4,20}\\z" means the username should have minimum of 4 & max of 20 char
        // w/o spaces at the start or in the end
        //Any capital A to small z
        String checkspaces = "\\A\\w{1,20}\\z";

        //check the value is empty or not
        if (val.isEmpty()) {
            //seterror is build in fx
            regusername.setError("Field cannot be empty");
            return false;
        } else if(val.length()>20){
            regusername.setError("Username is too long");
            return false;

            //if value entered is not matched with the define method, error will appear
        }else if(!val.matches(checkspaces)){
            regusername.setError("No spaces are allowed");
            return false;
        }
        else {
            //null will automatically remove the error
            regusername.setError(null);
            //the error for the material design is going to take some space, remove the field and spaces
            regusername.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validateEmail() {
        //create string to get user's value of fullname
        //as we are using material design and assign id to layout, need to define getedittext,
        // trim to ensure there's no spaces that will be stored into the database
        String val = regemail.getEditText().getText().toString().trim();
        //check spaces
        //"\\A\\w{4,20}\\z" means the username should have minimum of 4 & max of 20 char
        // w/o spaces at the start or in the end
        //Any capital A to small z
        String checkEmail = "[a-zA-z0-9._-]+@[a-z]+\\.+[a-z]+";

        //check the value is empty or not
        if (val.isEmpty()) {
            //seterror is build in fx
            regemail.setError("Field cannot be empty");
            return false;
        }
        //if value entered is not matched with the define method, error will appear
        else if(!val.matches(checkEmail)){
            regemail.setError("Invalid Email");
            return false;
        }
        else {
            //null will automatically remove the error
            regemail.setError(null);
            //the error for the material design is going to take some space, remove the field and spaces
            regemail.setErrorEnabled(false);
            return true;
        }
    }

    private boolean validatePass() {
        //create string to get user's value of fullname
        //as we are using material design and assign id to layout, need to define getedittext,
        // trim to ensure there's no spaces that will be stored into the database
        String val = regpassword.getEditText().getText().toString().trim();
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
            regpassword.setError("Field cannot be empty");
            return false;
        }
        //if value entered is not matched with the define method, error will appear
        else if(!val.matches(checkPass)){
            regpassword.setError("Password should contain 4 characters");
            return false;
        }
        else {
            //null will automatically remove the error
            regpassword.setError(null);
            //the error for the material design is going to take some space, remove the field and spaces
            regpassword.setErrorEnabled(false);
            return true;
        }
    }

    public void gobacktoSignIn(View view) {
        Intent intent = new Intent(getApplicationContext(),SUPorSUPWG.class);
        startActivity(intent);
    }


}