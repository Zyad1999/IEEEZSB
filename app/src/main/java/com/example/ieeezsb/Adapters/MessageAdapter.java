package com.example.ieeezsb.Adapters;

import android.content.Context;
import android.graphics.Color;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.example.ieeezsb.Models.AllMethods;
import com.example.ieeezsb.Models.MessageModel;
import com.example.ieeezsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;


public class MessageAdapter extends RecyclerView.Adapter<MessageAdapter.MessageAdapterViewHolder> {

    Context context;
    List<MessageModel> messages ;
    DatabaseReference messageDb ;

    public MessageAdapter(Context context, List<MessageModel> messages, DatabaseReference messageDb) {
        this.context = context;
        this.messages = messages;
        this.messageDb = messageDb;
    }

    @NonNull
    @Override
    public MessageAdapterViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(context).inflate(R.layout.item_message, viewGroup, false);
        return new MessageAdapterViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MessageAdapterViewHolder messageAdapterViewHolder, int i) {
        MessageModel messageModel = messages.get(i);
        if (messageModel.getName().equals(AllMethods.name)){
            messageAdapterViewHolder.tvTitle.setText("You: " + messageModel.getMessage());
            messageAdapterViewHolder.tvTitle.setGravity(Gravity.START);
            messageAdapterViewHolder.ll.setBackgroundColor(Color.parseColor("#f39c12"));


        } else {
            messageAdapterViewHolder.tvTitle.setText(messageModel.getName() + ":" + messageModel.getMessage());
            messageAdapterViewHolder.ibDelete.setVisibility(View.GONE);

        }

    }

    @Override
    public int getItemCount() {
        return messages.size();
    }

    public class MessageAdapterViewHolder extends RecyclerView.ViewHolder{

        TextView tvTitle ;
        ImageButton ibDelete;
        LinearLayout ll;

        public MessageAdapterViewHolder(@NonNull View itemView) {
            super(itemView);
            tvTitle = itemView.findViewById(R.id.tvTitle);
            ibDelete = itemView.findViewById(R.id.imgBtnDelete);
            ll = itemView.findViewById(R.id.l1Message);

            ibDelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    messageDb.child(messages.get(getAdapterPosition()).getKey()).removeValue();
                }
            });

        }
    }
}
