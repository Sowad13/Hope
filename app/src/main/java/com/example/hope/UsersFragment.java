package com.example.hope;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class UsersFragment extends Fragment {


    private RecyclerView recyclerView;

    private  UserAdapter userAdapter;

    private List<Users> mUsers;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_users,container,false);




        mUsers = new ArrayList<Users>();


        //mUsers.add(new Users("Nushraat",R.mipmap.ic_launcher,R.layout.user_item));
        //mUsers.add(new Users("Sowad",R.mipmap.smallpanda,R.layout.user_item));
        //mData.add(new detailpost(detailpost.TEXT_TYPE,"Feeling heavy","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.owlf,0));
        //mData.add(new detailpost(detailpost.TEXT_TYPE,"Feeling Happy","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));

        recyclerView = view.findViewById(R.id.recycler_view_users);
        recyclerView.setHasFixedSize(true);
        userAdapter = new UserAdapter(getContext(), mUsers);
        recyclerView.setAdapter(userAdapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        //readUsers();

        return view;

    }

    private void readUsers() {

        final FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();
        final DatabaseReference databaseReference = FirebaseDatabase.getInstance().getReference("Users").child(firebaseUser.getUid());

        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mUsers.clear();
                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Users user = snapshot.getValue(Users.class);

                    assert user != null;
                    assert firebaseUser != null;
                    if (user.getUsername().equals(firebaseUser.getDisplayName())){
                        mUsers.add(user);
                    }
                }

                userAdapter = new UserAdapter(getContext(), mUsers);
                recyclerView.setAdapter(userAdapter);
                recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

    }
}









