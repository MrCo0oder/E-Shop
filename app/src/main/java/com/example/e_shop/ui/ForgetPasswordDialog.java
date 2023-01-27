package com.example.e_shop.ui;

import android.app.AlertDialog.Builder;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnClickListener;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatDialogFragment;

import com.example.e_shop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;

import org.jetbrains.annotations.NotNull;

import java.util.Objects;

public class ForgetPasswordDialog extends AppCompatDialogFragment {
    private EditText titleET;
    private Context context;

    @NotNull
    public Dialog onCreateDialog(Bundle savedInstanceState) {
        Builder builder = new Builder(getActivity());
        LayoutInflater inflater = Objects.requireNonNull(getActivity()).getLayoutInflater();
        context = getActivity().getApplicationContext();
        View view = inflater.inflate(R.layout.dialog_forget_password, null);
        titleET = view.findViewById(R.id.username);
        builder.setView(view);
        builder.setPositiveButton("Reset", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                if (ForgetPasswordDialog.this.titleET.getText().toString().equals("")) {
                    Toast.makeText(ForgetPasswordDialog.this.getActivity().getApplicationContext(), "Enter your Email", Toast.LENGTH_SHORT).show();

                    return;
                }
                FirebaseAuth.getInstance().sendPasswordResetEmail(ForgetPasswordDialog.this.titleET.getText().toString()).addOnCompleteListener(new OnCompleteListener<Void>() {
                    public void onComplete(@NotNull Task<Void> task) {
                        if (task.isSuccessful()) {
                            Toast.makeText(ForgetPasswordDialog.this.context, R.string.check_your_email_inbox, Toast.LENGTH_SHORT).show();
                            return;
                        }
                        Toast.makeText(ForgetPasswordDialog.this.context, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
        builder.setNegativeButton("Cancel", new OnClickListener() {
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        return builder.create();
    }
}
