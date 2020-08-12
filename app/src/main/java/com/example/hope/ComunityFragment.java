package com.example.hope;
import android.content.Intent;
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

import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;


public class ComunityFragment extends Fragment {


    FloatingActionButton floatingActionButton;
    RecyclerView postRecyclerview;
    post_adapter Adapterpost;
    List<detailpost> mData;



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
        mData = new ArrayList<>();


        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"hi","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.owlf,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"No","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.hope,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"go","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.owlf,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"Hello","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.hope,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"hi","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.owlf,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"No","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.hope,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"go","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.owlf,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"OnePlus 6T Camera Review:","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.creat,0));
        mData.add(new detailpost(detailpost.TEXT_TYPE,"Hello","Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam,",R.drawable.owlf,0));


        Adapterpost = new post_adapter (getActivity(),mData);
        postRecyclerview.setAdapter(Adapterpost);
        postRecyclerview.setLayoutManager(new LinearLayoutManager(getActivity()));





        return v;

    }

}
