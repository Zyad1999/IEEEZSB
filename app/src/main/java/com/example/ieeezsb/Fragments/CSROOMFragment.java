package com.example.ieeezsb.Fragments;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ieeezsb.Activities.HomeActivity;
import com.example.ieeezsb.Adapters.MessageAdapter;
import com.example.ieeezsb.Models.AllMethods;
import com.example.ieeezsb.Models.MessageModel;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;

public class CSROOMFragment extends Fragment {
    private ImageButton sendBtn;
    private EditText msgEditText;
    private RecyclerView rvMessages ;
    private FirebaseAuth mAuth;
    private DatabaseReference messagesDb;
    private FirebaseDatabase database ;
    private MessageAdapter msgAdapter ;
    private User user ;
    private List<MessageModel> msgList ;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csroom, container, false);

        rvMessages = view.findViewById(R.id.recycler_view);
        sendBtn = view.findViewById(R.id.btnSend);
        msgEditText = view.findViewById(R.id.txtSend);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();
        msgList = new ArrayList<>();


        sendBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(msgEditText.getText().toString())){
                    MessageModel msg = new MessageModel(user.getName(),msgEditText.getText().toString());
                    msgEditText.setText("");
                    messagesDb.push().setValue(msg);

                } else {
                    Toast.makeText(getContext(), "You Cannot Send Blank Message", Toast.LENGTH_LONG).show();
                }
            }
        });


        final FirebaseUser currentUser = mAuth.getCurrentUser();
        user.setId(currentUser.getUid());


        database.getReference("users").child(currentUser.getUid()).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                user = dataSnapshot.getValue(User.class);
                user.setId(currentUser.getUid());
                AllMethods.name = user.getName();

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        messagesDb = database.getReference("messages").child("CS");

        messagesDb.addChildEventListener(new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                MessageModel message = dataSnapshot.getValue(MessageModel.class);
                message.setKey(dataSnapshot.getKey());
                msgList.add(message);
                displayMessages(msgList);


            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                messageModel.setKey(dataSnapshot.getKey());
                List<MessageModel> newMsg = new ArrayList<>();
                for (MessageModel m : msgList){
                    if (m.getKey().equals(messageModel.getKey())){
                        newMsg.add(messageModel);
                    } else {
                        newMsg.add(m);
                    }
                }

                msgList = newMsg ;
                displayMessages(msgList);


            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {
                MessageModel messageModel = dataSnapshot.getValue(MessageModel.class);
                messageModel.setKey(dataSnapshot.getKey());
                List<MessageModel> deletedMsg = new ArrayList<>();
                for (MessageModel m : msgList){
                    if (!m.getKey().equals(messageModel.getKey())){
                        deletedMsg.add(m);
                    }
                }

                msgList = deletedMsg ;
                displayMessages(msgList);

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });





        return view;
    }

    private void displayMessages(List<MessageModel> msgList) {

        rvMessages.setLayoutManager(new LinearLayoutManager(getContext()));
        msgAdapter = new MessageAdapter(getContext(), msgList, messagesDb);
        rvMessages.setAdapter(msgAdapter);

    }




}
