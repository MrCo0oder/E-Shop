package com.example.e_shop;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;



public class LoginActivity extends AppCompatActivity {
Button loginButton;
    TextInputLayout emailET,passwordET;
Boolean flag=false;
    private FirebaseAuth mAuth;
    FirebaseUser currentUser ;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        emailET=findViewById(R.id.emailTextField);
        passwordET=findViewById(R.id.loginPasswordTextField);

        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();

        loginButton =findViewById(R.id.loginBtn);
        loginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail,pass;
                mail=emailET.getEditText().getText().toString().trim();
                pass=passwordET.getEditText().getText().toString().trim();
                if (!(mail.equals("")) && !(pass.equals("")))
                {
                   mAuth.signInWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                       @Override
                       public void onComplete(@NonNull Task<AuthResult> task) {
                           if(task.isSuccessful()){
                               Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                               startActivity(intent);
                           }else
                           {
                               Toast.makeText(LoginActivity.this, task.getException().getLocalizedMessage(),
                                       Toast.LENGTH_SHORT).show();

                           }
                       }
                   });
                }
                else{
                    Toast.makeText(LoginActivity.this, "failed",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


    }

    public void forgetPassword(View view) {
        Toast.makeText(this,"Ok 😏",Toast.LENGTH_SHORT).show();
    }

    public void signup(View view) {

        Intent intent=new Intent(getApplicationContext(),SignUpActivity.class);
        startActivity(intent);
    }

    @Override
    public void onBackPressed() {

        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setCancelable(false)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                moveTaskToBack(true);
                                android.os.Process.killProcess(android.os.Process.myPid());
                                System.exit(1);
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();
                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
    }

    @Override
    protected void onStart() {
        super.onStart();
        if(currentUser!=null)
        {
            Intent intent=new Intent(getApplicationContext(),MainActivity.class);
            startActivity(intent);
        }

    }

    public void login(View view) {

    }
}