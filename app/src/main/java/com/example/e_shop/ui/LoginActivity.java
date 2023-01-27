package com.example.e_shop.ui;

import android.app.AlertDialog.Builder;
import android.app.ProgressDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.os.Process;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.e_shop.R;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

import org.jetbrains.annotations.NotNull;

public class LoginActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    TextInputLayout emailET;
    Button loginButton;
    FirebaseAuth mAuth;
    TextInputLayout passwordET;
    ProgressDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        emailET = findViewById(R.id.emailTextField);
        passwordET = findViewById(R.id.loginPasswordTextField);
        FirebaseAuth instance = FirebaseAuth.getInstance();
        mAuth = instance;
        currentUser = instance.getCurrentUser();
        loginButton = findViewById(R.id.loginBtn);
        dialog = new ProgressDialog(LoginActivity.this);
        dialog.setCanceledOnTouchOutside(false);
        dialog.setMessage("Logging in...");

        loginButton.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                String mail = emailET.getEditText().getText().toString().trim();
                String pass = passwordET.getEditText().getText().toString().trim();
                String str = "";
                if (mail.equals(str) || pass.equals(str)) {
                    Toast.makeText(LoginActivity.this, "failed", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();
                    mAuth.signInWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(@NotNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                dialog.dismiss();
                                startActivity(new Intent(LoginActivity.this.getApplicationContext(), MainActivity.class));
                                finish();
                                return;
                            }
                            dialog.dismiss();
                            Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void forgetPassword(View view) {
        new ForgetPasswordDialog().show(getSupportFragmentManager(), "forgetPassword dialog");
    }

    public void signup(View view) {
        startActivity(new Intent(getApplicationContext(), SignUpActivity.class));
        finish();
    }

    public void onBackPressed() {
        Builder alertDialogBuilder = new Builder(this);
        alertDialogBuilder.setTitle("Exit Application ?🥺");
        alertDialogBuilder.setCancelable(false).setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                LoginActivity.this.moveTaskToBack(true);
                Process.killProcess(Process.myPid());
                System.exit(1);
            }
        }).setNegativeButton("No", new DialogInterface.OnClickListener() {
            public void onClick(DialogInterface dialog, int id) {
                dialog.dismiss();
            }
        });
        alertDialogBuilder.create().show();
    }


    public void onStart() {
        super.onStart();
        if (this.currentUser != null) {
            startActivity(new Intent(getApplicationContext(), MainActivity.class));
            finish();
        }
    }
}
