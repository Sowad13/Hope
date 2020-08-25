package com.example.hope;

import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AnimationUtils;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.Switch;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

import static android.media.CamcorderProfile.get;
import static com.example.hope.detailpost.IMAGE_TYPE;
import static com.example.hope.detailpost.TEXT_TYPE;

public  class post_adapter extends RecyclerView.Adapter <RecyclerView.ViewHolder>{

    Context mContext;
    List<detailpost> mData ;
    int total_types;
    private ViewGroup viewGroup;
    private int i;

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
    public void onBindViewHolder(@NonNull RecyclerView.ViewHolder holder, int position) {


        detailpost object = mData.get(position);
        if (object != null) {
            switch (object.type) {
                case TEXT_TYPE:
                    ((textviewpost) holder).heading.setText(object.title);
                    ((textviewpost) holder).content.setText(object.description);

                    //((textviewpost) holder).userprofile.setImageResource( object.userdp);
                    Glide.with(mContext).load(mData.get(position).getUserdp()).into(((textviewpost) holder).userprofile);

                    textviewpost.container.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.transitionanimation));



                    break;

                case IMAGE_TYPE:
                      ((imageviewpost) holder).imgheading.setText(object.title);

                      //  ((imageviewpost) holder).imgupload.setImageResource(object.imgUpload);
                    Glide.with(mContext).load(mData.get(position).getImgUpload()).into(((imageviewpost) holder).picupload);

                    ((imageviewpost) holder).imgcontent.setText( object.description );

                     // ((imageviewpost)holder).ivuserprofile.setImageResource( object.userdp );
                    Glide.with(mContext).load(mData.get(position).getUserdp()).into(((imageviewpost) holder).ivuserprofile);

                    imageviewpost.imgcontainer.setAnimation( AnimationUtils.loadAnimation( mContext,R.anim.transitionanimation ) );

                    break;

            }
        }


        /*holder.title.setText(mData.get(position).getTitle());
        Glide.with(mContext).load(mData.get(position).getPicture()).into(holder.addimg);
        Glide.with(mContext).load(mData.get(position).getUserPhoto()).into(holder.userimg);*/

    }

    @Override
    public int getItemCount() {

        return mData.size();
    }



    public static class textviewpost extends RecyclerView.ViewHolder {

         TextView heading;
         TextView content;
         ImageView userprofile;
     //    TextView likesnumber;
         ImageView like;
         static ConstraintLayout container;


        public textviewpost(@NonNull final View itemView) {
            super(itemView);

            container = itemView.findViewById(R.id.constraint);
            heading = itemView.findViewById(R.id.title);
            content = itemView.findViewById(R.id.description);
            userprofile = itemView.findViewById( R.id.profilepic);
            //likesnumber = itemView.findViewById( R.id.likescounter );
            // like = itemView.findViewById( R.id.likebutton );


        }
    }


    public static class imageviewpost extends RecyclerView.ViewHolder {

        TextView imgheading;
        TextView imgcontent;
        ImageView ivuserprofile;
        ImageView picupload;
        static RelativeLayout imgcontainer;
        static RelativeLayout imgcoverTitle;



        public imageviewpost(@NonNull View itemView) {
            super( itemView );

            imgcontainer = itemView.findViewById(R.id.relativeimgview);
            imgheading = itemView.findViewById(R.id.imgTitle);
            imgcontent = itemView.findViewById(R.id.imgDescription);
            ivuserprofile = itemView.findViewById( R.id.userdp);
            picupload = itemView.findViewById( R.id.uploadedimg );

           // imgcoverTitle = itemView.findViewById( R.id.relativeTitle );
        }
    }
}
