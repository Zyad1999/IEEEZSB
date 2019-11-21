package com.example.ieeezsb.Adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ieeezsb.Models.MessageModel;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    private static final int MSG_TYPE_LEFT = 0 ;
    private static final int MSG_TYPE_RIGHT = 1 ;
    private Context context;
    private List<MessageModel> messages ;
    private FirebaseUser fUser;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private User user;

    public MessageAdapter(Context context, List<MessageModel> messages) {
        this.context = context;
        this.messages = messages;

    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        if (viewType == MSG_TYPE_LEFT){
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_left, parent, false);
            return new MessageAdapter.MessageAdapterViewHolder(view);
        } else{
            View view = LayoutInflater.from(context).inflate(R.layout.chat_item_right, parent, false);
            return new MessageAdapter.MessageAdapterViewHolder(view);
        }
    }

    @Override
    public void onBindViewHolder(@NonNull final MessageAdapterViewHolder messageAdapterViewHolder, int i) {
        MessageModel messageModel = messages.get(i);
        messageAdapterViewHolder.showMessage.setText(messageModel.getMessage());

        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");
        reference = FirebaseDatabase.getInstance().getReference("users").child(messageModel.getSenderId());


        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);

                if (user.getProfileImage().equals("default")) {
                    messageAdapterViewHolder.profileImage.setImageResource(R.mipmap.ic_launcher);
                } else {

                    Glide.with(context)
                            .load(user.getProfileImage())
                            .into(messageAdapterViewHolder.profileImage);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });


    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder{

        public TextView showMessage;
        public ImageView profileImage;
        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            showMessage = itemView.findViewById(R.id.showMessage);
            profileImage = itemView.findViewById(R.id.profile_image);
        }
    }

    @Override
    public int getItemViewType(int position) {
        fUser = FirebaseAuth.getInstance().getCurrentUser();
        if (messages.get(position).getSenderId().equals(fUser.getUid())){
            return MSG_TYPE_RIGHT;
        } else {
            return MSG_TYPE_LEFT;
        }
    }
}
