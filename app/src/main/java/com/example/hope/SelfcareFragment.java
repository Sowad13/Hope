package com.example.hope;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SelfcareFragment extends Fragment {

    DatabaseReference websitereference;
    String linkone,linktwo;

    Button storybutton,twostorybutton,s,t,u,v,w;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selfcare,container,false);

        websitereference = FirebaseDatabase.getInstance().getReference();

        storybutton =v.findViewById( R.id.newsbutton );
        twostorybutton = v.findViewById( R.id.onebutton );

        websitereference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                linkone = dataSnapshot.child("websites").child( "linkOne" ).getValue().toString();
                storybutton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {

                        website(linkone);
                    }
                } );

                linktwo = dataSnapshot.child("websites").child( "linkTwo" ).getValue().toString();
                twostorybutton.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        website(  linktwo);
                    }
                } );
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

        s = v.findViewById( R.id.a );
        s.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),first_yoga.class);
                startActivity(intent);
            }
        } );



        return v;
    }

    private void website(String url) {

        Intent webintent = new Intent(Intent.ACTION_VIEW);
        webintent.setData(Uri.parse(url));
        startActivity(webintent);
    }
}
