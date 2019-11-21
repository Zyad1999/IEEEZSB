package com.example.ieeezsb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ieeezsb.Adapters.UsersListAdapter;
import com.example.ieeezsb.Fragments.AddUserFragment;
import com.example.ieeezsb.Fragments.AddUserFragment.MyFragmentListener;
import com.example.ieeezsb.Interfaces.OnBackPressed;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class UsersActivity extends AppCompatActivity implements MyFragmentListener {


    private static final String TAG = "UsersActivity";
    public static boolean isFragmentRun = false;

    private FirebaseDatabase mFirebaseDatabase;
    private DatabaseReference usersDatabase;
    private FirebaseAuth mFirebaseAuth;
    private FirebaseUser mFirebaseUser;
    private ChildEventListener userEventListener;
    private ValueEventListener mValueEventListener;


    private ArrayList<User> usersList = new ArrayList<>();
    private UsersListAdapter usersAdapter;

    private FloatingActionButton fab;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_users);


        // Init Toolbar.
        Toolbar toolbar = findViewById(R.id.add_user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("Users");


        mFirebaseDatabase = FirebaseDatabase.getInstance();
        usersDatabase = mFirebaseDatabase.getReference().child("users");
        mFirebaseAuth = FirebaseAuth.getInstance();
        mFirebaseUser = mFirebaseAuth.getCurrentUser();

        // Init Floating Action Bar.
        fab = findViewById(R.id.add_user_fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {


                AddUserFragment addUserFragment = new AddUserFragment();
                addUserFragment.setArguments(getIntent().getExtras());
                FragmentManager fragmentManager = getSupportFragmentManager();
                fragmentManager.beginTransaction()
                        .add(R.id.fragment_container, addUserFragment)
                        .addToBackStack(null)
                        .commit();





            }
        });


        initRecyclerView();
        getAllUsers();

    }


    /**
     * Initialize the RecyclerView.
     */
    private void initRecyclerView() {
        RecyclerView usersRecyclerView = findViewById(R.id.user_recycler_view);
        usersAdapter = new UsersListAdapter(this, usersList);
        usersRecyclerView.setAdapter(usersAdapter);
        usersRecyclerView.setLayoutManager(new LinearLayoutManager(this));
    }

    /**
     * Get All Users From Database and Add them to UsersList.
     */
    private void getAllUsers() {
        mValueEventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                if (!usersList.isEmpty()) {
                    usersList.clear();
                }
                for (DataSnapshot data : dataSnapshot.getChildren()) {
                    User user = data.getValue(User.class);
                    usersList.add(user);
                }
                usersAdapter.notifyDataSetChanged();
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };
        usersDatabase.addValueEventListener(mValueEventListener);
    }


    /**
     * Add new user in database.
     */
    private void addNewUser(User newUser) {
        // Push the user to database.
        if (newUser.getId() == null) {
            String key = usersDatabase.push().getKey();
            newUser.setId(key);
            usersDatabase.child(key).setValue(newUser);
        } else { // Create user with specific ID.
            usersDatabase.child(newUser.getId()).setValue(newUser);
        }
    }


    /**
     * Update user's Status in database.
     */
    private void status(String statue) {
        usersDatabase.child(mFirebaseUser.getUid())
                .child("chatStatus").setValue(statue);
    }


    @Override
    protected void onPause() {
        super.onPause();
        if (userEventListener != null) {
            usersDatabase.removeEventListener(userEventListener);
            userEventListener = null;
        }
        status("offline");

    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onStart() {
        super.onStart();
        status("online");
    }


    @Override
    public void onFragmentLoaded() {
        fab.hide();
        isFragmentRun = true;
    }

    @Override
    public void onFragmentFinished() {
        fab.show();
        isFragmentRun = false;
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }

    @Override
    public void newUser(User newUser) {
        addNewUser(newUser);
    }


    @Override
    public void onBackPressed() {
        Fragment fragment = getSupportFragmentManager().findFragmentById(R.id.fragment_container);
        if (!(fragment instanceof OnBackPressed) || !((OnBackPressed) fragment).onBackPressed()) {
            super.onBackPressed();
        }
    }
}
