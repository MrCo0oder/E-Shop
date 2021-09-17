package com.example.e_shop;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

public class FDialog extends AppCompatDialogFragment {
    private EditText titleET;
    private Context var;

    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        LayoutInflater inflater = getActivity().getLayoutInflater();
        var = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.dialog_forget_password, null);
        titleET = view.findViewById(R.id.username);
        builder.setView(view);
        builder.setPositiveButton("Reset", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (FDialog.this.titleET.getText().toString().equals("")) {
                    Toast.makeText(FDialog.this.getActivity().getApplicationContext(), "Enter your Email", Toast.LENGTH_SHORT).show();
                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(FDialog.this.titleET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(FDialog.this.var, R.string.check_your_email_inbox, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(FDialog.this.var, task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.cancel();
            }
        });
        return builder.create();
    }
}
