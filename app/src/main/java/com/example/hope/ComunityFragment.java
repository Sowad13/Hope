package com.example.hope;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.firebase.ui.database.FirebaseRecyclerAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.MutableData;
import com.google.firebase.database.Transaction;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;


public class ComunityFragment extends Fragment {


    private OnFragmentInteractionListener mListener;
    FirebaseDatabase database;
    DatabaseReference databaseReference;
    FirebaseAuth mAuth;
    FirebaseUser user;

    FloatingActionButton floatingActionButton;
    RecyclerView postRecyclerview;
    post_adapter Adapterpost;
    List<detailpost> PostData;
    String UserId;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_community, container, false);


        floatingActionButton = v.findViewById(R.id.fabbutton);
        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),Adding_post.class);
                startActivity(intent);

            }
        });




        postRecyclerview = v.findViewById(R.id.recycler);
        postRecyclerview.setHasFixedSize(true);
        PostData = new ArrayList<>();

        mAuth = FirebaseAuth.getInstance();
        user = mAuth.getCurrentUser();
        UserId = user.getUid();
        database = FirebaseDatabase.getInstance();
        databaseReference = database.getReference("Posts");
        postRecyclerview.setLayoutManager(new LinearLayoutManager(getContext()));

        return v;
    }

    @Override
    public void onStart() {
        super.onStart();


        databaseReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                PostData.clear();
                List<String> keys = new ArrayList<>(  );

                for(DataSnapshot keyNode : dataSnapshot.getChildren()){

                    keys.add( keyNode.getKey() );
                    detailpost detailpost = keyNode.getValue( detailpost.class );
                    PostData.add( detailpost );
                }

                Adapterpost = new post_adapter (getContext(),PostData);
                postRecyclerview.setAdapter(Adapterpost);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );


    }

    public void onButtonPressed(Uri uri) {
        if (mListener != null) {
            mListener.onFragmentInteraction(uri);
        }
    }

    @Override
    public void onAttach(@NonNull Context context) {
        super.onAttach( context );
    }

    @Override
    public void onDetach() {
        super.onDetach();

        mListener = null;
    }

    public interface OnFragmentInteractionListener {

        void onFragmentInteraction(Uri uri);
    }

}
