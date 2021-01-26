package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

public class AccountStatusDisableorDeleteConfirmation extends AppCompatActivity {

    EditText usersAnswer;
    Button button;
    String mText;

    StorageReference storageReference;
    FirebaseUser user;
    FirebaseAuth fAuth;
    FirebaseFirestore fStore;
    String userId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account_status_disableor_delete_confirmation);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        storageReference = FirebaseStorage.getInstance().getReference();
        user = fAuth.getCurrentUser();

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
                    DocumentReference documentReference = fStore.collection("users").document(userId);
                    documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void aVoid) {
                            final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

                            // Get auth credentials from the user for re-authentication. The example below shows
                            // email and password credentials but there are multiple possible providers,
                            // such as GoogleAuthProvider or FacebookAuthProvider.
                            AuthCredential credential = EmailAuthProvider
                                    .getCredential("user@example.com", "password1234");

                            // Prompt the user to re-provide their sign-in credentials
                            user.reauthenticate(credential)
                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                        @Override
                                        public void onComplete(@NonNull Task<Void> task) {
                                            user.delete()
                                                    .addOnCompleteListener(new OnCompleteListener<Void>() {
                                                        @Override
                                                        public void onComplete(@NonNull Task<Void> task) {
                                                            if (task.isSuccessful()) {
                                                                Toast.makeText(AccountStatusDisableorDeleteConfirmation.this, "Your account has been deleted!", Toast.LENGTH_SHORT).show();
                                                            }
                                                        }
                                                    });
                                        }
                                    });
                            Intent intent = new Intent(AccountStatusDisableorDeleteConfirmation.this, WelcomeScreen.class);
                            startActivity(intent);
                        }
                    }).addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(AccountStatusDisableorDeleteConfirmation.this, "Failed to " + mText +" this account", Toast.LENGTH_SHORT).show();
                        }
                    });

                }
            }
        });
    }
}