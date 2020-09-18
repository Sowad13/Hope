package com.example.hope;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.List;

import static com.example.hope.detailpost.IMAGE_TYPE;
import static com.example.hope.detailpost.TEXT_TYPE;

public  class post_adapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    Context mContext;
    List<detailpost> mData ;
    int total_types;
    private ViewGroup viewGroup;
    private int i;
    ConstraintLayout container;
    RelativeLayout imgcontainer;
    FirebaseUser firebaseUser = FirebaseAuth.getInstance().getCurrentUser();;

    public post_adapter(Context mContext, List<detailpost> mData) {
        this.mContext = mContext;
        this.mData = mData;
        total_types = mData.size();


    }

    @Override
    public int getItemViewType(int position) {

        switch (mData.get(position).type) {
            case 0:
                return TEXT_TYPE;
            case 1:
                return IMAGE_TYPE;

            default:
                return -1;
        }
    }

    @NonNull
    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int viewType) {

        View layout;

        if(viewType == TEXT_TYPE){

            layout = LayoutInflater.from(mContext).inflate(R.layout.post,viewGroup,false);
            return new textviewpost(layout);
        }

        else if (viewType == IMAGE_TYPE){

            layout = LayoutInflater.from( mContext ).inflate( R.layout.image_view_story,viewGroup,false );
            return  new imageviewpost( layout );
        }

        return null;

    }

    @Override
    public void onBindViewHolder(@NonNull final RecyclerView.ViewHolder holder, int position) {


        final detailpost object = mData.get(position);
        if (object != null) {
            switch (object.type) {
                case TEXT_TYPE:
                    ((textviewpost) holder).heading.setText(object.getTitle());
                    ((textviewpost) holder).userName.setText(object.getUsernam());
                    ((textviewpost) holder).content.setText(object.getDescription());

                    Glide.with(mContext).load(object.getUserdp()).into(((textviewpost) holder).userprofile);

                    container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.fading_animation));

                    setlikeButton( object.getPostKey() ,((textviewpost)holder).like );
                    setlikecounter( ((textviewpost)holder).likesnumber,object.getPostKey() );

                    ((textviewpost)holder).like.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((textviewpost)holder).like.getTag().equals("Like")) {
                                FirebaseDatabase.getInstance().getReference().child("Likes").child( object.getPostKey() ).child( firebaseUser.getUid() ).setValue( true );
                                Toast.makeText(mContext,"post liked", Toast.LENGTH_LONG).show();

                            }
                            else{
                                FirebaseDatabase.getInstance().getReference().child("Likes").child(object.getPostKey() ).child( firebaseUser.getUid() ).removeValue();
                            }
                        }
                    } );

                    break;

                case IMAGE_TYPE:
                     ((imageviewpost) holder).imgheading.setText(object.getTitle());
                    ((imageviewpost) holder).userName.setText(object.getUsernam());

                    Glide.with(mContext).load(object.getImgUpload()).into(((imageviewpost) holder).picupload);

                    ((imageviewpost) holder).imgcontent.setText( object.getDescription() );
                    Glide.with(mContext).load(object.getUserdp()).into(((imageviewpost) holder).ivuserprofile);

                    imgcontainer.setAnimation( AnimationUtils.loadAnimation( mContext,R.anim.fading_animation ) );

                    setlikeButton( object.getPostKey() ,((imageviewpost)holder).ivlike );
                    setlikecounter( ((imageviewpost)holder).ivlikenumber,object.getPostKey() );

                    ((imageviewpost)holder).ivlike.setOnClickListener( new View.OnClickListener() {
                        @Override
                        public void onClick(View v) {
                            if(((imageviewpost)holder).ivlike.getTag().equals("Like")) {
                                FirebaseDatabase.getInstance().getReference().child("Likes").child( object.getPostKey() ).child( firebaseUser.getUid() ).setValue( true );

                            }
                            else{
                                FirebaseDatabase.getInstance().getReference().child("Likes").child(object.getPostKey() ).child( firebaseUser.getUid() ).removeValue();
                            }
                        }
                    } );

                    break;

            }
        }

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }



    public class textviewpost extends RecyclerView.ViewHolder {

         TextView heading;
         TextView content;
         TextView userName;
         ImageView userprofile;
         TextView likesnumber;
         ImageView like;



        public textviewpost(@NonNull final View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.constraint);
            heading = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.description);
            userprofile = itemView.findViewById( R.id.profilepic);
            userName = itemView.findViewById( R.id.nameuser );
            likesnumber = itemView.findViewById( R.id.likescounter );
            like = itemView.findViewById( R.id.likebutton );

        }
    }

    public  class imageviewpost extends RecyclerView.ViewHolder {

        TextView imgheading;
        TextView imgcontent;
        TextView userName;
        ImageView ivuserprofile;
        ImageView picupload;
        RelativeLayout imgcoverTitle;
        ImageView ivlike;
        TextView ivlikenumber;


        public imageviewpost(@NonNull View itemView) {
            super( itemView );

            imgcontainer = itemView.findViewById(R.id.relativeimgview);
            imgheading = itemView.findViewById(R.id.imgTitle);
            userName = itemView.findViewById( R.id.username );
            imgcontent = itemView.findViewById(R.id.imgDescription);
            ivuserprofile = itemView.findViewById( R.id.userdp);
            picupload = itemView.findViewById( R.id.uploadedimg );
            ivlike = itemView.findViewById( R.id.ivlikebutton );
            ivlikenumber = itemView.findViewById( R.id.likesnum );

           // imgcoverTitle = itemView.findViewById( R.id.relativeTitle );

        }

    }

    private void setlikeButton (String PostKey, final ImageView likeImageview){
        DatabaseReference likeReference = FirebaseDatabase.getInstance().getReference().child("Likes").child( PostKey );
        final FirebaseUser user = FirebaseAuth.getInstance().getCurrentUser();

        likeReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                if(dataSnapshot.child( user.getUid()).exists()){
                    likeImageview.setImageResource( R.drawable.redtrust );
                    likeImageview.setTag( "Liked" );
                }
                else{
                    likeImageview.setImageResource( R.drawable.blanktrust );
                    likeImageview.setTag( "Like" );
                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }

    private  void setlikecounter(final TextView likeCounter,String PostKey){

        DatabaseReference likeReference = FirebaseDatabase.getInstance().getReference().child("Likes").child( PostKey);
        likeReference.addValueEventListener( new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                likeCounter.setText( dataSnapshot.getChildrenCount()+"" );

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        } );

    }


}
