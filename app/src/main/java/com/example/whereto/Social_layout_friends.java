package com.example.whereto;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.Query;


public class Social_layout_friends extends AppCompatActivity{
    public static final String TAG = "TAG";

    //widgets and view
    private ImageButton btn;
    private RecyclerView mFriendList;

    //database
    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth fAuth;
    FirestoreRecyclerAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_social_layout_friends);
        btn = (ImageButton) findViewById (R.id.backProfileButton);

        mFriendList = findViewById(R.id.recyclerView);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();


        //query
        Query query =fStore.collection("users");
        
        //recycler options
        FirestoreRecyclerOptions<FriendListModel> options = new FirestoreRecyclerOptions.Builder<FriendListModel>()
                .setQuery(query, FriendListModel.class)
                .build();

        adapter = new FirestoreRecyclerAdapter<FriendListModel, FriendsViewHolder>(options) {
            @NonNull
            @Override
            public FriendsViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
                View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.friendlist_item, parent, false);
                return new FriendsViewHolder(view);
            }

            @Override
            protected void onBindViewHolder(@NonNull FriendsViewHolder holder, int position, @NonNull FriendListModel model) {
                holder.list_email.setText(model.geteMail());
                holder.list_username.setText(model.getUserName());

            }
        };
        mFriendList.setHasFixedSize(true);
        mFriendList.setLayoutManager(new LinearLayoutManager(this));
        mFriendList.setAdapter(adapter);

    }

    private class FriendsViewHolder extends RecyclerView.ViewHolder {

        private TextView list_email;
        private TextView list_username;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);

            list_email = itemView.findViewById(R.id.listEmail);
            list_username = itemView.findViewById(R.id.listUsername);
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    protected void onStart() {
        super.onStart();
        adapter.startListening();
    }

    public void BTP(View view){

        Intent intent =new Intent();
        intent.setClass(Social_layout_friends.this,Profile.class);
        startActivity(intent);
    }
}
