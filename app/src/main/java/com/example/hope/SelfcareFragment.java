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

public class SelfcareFragment extends Fragment {


    Button storybutton,yogabutton;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_selfcare,container,false);

        storybutton =v.findViewById( R.id.newsbutton );
        storybutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                website("https://www.add.org.uk/mental-health-story");
            }
        } );

        yogabutton = v.findViewById( R.id.yogabutton );
        yogabutton.setOnClickListener( new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                website( "https://www.yogajournal.com/practice/yoga-for-inner-peace-stress-relief-daily-practice-challenge#gid=ci0207569e402525bd&pid=colleen-saidman-yee-performs-savasana-with-blocks-on-head" );
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
