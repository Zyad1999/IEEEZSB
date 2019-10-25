package com.example.ieeezsb;

import android.os.Bundle;
import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ieeezsb.user.AddUserFragment;
import com.example.ieeezsb.user.User;
import com.example.ieeezsb.user.User.Roles;
import com.example.ieeezsb.user.UsersAdapter;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity {


    private static final String TAG = "UsersActivity";

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersDatabase;
    private ChildEventListener userEventListener;


    private ArrayList<User> usersList = new ArrayList<>();
    private UsersAdapter usersAdapter;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);




        mFirebaseDatabase = FirebaseDatabase.getInstance();
        usersDatabase = mFirebaseDatabase.getReference().child("Users");




        FloatingActionButton fab = findViewById(R.id.add_user_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AddUserFragment addUserFragment = new AddUserFragment();
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, addUserFragment)
                        .commit();
            }
        });


        //Initialize the RecyclerView.
        initRecyclerView();


        getAllUsers();




    }


    /**
     * Initialize the RecyclerView.
     */
    private void initRecyclerView() {

        RecyclerView usersRecyclerView = findViewById(R.id.user_recycler_view);

        usersAdapter = new UsersAdapter(this, usersList);

        usersRecyclerView.setAdapter(usersAdapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }


    private void getAllUsers() {
        // TODO : Get All Users From Database and Add them to UsersList.

        userEventListener = new ChildEventListener() {
            @Override
            public void onChildAdded(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {
                // Read data from database and update the UI.
                User user = dataSnapshot.getValue(User.class);
                usersList.add(user);
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onChildChanged(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onChildRemoved(@NonNull DataSnapshot dataSnapshot) {

            }

            @Override
            public void onChildMoved(@NonNull DataSnapshot dataSnapshot, @Nullable String s) {

            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        };
        usersDatabase.addChildEventListener(userEventListener);
    }


    private void addNewUser() {
        // TODO : Create a new user in Database.
        Roles roles = new Roles(true, false);
        User newUser = new User("Samy", "Cs", "0100000",
                "samy@ieee.org", null, roles, null);

        // Add user to database.
        usersDatabase.push().setValue(newUser);
    }
}
