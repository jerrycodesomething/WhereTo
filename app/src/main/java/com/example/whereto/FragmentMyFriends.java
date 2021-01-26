package com.example.whereto;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.firestore.FirestoreRecyclerAdapter;
import com.firebase.ui.firestore.FirestoreRecyclerOptions;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.Query;

public class FragmentMyFriends extends Fragment {

    //class components
    public RecyclerView mFriendList;

    //database
    FirebaseFirestore fStore;
    String userID;
    FirebaseAuth fAuth;
    FirestoreRecyclerAdapter adapter;

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_my_friends, container, false);

        mFriendList = view.findViewById(R.id.recyclerView);
        fAuth = FirebaseAuth.getInstance();
        fStore = FirebaseFirestore.getInstance();

        //query
        Query query = fStore.collection("users").orderBy("fName", Query.Direction.ASCENDING);

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
                holder.list_username.setText(model.getfName());
            }
        };
        mFriendList.hasFixedSize();
        mFriendList.setLayoutManager(new LinearLayoutManager(getActivity()));
        mFriendList.setAdapter(adapter);

        return view;
    }

    public class FriendsViewHolder extends RecyclerView.ViewHolder {
        public TextView list_email;
        public TextView list_username;
        public FriendsViewHolder(@NonNull View itemView) {
            super(itemView);
            list_email = itemView.findViewById(R.id.listEmail);
            list_username = itemView.findViewById(R.id.listFullName);
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        adapter.stopListening();
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter.startListening();
    }

}



