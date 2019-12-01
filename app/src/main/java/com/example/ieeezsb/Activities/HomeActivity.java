package com.example.ieeezsb.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;

import com.bumptech.glide.Glide;
import com.example.ieeezsb.Fragments.HomeFragment;
import com.example.ieeezsb.Fragments.ROOMSFragment;
import com.example.ieeezsb.Fragments.SettingsFragment;
import com.example.ieeezsb.LoginActivity;
import com.example.ieeezsb.Models.AllMethodsCommunity;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;

import de.hdodenhof.circleimageview.CircleImageView;

public class HomeActivity extends AppCompatActivity implements NavigationView.OnNavigationItemSelectedListener {
    private DrawerLayout drawer;
    private FirebaseAuth firebaseAuth;
    private CircleImageView profileImage;
    private TextView nameNav, emailNav;
    private FirebaseUser fUser;
    private DatabaseReference reference;
    private StorageReference storageReference;
    private User user;
    private String my_community ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        firebaseAuth = FirebaseAuth.getInstance();
        Toolbar toolbar = findViewById(R.id.add_user_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Home");
        drawer = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);
        navigationView.setNavigationItemSelectedListener(this);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(this, drawer, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);

        drawer.addDrawerListener(toggle);
        toggle.syncState();
        View headerView = navigationView.getHeaderView(0);
        nameNav = headerView.findViewById(R.id.nameNav);
        emailNav = headerView.findViewById(R.id.emailNav);
        profileImage = headerView.findViewById(R.id.profile_image);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new HomeFragment()).commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }


        fUser = firebaseAuth.getCurrentUser();
        storageReference = FirebaseStorage.getInstance().getReference("ProfileImages");
        reference = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());

        reference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {

                user = dataSnapshot.getValue(User.class);
                nameNav.setText(user.getName());
                my_community = user.getCommunity().toLowerCase();
                emailNav.setText(user.getEmail());
                AllMethodsCommunity.communityOfUser = user.getCommunity();
                if (user.getProfileImage().equals("default")) {
                    profileImage.setImageResource(R.mipmap.ic_launcher);
                } else {

                    Glide.with(getApplicationContext())
                            .load(user.getProfileImage())
                            .into(profileImage);
                }


            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });

        status("online");


    }

    @Override
    public void onBackPressed() {
        if (drawer.isDrawerOpen(GravityCompat.START)) {
            drawer.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }


    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem menuItem) {
        switch (menuItem.getItemId()) {
            case R.id.nav_settings:
                getSupportActionBar().setTitle("Settings");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new SettingsFragment()).commit();
                break;
            case R.id.nav_chat:
                chatToOpen();
                break;
            case R.id.nav_home:
                getSupportActionBar().setTitle("Home");
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                        new HomeFragment()).commit();
                break;
            case R.id.nav_users:
                Intent i = new Intent(HomeActivity.this, UsersActivity.class);
                startActivity(i);
                break;
            case R.id.nav_logout:
                logOut();
                return true;
            case R.id.nav_tasks:
                if (my_community.contains("chairman")){
                    Intent i3 = new Intent(HomeActivity.this , Sending_Tasks_Activity.class);
                    startActivity(i3);
                }
                else {
                    Intent i4 = new Intent(HomeActivity.this,TasksActivity.class);
                    startActivity(i4);
                }
                break;
        }
        drawer.closeDrawer(GravityCompat.START);
        return true;
    }

    private void logOut() {
        firebaseAuth.signOut();
        sendToLogin();
    }

    private void sendToLogin() {
        Intent loginIntent = new Intent(HomeActivity.this, LoginActivity.class);
        startActivity(loginIntent);
        finish();
    }

    public void status(String status) {
        reference = FirebaseDatabase.getInstance().getReference("users").child(fUser.getUid());
        reference.child("chatStatus").setValue(status);
    }

    @Override
    protected void onResume() {
        super.onResume();
        status("online");
    }

    @Override
    protected void onPause() {
        super.onPause();
        status("offline");
    }

    @Override
    protected void onStart() {
        super.onStart();
        status("online");
    }

    public void chatToOpen(){
        if(AllMethodsCommunity.communityOfUser.equals("CS") || AllMethodsCommunity.communityOfUser.equals("CS Chairman")){
            getSupportActionBar().setTitle("CS ROOM");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ROOMSFragment("CS")).commit();
        } else if (AllMethodsCommunity.communityOfUser.equals("RAS")){

            getSupportActionBar().setTitle("RAS ROOM");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ROOMSFragment("RAS")).commit();



        }else if (AllMethodsCommunity.communityOfUser.equals("Logistics") ){

            getSupportActionBar().setTitle("Logistics ROOM");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ROOMSFragment("Logistics")).commit();

        }else if (AllMethodsCommunity.communityOfUser.equals("Media")){

            getSupportActionBar().setTitle("Media ROOM");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ROOMSFragment("Media")).commit();

        }else if (AllMethodsCommunity.communityOfUser.equals("Markting")){

            getSupportActionBar().setTitle("Markting ROOM");
            getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,
                    new ROOMSFragment("Markting")).commit();

        }
    }
}
