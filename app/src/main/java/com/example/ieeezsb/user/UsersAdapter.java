package com.example.ieeezsb.user;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ieeezsb.R;

import java.util.ArrayList;

public class UsersAdapter extends RecyclerView.Adapter<UsersAdapter.ViewHolder> {


    private static final String TAG = "UsersAdapter";

    private ArrayList<User> usersList;
    private Context mContext;

    public UsersAdapter(Context mContext, ArrayList<User> usersList) {
        this.usersList = usersList;
        this.mContext = mContext;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View itemView = LayoutInflater.from(mContext).inflate(
                R.layout.user_item, parent, false);
        ViewHolder viewHolder = new ViewHolder(itemView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {


        // Get Current User.
        final User currentUser = usersList.get(position);

        // put user's Name.
        holder.name.setText(currentUser.getName());
        // put user's Position.
        holder.community.setText(currentUser.getCommunity());
        // put user's Photo.
        putUserPhoto(currentUser, holder);

        holder.userItemLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(mContext, "User's name : " + currentUser.getName(), Toast.LENGTH_SHORT).show();
            }
        });


    }

    @Override
    public int getItemCount() {
        return usersList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {


        TextView name;
        TextView community;
        TextView shortName;
        ConstraintLayout userItemLayout;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.name);
            community = itemView.findViewById(R.id.textView3);
            userItemLayout = itemView.findViewById(R.id.user_item_layout);
            shortName = itemView.findViewById(R.id.short_name);
        }
    }


    /**
     * Put User's Photo if it is existed.
     */
    private void putUserPhoto(User user, ViewHolder holder) {
        if (false) {
            Log.d(TAG, "putUserPhoto: User has a Photo");
            // TODO : Show User's Photo
        } else {
            holder.shortName.setText(getShortName(user));
        }

    }


    /**
     * @param user : Current User Object.
     * @return The First char of First & Last User's name.
     */
    private String getShortName(User user) {
        String shortName = "";
        String[] name = user.getName().split(" ");

        for (String n : name) {
            shortName = shortName + n.substring(0, 1);
        }

        return shortName;
    }


}
