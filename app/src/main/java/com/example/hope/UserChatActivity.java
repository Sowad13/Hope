package com.example.hope;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import com.example.hope.Adapter.MessageAdapter;
import com.example.hope.Model.Chats;
import com.example.hope.Model.Users;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class UserChatActivity extends AppCompatActivity {

    ImageView profileImage;
    TextView username;

    ImageButton sendmsg;
    EditText typemsg;

    FirebaseUser fuser;
    DatabaseReference reference;

    MessageAdapter messageAdapter;

    List<Chats> mChat;
    RecyclerView recyclerView;




    Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_chat);

        /*Toolbar toolbar = findViewById(R.id.msg_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("");
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        toolbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });*/


        recyclerView = findViewById(R.id.recycler_view_msg);
        recyclerView.setHasFixedSize(true);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getApplicationContext());
        linearLayoutManager.setStackFromEnd(true);
        recyclerView.setLayoutManager(linearLayoutManager);

        profileImage = findViewById(R.id.profile_image);
        username = findViewById(R.id.user_name);
        sendmsg = findViewById(R.id.btn_msgsend);
        typemsg = findViewById(R.id.typemessage);

        intent = getIntent();
        final String userid = intent.getStringExtra("userid");
        fuser = FirebaseAuth.getInstance().getCurrentUser();

        sendmsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String msg = typemsg.getText().toString();
                if (!msg.equals("")){
                    sendMessage(fuser.getUid(),userid,msg);
                }else {
                    Toast.makeText(UserChatActivity.this, "Message Field Empty",Toast.LENGTH_SHORT).show();
                }
                typemsg.setText("");
            }
        });


        reference = FirebaseDatabase.getInstance().getReference("Users").child(userid);

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                Users user = dataSnapshot.getValue(Users.class);
                /*username.setText(user.getUsername());

                if(user.getImageURL().equals("default")){
                    profileImage.setImageResource(R.mipmap.ic_launcher);

                }else{
                    Glide.with(UserChatActivity.this).load(user.getImageURL()).into(profileImage);
                }*/

                readMessage(fuser.getUid(),userid, user.getImageURL());

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    private void sendMessage(String sender, String receiver,String message){

        DatabaseReference reference = FirebaseDatabase.getInstance().getReference();

        HashMap<String, Object> hashMap = new HashMap<>();
        hashMap.put("sender",sender);
        hashMap.put("receiver",receiver);
        hashMap.put("message",message);

        reference.child("Chats").push().setValue(hashMap);
    }

    private void readMessage(final String myId, final String userId, final String imageurl){
        mChat = new ArrayList<>();

        reference = FirebaseDatabase.getInstance().getReference("Chats");
        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                mChat.clear();

                for (DataSnapshot snapshot: dataSnapshot.getChildren()){
                    Chats chat = snapshot.getValue(Chats.class);
                    if (chat.getReceiver().equals(myId) && chat.getSender().equals(userId)||  chat.getReceiver().equals(userId) && chat.getSender().equals(myId)){
                        mChat.add(chat);

                    }

                    messageAdapter = new MessageAdapter(UserChatActivity.this,mChat,imageurl);
                    recyclerView.setAdapter(messageAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }
}
