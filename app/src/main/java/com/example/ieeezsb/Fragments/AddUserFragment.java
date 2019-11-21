package com.example.ieeezsb.Fragments;

import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;

import com.example.ieeezsb.Interfaces.OnBackPressed;
import com.example.ieeezsb.Models.User;
import com.example.ieeezsb.R;

import java.util.ArrayList;
import java.util.List;

public class AddUserFragment extends Fragment implements OnBackPressed {

    private MyFragmentListener mListener;
    private EditText nameEditText, idEditText, phoneEditText,
            personalMailEditText, emailEditText, passwordIeee;
    private String name, id, phone, personalMail,
            email, community;
    private Spinner communitySpinner;
    private Context mContext;
    private boolean cancelButtonAlert;


    public AddUserFragment() {

    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater,
                             @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(
                R.layout.fragment_add_user, container, false);

        mContext = getContext();
        cancelButtonAlert = true;

        // Initialize Toolbar.
        Toolbar toolbar = rootView.findViewById(R.id.add_user_toolbar);
        ((AppCompatActivity) getActivity()).setSupportActionBar(toolbar);
        getActivity().setTitle("Add User");

        // Hide the Floating Action Bar.
        onLoad();

        nameEditText = rootView.findViewById(R.id.user_name_edit_text);
        idEditText = rootView.findViewById(R.id.user_id_edit_text);
        phoneEditText = rootView.findViewById(R.id.phone_edit_text);
        personalMailEditText = rootView.findViewById(R.id.personal_mail_edit_text);
        emailEditText = rootView.findViewById(R.id.ieee_email_edit_text);
        passwordIeee = rootView.findViewById(R.id.passwordIeee);

        // Create Community List for Spinner.
        List<String> communityList = getCommunity();

        // Initialize Spinner.
        communitySpinner = rootView.findViewById(R.id.community_spinner);
        ArrayAdapter<String> dataAdapter = new ArrayAdapter<>(mContext,
                android.R.layout.simple_spinner_item, communityList);
        dataAdapter.setDropDownViewResource(
                android.R.layout.simple_spinner_dropdown_item);
        communitySpinner.setAdapter(dataAdapter);
        // Add listener to communitySpinner.
        communitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int position, long id) {
                community = communitySpinner.getSelectedItem().toString();
            }

            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });


        // Initialize Add Button
        Button addButton = rootView.findViewById(R.id.add_user_button);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (checkInputs()) {
                    createNewUser();
                    cancelButtonAlert = false;
                    msg("User Added");
                    getActivity().onBackPressed();
                }

            }
        });


        // Initialize Cancel Button
        Button cancelButton = rootView.findViewById(R.id.cancel_button);
        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (isFill()) {
                    cancelButtonAlert();
                } else {
                    getActivity().onBackPressed();
                }


            }
        });

        return rootView;
    }

    /**
     * Create alert when any EditText isn't empty and cancel button pressed.
     */
    private void cancelButtonAlert() {
        AlertDialog.Builder builder1 = new AlertDialog.Builder(mContext);
        builder1.setMessage("Are you sure you want to cancel adding new user ?");
        builder1.setTitle("Cancel Adding");
        builder1.setCancelable(true);
        builder1.setPositiveButton(
                "Yes",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                        cancelButtonAlert = false;
                        getActivity().onBackPressed();
                    }
                });

        builder1.setNegativeButton(
                "No",
                new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert11 = builder1.create();
        alert11.show();


    }


    /**
     * @return Community List.
     */
    private List<String> getCommunity() {

        List<String> communityList = new ArrayList<>();
        communityList.add("Chairman");
        communityList.add("Vice-Chairman");
        communityList.add("Treasurer");
        communityList.add("Secretary");
        communityList.add("CS Chairman");
        communityList.add("RAS Chairman");
        communityList.add("HR Head");
        communityList.add("PR & FR Head");
        communityList.add("Marketing Head");
        communityList.add("Multimedia Head");
        communityList.add("Operations Head");
        communityList.add("R&D Head");
        communityList.add("CS");
        communityList.add("RAS");
        communityList.add("HR");
        communityList.add("Operations");
        communityList.add("Marketing");
        communityList.add("Multimedia ");
        communityList.add("PR & FR");

        return communityList;
    }


    /**
     * Check the User Inputs
     */
    private boolean checkInputs() {
        name = nameEditText.getText().toString().trim();
        if (name.isEmpty()) {
            msg("User Name is Empty");
            return false;
        }
        phone = phoneEditText.getText().toString().trim();
        if (phone.isEmpty()) {
            msg("User Phone is Empty");
            return false;
        }
        personalMail = personalMailEditText.getText().toString().trim();
        if (personalMail.isEmpty()) {
            msg("User Personal Mail is Empty");
            return false;
        }

        if (!emailEditText.getText().toString().trim().isEmpty()) {
            email = idEditText.getText().toString().trim();
        } else {
            email = "null";
        }

        if (!idEditText.getText().toString().trim().isEmpty()){
            id = idEditText.getText().toString().trim();
        }



        return true;
    }


    /**
     * Create a Toast message.
     */
    private void msg(String msg) {
        Toast.makeText(mContext, msg, Toast.LENGTH_SHORT).show();
    }


    /**
     * Create a new User.
     */
    private void createNewUser() {

        User user = new User(name,
                "null",
                email,
                personalMail,
                phone,
                community,
                "offline",
                "null",
                "null",
                getSecurityLevel(),
                id);


        newUser(user);
    }


    /**
     * Get User Security Level.
     */
    private String getSecurityLevel() {
        // TODO : Give User's permission.
        return "0";
    }


    /**
     * Check if any EditText has a Value.
     */
    private boolean isFill() {
        if (!nameEditText.getText().toString().trim().isEmpty()
                || !phoneEditText.getText().toString().trim().isEmpty()
                || !personalMailEditText.getText().toString().trim().isEmpty()
                || !emailEditText.getText().toString().trim().isEmpty()) {
            return true;
        }

        return false;
    }


    public interface MyFragmentListener {
        void onFragmentLoaded();

        void onFragmentFinished();

        void newUser(User newUser);
    }

    private void onLoad() {
        if (mListener != null) {
            mListener.onFragmentLoaded();
        }
    }

    private void onFinish() {
        if (mListener != null) {
            mListener.onFragmentFinished();
        }
    }

    private void newUser(User newUser) {
        if (mListener != null) {
            mListener.newUser(newUser);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        onFinish();
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        // Force the parent activity to implement listener.
        if (context instanceof MyFragmentListener) {
            mListener = (MyFragmentListener) context;
        } else {
            throw new RuntimeException(context.toString()
                    + " must implement OnFragmentInteractionListener");
        }
    }

    @Override
    public void onDetach() {
        super.onDetach();
        mListener = null;
    }

    @Override
    public boolean onBackPressed() {

        if (isFill() && cancelButtonAlert) {
            cancelButtonAlert();
            return true;
        } else {
            return false;
        }
    }

}
