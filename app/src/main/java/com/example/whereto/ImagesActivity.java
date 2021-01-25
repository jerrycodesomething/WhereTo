package com.example.whereto;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.ContentResolver;
import android.net.Uri;
import android.os.Bundle;
import android.webkit.MimeTypeMap;
import android.widget.Toast;

import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.ArrayList;
import java.util.List;

public class ImagesActivity extends AppCompatActivity implements ImageAdapter.OnItemClickListener {

    //recycler view variable
    private RecyclerView recyclerView;
    private ImageAdapter imageAdapter;


    //database reference
    private ValueEventListener mDBListener;
    private FirebaseStorage mStorage;
    private DatabaseReference root;
    private StorageReference reference;
    FirebaseFirestore fStore;
    String userId;
    FirebaseAuth fAuth;
    FirebaseUser user;
    private Uri imageUri;

    // List for the photo
    private List<Upload> mUpload;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_images);

        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();
        userId = fAuth.getCurrentUser().getUid();
        user = fAuth.getCurrentUser();

        recyclerView =findViewById(R.id.recycle_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        StorageReference mStorage = FirebaseStorage.getInstance().getReference("usersVisited/" + fAuth.getCurrentUser().getUid() + "/"+ (System.currentTimeMillis()));

        mUpload = new ArrayList<>();
        //below array list because we need to pass array list to the adapter(mUploads)
        imageAdapter = new ImageAdapter(ImagesActivity.this,mUpload);
        recyclerView.setAdapter(imageAdapter);
        imageAdapter.setOnItemClickListener(ImagesActivity.this);


        root = FirebaseDatabase.getInstance().getReference("UsersVisited/" + userId);
        mDBListener= root.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                //to avoid redundant images in the firebase
                mUpload.clear();
                for(DataSnapshot postSnapshot : snapshot.getChildren()){
                    Upload upload = postSnapshot.getValue(Upload.class);
                    upload.setKey(postSnapshot.getKey());
                    mUpload.add(upload);
                }

                imageAdapter.notifyDataSetChanged();

            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Toast.makeText(ImagesActivity.this,error.getMessage(),Toast.LENGTH_SHORT).show();

            }
        });

    }

    @Override
    public void onItemClick(int position) {

    }

    @Override
    public void onDoNothingClick(int position) {

    }

    @Override
    public void onDeleteClick(int position) {
        Upload selectedItem = mUpload.get(position);
        String selectedKey = selectedItem.getKey();

        //StorageReference fileRef = reference.child("usersVisited/" + fAuth.getCurrentUser().getUid() + "/"+ (System.currentTimeMillis()));

        StorageReference imageRef = mStorage.getReferenceFromUrl(selectedItem.getmImageUrl());
        imageRef.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void aVoid) {
                root.child(selectedKey).removeValue();
                Toast.makeText(ImagesActivity.this, "Item deleted", Toast.LENGTH_SHORT).show();
            }
        });

    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        root.removeEventListener(mDBListener);
    }

}