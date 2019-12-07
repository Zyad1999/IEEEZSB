package com.example.ieeezsb.Activities;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.coordinatorlayout.widget.CoordinatorLayout;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.ieeezsb.Adapters.TasksAdapter;
import com.example.ieeezsb.Models.MessageModel;
import com.example.ieeezsb.Models.Task;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.android.gms.tasks.Tasks;
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
import java.util.List;

public class TasksActivity extends AppCompatActivity {
    private FirebaseAuth firebaseAuth;
    private FirebaseDatabase firebaseDatabase;
    private DatabaseReference databaseReference ;
    private User myUser;
    List<Task> myList = new ArrayList<Task>();
    RecyclerView recyclerView;
    TasksAdapter adapter;
    String id ,myname ;
    String [] mycomminuty;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_tasks);
        recyclerView = (RecyclerView)findViewById(R.id.recycler_view_tasks);
        firebaseAuth = FirebaseAuth.getInstance();
        FirebaseUser user = firebaseAuth.getCurrentUser();
        firebaseDatabase = FirebaseDatabase.getInstance();
        // Check if the current user is a head or noot
        if(user != null){
            id = user.getUid();
            databaseReference = firebaseDatabase.getReference("users").child(id);
            databaseReference.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    myUser = new User();
                    myUser = dataSnapshot.getValue(User.class);
                    myname = myUser.getName();
                    mycomminuty = myUser.getCommunity().split(" " , 4);
                    databaseReference = firebaseDatabase.getReference("TASKS").child(mycomminuty[0]+"_TASKS");
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            myList.clear();
                            for(DataSnapshot snapshot : dataSnapshot.getChildren()){
                                Task t = snapshot.getValue(Task.class);
                                if (t.getTask_names().contains(myname.toLowerCase()))
                                    myList.add(t);
                                else if (mycomminuty.length >1)
                                    myList.add(t);
                            }
                            displayMessages(myList);
                        }
                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {
                }
            });
        }
        else{ }
    }

    private void displayMessages(List<Task> msgList) {
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        adapter = new TasksAdapter(TasksActivity.this, msgList);
        recyclerView.setAdapter(adapter);
    }
}
