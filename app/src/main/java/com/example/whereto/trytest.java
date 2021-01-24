package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.app.Activity;
import android.content.ContentResolver;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.view.View;
import android.webkit.MimeTypeMap;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

public class trytest extends AppCompatActivity {

    private Button uploadBtn, showAllbtn;
    private ImageView imageView;
    private ProgressBar progressBar;
    private StorageReference reference = FirebaseStorage.getInstance().getReference();
    DatabaseReference root;
    private Uri imageUri;
    FirebaseFirestore fStore;
    String userId;
    FirebaseAuth fAuth;
    FirebaseUser user;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_trytest);


        uploadBtn = findViewById(R.id.upload);
        showAllbtn = findViewById(R.id.showall);
        progressBar = findViewById(R.id.progressBar);
        imageView = findViewById(R.id.imageView);
        progressBar.setVisibility(View.INVISIBLE);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        imageView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent galleryIntent = new Intent();
                galleryIntent.setAction(Intent.ACTION_GET_CONTENT);
                galleryIntent.setType("image/*");
                startActivityForResult(galleryIntent, 2);
            }
        });
        uploadBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(imageUri !=null){
                    uploadtoFirebase1(imageUri);
                }else{
                    Toast.makeText(trytest.this, "Please select image", Toast.LENGTH_SHORT).show();
                }
            }
        });

        showAllbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(getApplicationContext(),ImagesActivity.class));
            }
        });

    }

    //method to upload photo to Firebase
    private void uploadtoFirebase1(Uri uri) {
        StorageReference fileRef = reference.child("usersVisited/" + fAuth.getCurrentUser().getUid() + "/"+ (System.currentTimeMillis() + "." + getFileExtension(uri)));
        fileRef.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                fileRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        root = FirebaseDatabase.getInstance().getReference("UsersVisited/" + userId);
                        String uploadId = root.push().getKey();
                        Upload upload = new Upload(uri.toString());
                        root.child(uploadId).setValue(upload);
                    }
                });
            }
        }).addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onProgress(@NonNull UploadTask.TaskSnapshot snapshot) {
                progressBar.setVisibility(View.VISIBLE);
            }
        }).addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception e) {
                progressBar.setVisibility(View.VISIBLE);
                Toast.makeText(trytest.this, "Uploading Failed", Toast.LENGTH_SHORT).show();
            }
        });
    }

    //will return selected image types of extension
    private String getFileExtension(Uri mUri){
        ContentResolver cr = getContentResolver();
        MimeTypeMap mime = MimeTypeMap.getSingleton();
        return mime.getExtensionFromMimeType(cr.getType(mUri));
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if(requestCode ==2 && resultCode == RESULT_OK && data != null){
            imageUri = data.getData();
            imageView.setImageURI(imageUri);

        }
    }



}