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
    String linkone,linktwo,linkthree,linkfour,linkfive;

    Button storybutton,twostorybutton,s,t,u,z,w;
    Button three,four,five;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selfcare,container,false);

        websitereference = FirebaseDatabase.getInstance().getReference();

        s = v.findViewById( R.id.a );
        s.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),first_yoga.class);
                startActivity(intent);
            }
        } );

        t = v.findViewById( R.id.b);
        t.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),first_yoga.class);
                startActivity(intent);
            }
        } );


        u = v.findViewById( R.id.c );
        u.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),first_yoga.class);
                startActivity(intent);
            }
        } );
        z= v.findViewById( R.id.d );
        z.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),first_yoga.class);
                startActivity(intent);
            }
        } );
        w = v.findViewById( R.id.e );
        w.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),first_yoga.class);
                startActivity(intent);
            }
        } );


        storybutton =v.findViewById( R.id.twobutton );
        twostorybutton = v.findViewById( R.id.onebutton );
        three = v.findViewById( R.id.threebutton);
        four = v.findViewById( R.id.fourbutton );
        five = v.findViewById( R.id.fivebutton );

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

                linkthree = dataSnapshot.child("websites").child( "linkthree" ).getValue().toString();
               three.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        website(  linkthree);
                    }
                } );

                linkfour = dataSnapshot.child("websites").child( "linkfour" ).getValue().toString();
                four.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        website(  linkfour);
                    }
                } );

                linkfive = dataSnapshot.child("websites").child( "linkfive" ).getValue().toString();
                five.setOnClickListener( new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        website(  linkfive);
                    }
                } );

            }



            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

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
