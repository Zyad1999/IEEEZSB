package com.example.ieeezsb.Fragments;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.ieeezsb.R;

public class ChangePasswordDialog extends AppCompatDialogFragment {

    private EditText currentPass, newPass, confirmPass;
    private ChangePasswordListener listener;
    private static final String TAG = "MyCustomDialog";
    @Override
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.change_password, null);
        currentPass = view.findViewById(R.id.currentPassword);
        newPass = view.findViewById(R.id.newPassword);
        confirmPass = view.findViewById(R.id.confirmPassword);
        builder.setView(view)
                .setTitle("Login")
                .setNegativeButton("cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        getDialog().dismiss();
                    }
                })
                .setPositiveButton("OK", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        final String currentPassword = currentPass.getText().toString();
                        final String newPassword = newPass.getText().toString();
                        final String confirmNewPassword = confirmPass.getText().toString();


                        if (!newPassword.equals(confirmNewPassword)){
                            Toast.makeText(getContext(), "Password Confirmation Error", Toast.LENGTH_SHORT).show();
                        } else {
                            listener.applyTexts(currentPassword, newPassword);
                            getDialog().dismiss();
                        }








                    }
                });



        return builder.create();
    }

    public interface ChangePasswordListener {
        void applyTexts(String currentPass, String newPass);
    }

    @Override
    public void onAttach(Context context) {
        super.onAttach(context);

        try {
            listener = (ChangePasswordListener) getTargetFragment();
        } catch (ClassCastException e) {
            throw new ClassCastException(context.toString() +
                    "must implement ChangePasswordListener");
        }
    }



}
