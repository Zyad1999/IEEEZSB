package com.example.ieeezsb.Fragments;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.bumptech.glide.Glide;
import com.example.ieeezsb.Activities.HomeActivity;
import com.example.ieeezsb.LoginActivity;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import de.hdodenhof.circleimageview.CircleImageView;


public class SettingsFragment extends Fragment implements ChangePasswordDialog.ChangePasswordListener {

    private static final int PICK_IMAGE = 1;

    private Context mContext;
    private DatabaseReference mDatabase;
    private FirebaseStorage storage;
    private StorageReference imageStorageRef;
    private CircleImageView profileImage;
    private EditText name, about, phone;
    private TextView shortName;
    private String previousName;
    private String previousAbout;
    private String previousPhone;
    private Uri imageUri;
    private boolean changeImage;
    private User user;
    private Button btnChangePassword;

    /**
     * Prevent app to crash while loading the photo.
     */
    public static boolean isValidContextForGlide(final Context context) {
        if (context == null) {
            return false;
        }
        if (context instanceof Activity) {
            final Activity activity = (Activity) context;
            if (activity.isDestroyed() || activity.isFinishing()) {
                return false;
            }
        }
        return true;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View rootView = inflater.inflate(R.layout.fragment_settings,
                container, false);

        mContext = getContext();
        Button saveButton = rootView.findViewById(R.id.save_button);
        profileImage = rootView.findViewById(R.id.profile_img);
        CircleImageView changeProfileImg = rootView.findViewById(R.id.change_imageView);
        name = rootView.findViewById(R.id.name_editText);
        about = rootView.findViewById(R.id.about_editText);
        phone = rootView.findViewById(R.id.phone_editText);
        shortName = rootView.findViewById(R.id.shortenName);
        changeImage = true;
        btnChangePassword = rootView.findViewById(R.id.changePass);

        getUser();

        changeProfileImg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                updateProfileImage();
            }
        });


        // Save Changes.
        saveButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (imageUri != null || !previousName.equals(name.getText().toString().trim())
                        || !previousAbout.equals(about.getText().toString().trim())
                        || !previousPhone.equals(phone.getText().toString().trim())) {

                    if (imageUri != null) {

                        changeImage = true;

                        storage = FirebaseStorage.getInstance();
                        imageStorageRef = storage.getReference()
                                .child("ProfileImages/" + imageUri.getLastPathSegment());

                        imageStorageRef.putFile(imageUri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                            @Override
                            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                                imageStorageRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                                    @Override
                                    public void onSuccess(Uri uri) {
                                        mDatabase.child("profileImage").setValue(uri.toString());
                                    }
                                });


                            }
                        });
                    }

                    mDatabase.child("name").setValue(name.getText().toString().trim());
                    mDatabase.child("about").setValue(about.getText().toString().trim());
                    mDatabase.child("phone").setValue(phone.getText().toString().trim());


                    Toast.makeText(mContext, "Changes Saved", Toast.LENGTH_SHORT).show();


                } else {
                    Toast.makeText(mContext, "No changes to save", Toast.LENGTH_SHORT).show();
                }


            }
        });

        btnChangePassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {


                ChangePasswordDialog dialog = new ChangePasswordDialog();
                dialog.setTargetFragment(SettingsFragment.this, 1);
                dialog.show(getFragmentManager(), "MyCustomDialog");
            }
        });


        return rootView;
    }

    private void getUser() {
        FirebaseUser currentUser = FirebaseAuth.getInstance().getCurrentUser();
        if (currentUser != null) {
            String uID = currentUser.getUid();
            mDatabase = FirebaseDatabase.getInstance().getReference().child("users").child(uID);
            mDatabase.addValueEventListener(new ValueEventListener() {
                @Override
                public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                    user = dataSnapshot.getValue(User.class);

                    previousName = user.getName();
                    previousAbout = user.getAbout();
                    previousPhone = user.getPhone();

                    name.setText(previousName);
                    about.setText(previousAbout);
                    phone.setText(previousPhone);

                    if (changeImage) {
                        if (!user.getProfileImage().equals("null")) {
                            shortName.setVisibility(View.GONE);
                            if (isValidContextForGlide(mContext)) {
                                Glide.with(mContext)
                                        .load(user.getProfileImage())
                                        .into(profileImage);
                            }
                        } else {
                            shortName.setVisibility(View.VISIBLE);
                            profileImage.setImageResource(R.color.colorPrimary);
                            shortName.setText(getShortName(user.getName()));
                        }
                    }

                }

                @Override
                public void onCancelled(@NonNull DatabaseError databaseError) {

                }
            });


        }


    }

    private String getShortName(String fullName) {
        String shortName = "";
        String[] name = fullName.split(" ");

        shortName = shortName + name[0].substring(0, 1);
        shortName = shortName + name[name.length - 1].substring(0, 1);

        return shortName;
    }

    /**
     * pick up a photo then change it.
     */
    private void updateProfileImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE);


    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == PICK_IMAGE && data != null) {

            imageUri = data.getData();
            profileImage.setImageURI(imageUri);
            changeImage = false;

        }
    }


    @Override
    public void applyTexts(String currentPass, String newPass) {

        FirebaseUser fUser = FirebaseAuth.getInstance().getCurrentUser();
       final FirebaseAuth mAuth = FirebaseAuth.getInstance();
        fUser.updatePassword(newPass).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                if (task.isSuccessful()){
                    Toast.makeText(getContext(), "Password Updated", Toast.LENGTH_LONG).show();
                    mAuth.signOut();
                    Intent loginIntent = new Intent(getContext(), LoginActivity.class);
                    startActivity(loginIntent);
                    getActivity().finish();
                } else {
                    Toast.makeText(getContext(), "Error In Changing Password", Toast.LENGTH_LONG).show();
                }
            }
        });


    }
}
