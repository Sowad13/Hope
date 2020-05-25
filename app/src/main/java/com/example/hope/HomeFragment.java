package com.example.hope;

import android.content.Intent;
import android.media.Image;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.firebase.auth.FirebaseAuth;

public class HomeFragment extends Fragment {

    ImageButton BotChat;
    Button logout,videocall;
    FirebaseAuth mAuth;
    CustomLoadingBar loadingBar;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v =inflater.inflate(R.layout.fragment_home,container,false);

        BotChat = v.findViewById(R.id.chatbot_btn);
        logout = v.findViewById(R.id.logout_btn);
        videocall = v.findViewById(R.id.video_call);
        mAuth = FirebaseAuth.getInstance();
        loadingBar = new CustomLoadingBar(getActivity());


        videocall.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),VideoCallActivity.class);
                startActivity(intent);
            }
        });
        BotChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                Intent intent = new Intent(getContext(),ChatBotActivity.class);
                startActivity(intent);
            }
        });

        logout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                loadingBar.StartLoadingDialog();
                mAuth.signOut();
                loadingBar.DismissLoadingDialog();
                Intent intent = new Intent(getActivity(),secondpage.class);
                startActivity(intent);
                getActivity().finish();









            }
        });


        return v;



    }
}
