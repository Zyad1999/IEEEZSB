package com.example.ieeezsb.Fragments;


import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ieeezsb.Adapters.MessageAdapter;
import com.example.ieeezsb.Models.Chat;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class CSROOMFragment extends Fragment {
    private RecyclerView recyclerView;
    private EditText txtSend;
    private ImageButton btnSend;
    List<Chat> msgList;
    private FirebaseAuth mAuth;
    private DatabaseReference messagesDb;
    private FirebaseDatabase database;
    private MessageAdapter msgAdapter;
    private User user;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csroom, container, false);

        recyclerView = view.findViewById(R.id.recycler_view);
        btnSend = view.findViewById(R.id.btnSend);
        txtSend = view.findViewById(R.id.txtSend);
        mAuth = FirebaseAuth.getInstance();
        database = FirebaseDatabase.getInstance();
        user = new User();
        msgList = new ArrayList<>();


        btnSend.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (!TextUtils.isEmpty(txtSend.getText().toString())) {
                    Chat msg = new Chat(user.getId(), txtSend.getText().toString());
                    txtSend.setText("");
                    messagesDb.push().setValue(msg);

                } else {
                    Toast.makeText(getContext(), "You Cannot Send Blank Message", Toast.LENGTH_LONG).show();
                }
            }
        });






        return view;
    }

    private void displayMessages(List<Chat> msgList) {

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        // msgAdapter = new MessageAdapter(getContext(), msgList, messagesDb);
        recyclerView.setAdapter(msgAdapter);

    }

}
