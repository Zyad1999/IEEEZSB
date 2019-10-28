package com.example.ieeezsb.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ieeezsb.Activities.ProfileActivity;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {

    private Context mContext;
    private List<User> mUsers;
    private boolean isChat;

    public UsersAdapter(Context mContext, List<User> musers, boolean isChat) {
        this.mUsers = musers;
        this.mContext = mContext;
        this.isChat = isChat;

    }


    @NonNull
    @Override
    public UsersAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(mContext).inflate(R.layout.user_item, parent, false);
        return new UsersAdapter.ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull UsersAdapter.ViewHolder holder, int position) {
        final User user = mUsers.get(position);
        holder.memberName.setText(user.getName());
        holder.memberCommunity.setText(user.getCommunity());
        if (user.getProfileImage().equals("default")) {
            holder.memberProfileImage.setImageResource(R.mipmap.ic_launcher);
        } else {
            Glide.with(mContext).load(user.getProfileImage()).into(holder.memberProfileImage);
        }

        if (isChat) {

            if (user.getChatStatus().equals("online")) {
                holder.imgOn.setVisibility(View.VISIBLE);
                holder.imgOn.setVisibility(View.GONE);
            } else {
                holder.imgOn.setVisibility(View.GONE);
                holder.imgOff.setVisibility(View.VISIBLE);
            }

        } else {
            holder.imgOn.setVisibility(View.GONE);
            holder.imgOff.setVisibility(View.GONE);
        }

        holder.itemView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(mContext, ProfileActivity.class);
                intent.putExtra("userid", user.getId());
                mContext.startActivity(intent);

            }
        });

    }

    @Override
    public int getItemCount() {
        return mUsers.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        public TextView memberName, memberCommunity;
        public CircleImageView memberProfileImage;
        public ImageView imgOn, imgOff;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            memberName = itemView.findViewById(R.id.memberName);
            memberProfileImage = itemView.findViewById(R.id.profile_image);
            memberCommunity = itemView.findViewById(R.id.memberCommunity);
            imgOn = itemView.findViewById(R.id.imgOn);
            imgOff = itemView.findViewById(R.id.imgOff);

        }
    }
}
