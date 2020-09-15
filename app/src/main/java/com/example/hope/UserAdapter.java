package com.example.hope;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.List;

public class UserAdapter extends RecyclerView.Adapter<UserAdapter.ViewHolder> {

    private Context mContext;
    private List<Users> mUsers;

    public UserAdapter(Context mContext, List<Users> mUsers) {
        this.mUsers = mUsers;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {


        View layout;

            layout = LayoutInflater.from(mContext).inflate(R.layout.user_item,parent,false);
            return new ViewHolder(layout);



    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        //Users object = mUsers.get(position);


        //Users user = mUsers.get(position);
        //holder.username.setText(object.getUsername());
        //((ViewHolder) holder).username.setText(object.username);
        //((ViewHolder) holder).profileImage.setImageResource( object.imageURL);


        final Users user = mUsers.get(position);
        holder.username.setText(user.getUsername());



       // ViewHolder.userItem.setAnimation(AnimationUtils.loadAnimation(mContext,R.anim.transitionanimation));


        if (user.getImageURL().equals("default"))
        {
            holder.profileImage.setImageResource(R.mipmap.ic_launcher);
        }else
        {
            Glide.with(mContext).load(user.getImageURL()).into(holder.profileImage);

        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(mContext,UserChatActivity.class);
                intent.putExtra("userid",user.getId());
                mContext.startActivity(intent);
            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder{

        public TextView username;
        public ImageView profileImage;
        static RelativeLayout userItem;



        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            username = itemView.findViewById(R.id.user_name);
            profileImage = itemView.findViewById(R.id.profile_image);
            userItem = itemView.findViewById(R.id.userlist);


        }
    }
}
