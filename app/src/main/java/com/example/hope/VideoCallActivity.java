package com.example.hope;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.net.Uri;
import android.os.Bundle;
import android.widget.VideoView;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class VideoCallActivity extends AppCompatActivity {


    DatabaseReference userRef;

    String st;

    VideoView videoView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_video_call);

        videoView = findViewById(R.id.videoView);

        userRef = FirebaseDatabase.getInstance().getReference();


      userRef.addValueEventListener(new ValueEventListener() {
          @Override
          public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

              st = dataSnapshot.child("webinar").getValue().toString();

              if(st=="");
              else
              {
                  Uri videouri = Uri.parse(st);
                  videoView.setVideoURI(videouri);
                  videoView.requestFocus();
                  videoView.start();
              }


          }

          @Override
          public void onCancelled(@NonNull DatabaseError databaseError) {

          }
      });











    }





}