package com.example.ieeezsb.Activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.example.ieeezsb.Models.Task;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import org.w3c.dom.Text;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class Sending_Tasks_Activity extends AppCompatActivity {

    private FirebaseDatabase firebaseDatabase;
    private FirebaseAuth firebaseAuth;
    private DatabaseReference databaseReference;
    private FirebaseUser firebaseUser;
    private EditText task_name , txt_task;
    private TextView selected_members;
    private String [] userList;
    private Button select ;
    private ArrayList<String> selectedItem = new ArrayList() ;
    private ArrayList<String> show_users = new ArrayList<>();
    private int x=0 , mSelected =-1;
    private String m ;
    private User myUser;
    private String Current_user;
    private String [] team;
    private boolean [] checked;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sending__tasks_);
        Toolbar toolbar = findViewById(R.id.tasks_toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Write the task ... ");
        selected_members = (TextView)findViewById(R.id.txtView);
        select = (Button)findViewById(R.id.btn_select);
        task_name = (EditText)findViewById(R.id.task_name);
        txt_task = (EditText)findViewById(R.id.txt_task);
        firebaseDatabase = FirebaseDatabase.getInstance();
        firebaseAuth = FirebaseAuth.getInstance();
        firebaseUser = firebaseAuth.getCurrentUser();
        Current_user = firebaseUser.getUid();
        databaseReference = firebaseDatabase.getReference("users");
        databaseReference.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                show_users.clear();
                for(DataSnapshot snapshot :dataSnapshot.getChildren() ){
                   User user = snapshot.getValue(User.class);
                   show_users.add(user.getName() + " - " + user.getCommunity()) ;
               }
                databaseReference = firebaseDatabase.getReference("users").child(Current_user).child("community");
                databaseReference.addValueEventListener(new ValueEventListener() {
                    @Override
                    public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                        String position = dataSnapshot.getValue().toString().toLowerCase();
                        team = position.split(" ",0);
                                for (int i = 0; i < show_users.size(); i++) {
                                    while (!show_users.get(i).toLowerCase().contains(team[0].toLowerCase()) || show_users.get(i).toLowerCase().contains("chairman")) {
                                        show_users.remove(i);
                                        if (show_users.size() == 0)
                                            break;
                                        i=0;
                                    }
                                }
                            x=show_users.size();
                            userList = new String[x];
                             for (int i = 0 ;i<x ; i++){
                                 userList[i] = show_users.get(i);
                             }
                        checked = new boolean[x];
                    }
                    @Override
                    public void onCancelled(@NonNull DatabaseError databaseError) {
                    }
                });
            }
            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) { }
        });

        select.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AlertDialog.Builder mBuilder = new AlertDialog.Builder(Sending_Tasks_Activity.this);
                mBuilder.setTitle("Select members ");
                mBuilder.setMultiChoiceItems(userList, checked, new DialogInterface.OnMultiChoiceClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which, boolean isChecked) {
                        if(isChecked){
                            selectedItem.add(userList[which]);
                        }
                        else if(selectedItem.contains(userList[which])){
                }

                        if (isChecked) {
                            if ((mSelected != -1) && (mSelected != which)) {
                                final int oldVal = mSelected;
                                final AlertDialog alert = (AlertDialog)dialog;
                                final ListView list = alert.getListView();
                                list.setItemChecked(oldVal, false);
                            }
                            mSelected = which;
                        } else
                            mSelected = -1;
                    }
                }).setPositiveButton(R.string.done, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        m = "";
                        for (int i =0 ;i<selectedItem.size();i++){
                            m = m + " - " +selectedItem.get(i);
                        }
                        selected_members.setText(m);
                    }
                }).setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                    }
                });
            AlertDialog dialog = mBuilder.create();
            dialog.setCancelable(false);
            dialog.show();
            }

        });


}

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.send_tasks, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btn_send_task:
                if (firebaseUser != null) {
                    String id = firebaseUser.getUid();
                    databaseReference = firebaseDatabase.getReference("users").child(id);
                    databaseReference.addValueEventListener(new ValueEventListener() {
                        @Override
                        public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                            //Write to database
                            if (!TextUtils.isEmpty(task_name.getText().toString()) && !TextUtils.isEmpty(txt_task.getText().toString()) && mSelected !=-1) {
                                myUser = new User();
                                myUser = dataSnapshot.getValue(User.class);
                                Task myTask = new Task(txt_task.getText().toString(),  m.toLowerCase(),task_name.getText().toString());
                                databaseReference = firebaseDatabase.getReference("TASKS").child(team[0].toUpperCase() + "_TASKS");
                                databaseReference.push().setValue(myTask);
                                txt_task.setText("");
                                task_name.setText("");
                                selected_members.setText("");
                                m = "";
                                Intent i = new Intent(Sending_Tasks_Activity.this,TasksActivity.class);
                                startActivity(i);
                                finish();
                            }

                        }

                        @Override
                        public void onCancelled(@NonNull DatabaseError databaseError) {
                        }
                    });
                }
                if (TextUtils.isEmpty(task_name.getText().toString()) || TextUtils.isEmpty(txt_task.getText().toString()) || mSelected ==-1){
                    Toast.makeText(Sending_Tasks_Activity.this,"Fill blank fields " , Toast.LENGTH_SHORT).show();
                }
                return true;

            case R.id.switch_to_tasks:
                Intent i2 = new Intent(Sending_Tasks_Activity.this,TasksActivity.class);
                startActivity(i2);
                finish();
                return true;

            case R.id.remove_tasks:
                firebaseDatabase = FirebaseDatabase.getInstance();
                firebaseAuth = FirebaseAuth.getInstance();
                firebaseUser = firebaseAuth.getCurrentUser();
                Current_user = firebaseUser.getUid();
                AlertDialog.Builder builder = new AlertDialog.Builder(Sending_Tasks_Activity.this);
                builder.setMessage("Are you sure ?").setPositiveButton("Delete", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        databaseReference = firebaseDatabase.getReference("TASKS").child(team[0].toUpperCase() + "_TASKS");
                        databaseReference.addListenerForSingleValueEvent(new ValueEventListener() {
                            @Override
                            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                                for (DataSnapshot dataSnapshot1 :dataSnapshot.getChildren()){
                                    dataSnapshot1.getRef().removeValue(); }
                            }
                            @Override
                            public void onCancelled(@NonNull DatabaseError databaseError) {
                            }
                        });
                    }
                }).setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                    }
                });
                builder.create();
                builder.show();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }


    }
}

