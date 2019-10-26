package com.example.ieeezsb.Fragments;


import android.content.Intent;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.ieeezsb.Adapters.MessageAdapter;
import com.example.ieeezsb.Models.Chat;
import com.example.ieeezsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DatabaseReference;

import java.util.List;

import de.hdodenhof.circleimageview.CircleImageView;

public class CSROOMFragment extends Fragment {

    private CircleImageView profileImage;
    private TextView username;
    private FirebaseUser firebaseUser;
    private DatabaseReference reference;
    private RecyclerView recyclerView;
    private EditText txtSend;
    private ImageButton btnSend;
    private MessageAdapter messageAdapter;
    private List<Chat> mChat;
    private Intent intent;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_csroom, container, false);
        recyclerView = view.findViewById(R.id.recycler_view);
        recyclerView.setHasFixedSize(true);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        firebaseUser = FirebaseAuth.getInstance().getCurrentUser();

        return view;
    }

}
