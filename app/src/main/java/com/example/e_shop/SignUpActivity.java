package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.example.e_shop.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    private FirebaseAuth mAuth;
    Button signupBTN;
    TextInputLayout emailET,passwordET,nameET,phoneET;
    FirebaseUser currentUser ;
    FirebaseFirestore db ;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        passwordET=findViewById(R.id.signupPasswordTextField);
        emailET=findViewById(R.id.signupEmailTextField);
        nameET=findViewById(R.id.signupnameTextField);
        phoneET=findViewById(R.id.signupPhoneTextField);
        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();
        currentUser = mAuth.getCurrentUser();
        signupBTN=findViewById(R.id.signupBtn);
        signupBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                final String mail,pass ,name,phone;
                mail= Objects.requireNonNull(emailET.getEditText()).getText().toString().trim();
                pass= Objects.requireNonNull(passwordET.getEditText()).getText().toString().trim();
                name= Objects.requireNonNull(nameET.getEditText()).getText().toString().trim();
                phone= Objects.requireNonNull(phoneET.getEditText()).getText().toString().trim();

                if (!(mail.equals("")) && !(pass.equals(""))&& !(name.equals(""))&& !(phone.equals("")))
                {

                    mAuth.createUserWithEmailAndPassword(mail,pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        @Override
                        public void onComplete(@NonNull Task<AuthResult> task) {
                            if (task.isSuccessful()) {
                                Log.d("TAG", "createUserWithEmail:success");
                                FirebaseUser user = mAuth.getCurrentUser();

                                // Add a new document with a generated ID
                                db.collection("users")
                                        .add(putUserData(name,phone,mail,null))
                                        .addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                            @Override
                                            public void onSuccess(DocumentReference documentReference) {
                                                Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                            }
                                        })
                                        .addOnFailureListener(new OnFailureListener() {
                                            @Override
                                            public void onFailure(@NonNull Exception e) {
                                                Log.w("TAG", "Error adding document", e);
                                            }
                                        });
                                Intent intent=new Intent(getApplicationContext(),MainActivity.class);
                                startActivity(intent);
                            } else {
                                // If sign in fails, display a message to the user.
                                Log.w("TAG", "createUserWithEmail:failure", task.getException());
                                Toast.makeText(SignUpActivity.this, "Authentication failed.",
                                        Toast.LENGTH_SHORT).show();
                            }

                        }
                    });
                }
                else{
                    Toast.makeText(SignUpActivity.this, "failed.",
                            Toast.LENGTH_SHORT).show();
                }
            }
        });

    }

    public void login(View view) {
        Intent intent=new Intent(getApplicationContext(),LoginActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onStart() {
        super.onStart();

    }
    Map<String, Object> putUserData(String name, String phone, String mail, ArrayList<Products> cart){
        Map<String, Object> user = new HashMap<>();
        user.put("name", name);
        user.put("phone", phone);
        user.put("mail", mail);
        user.put("cart",cart);

        return user;
    }
}
