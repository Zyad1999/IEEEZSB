package com.example.ieeezsb.Adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ieeezsb.Activities.ProfileActivity;
import com.example.ieeezsb.Activities.UsersActivity;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;

import java.util.ArrayList;

import de.hdodenhof.circleimageview.CircleImageView;

import static com.example.ieeezsb.Fragments.SettingsFragment.isValidContextForGlide;

public class UsersListAdapter extends RecyclerView.Adapter<UsersListAdapter.ViewHolder> {


    private static final String TAG = "UsersAdapter";

    private ArrayList<User> usersList;
    private Context mContext;

    private User currentUser;


    public UsersListAdapter(Context mContext, ArrayList<User> usersList) {
        this.usersList = usersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.user_item_list, parent, false);
        return new ViewHolder(itemView);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, final int position) {


        // Get Current User.
        currentUser = usersList.get(position);

        // put user's Name.
        holder.name.setText(currentUser.getName());

        // put user's Position.
        holder.community.setText(currentUser.getCommunity());

        // put user's Photo.
        if (!currentUser.getProfileImage().equals("null") && isValidContextForGlide(mContext)) {
            holder.shortName.setVisibility(View.GONE);
            Glide.with(mContext)
                    .load(currentUser.getProfileImage())
                    .into(holder.profileImage);
        } else {
            holder.shortName.setVisibility(View.VISIBLE);
            holder.profileImage.setImageResource(R.color.colorPrimary);
            holder.shortName.setText(getShortName());
        }

        // Intent to the selected user profile.
        holder.userItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (!UsersActivity.isFragmentRun) {
                    Intent intent = new Intent(mContext, ProfileActivity.class);
                    intent.putExtra("userid", usersList.get(position).getId());
                    mContext.startActivity(intent);
                }
            }
        });


        // Get user's Status
        if (currentUser.getChatStatus().equals("online")) {
            holder.imgOnline.setVisibility(View.VISIBLE);
            holder.imgOffline.setVisibility(View.GONE);
        } else {
            holder.imgOffline.setVisibility(View.VISIBLE);
            holder.imgOnline.setVisibility(View.GONE);
        }


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TextView community;
        TextView shortName;

        CircleImageView profileImage, imgOnline, imgOffline;


        ConstraintLayout userItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            community = itemView.findViewById(R.id.textView3);
            userItemLayout = itemView.findViewById(R.id.user_item_layout);
            shortName = itemView.findViewById(R.id.short_name);
            profileImage = itemView.findViewById(R.id.profile_image);

            imgOnline = itemView.findViewById(R.id.img_online);
            imgOffline = itemView.findViewById(R.id.img_offline);
        }
    }


    private String getShortName() {
        String shortName = "";
        String[] name = currentUser.getName().split(" ");

        shortName = shortName + name[0].substring(0, 1);
        shortName = shortName + name[name.length - 1].substring(0, 1);


//        for (String n : name) {
//            shortName = shortName + n.substring(0, 1);
//        }

        return shortName;
    }


}
