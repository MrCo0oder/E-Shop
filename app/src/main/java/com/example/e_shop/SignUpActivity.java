package com.example.e_shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;

public class SignUpActivity extends AppCompatActivity {
    FirebaseUser currentUser;
    FirebaseFirestore db;
    TextInputLayout emailET;
    FirebaseAuth mAuth;
    TextInputLayout nameET;
    TextInputLayout passwordET;
    TextInputLayout phoneET;
    Button signupBTN;

    /* Access modifiers changed, original: protected */
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        passwordET = findViewById(R.id.signupPasswordTextField);
        emailET = findViewById(R.id.signupEmailTextField);
        nameET = findViewById(R.id.signupnameTextField);
        phoneET = findViewById(R.id.signupPhoneTextField);
        db = FirebaseFirestore.getInstance();
        FirebaseAuth instance = FirebaseAuth.getInstance();
        mAuth = instance;
        currentUser = instance.getCurrentUser();

        signupBTN = findViewById(R.id.signupBtn);
        signupBTN.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final String mail = Objects.requireNonNull(emailET.getEditText()).getText().toString().trim();
                String pass = Objects.requireNonNull(passwordET.getEditText()).getText().toString().trim();
                final String name = Objects.requireNonNull(nameET.getEditText()).getText().toString().trim();
                final String phone = Objects.requireNonNull(phoneET.getEditText()).getText().toString().trim();
                String str = "";
                if (mail.equals(str) || pass.equals(str) || name.equals(str) || phone.equals(str)) {
                    Toast.makeText(SignUpActivity.this, "failed.", Toast.LENGTH_SHORT).show();
                } else {
                    mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            String str = "TAG";
                            if (task.isSuccessful()) {
                                Log.d(str, "createUserWithEmail:success");
                                FirebaseUser user = SignUpActivity.this.mAuth.getCurrentUser();
                                db.collection("users").add(SignUpActivity.this.putUserData(name, phone, mail, null)).addOnSuccessListener(new OnSuccessListener<DocumentReference>() {
                                    public void onSuccess(DocumentReference documentReference) {
                                        Log.d("TAG", "DocumentSnapshot added with ID: " + documentReference.getId());
                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    public void onFailure(Exception e) {
                                        Log.w("TAG", "Error adding document", e);
                                    }
                                });
                                startActivity(new Intent(SignUpActivity.this.getApplicationContext(), MainActivity.class));
                                return;
                            }
                            Log.w(str, "createUserWithEmail:failure", task.getException());
                            Toast.makeText(SignUpActivity.this, "Authentication failed.", Toast.LENGTH_SHORT).show();
                        }
                    });
                }
            }
        });
    }

    public void login(View view) {
        startActivity(new Intent(getApplicationContext(), LoginActivity.class));
    }

    public void onStart() {
        super.onStart();
    }

    /* Access modifiers changed, original: 0000 */
    public Map<String, Object> putUserData(String name, String phone, String mail, ArrayList<Products> cart) {
        Map<String, Object> user = new HashMap();
        user.put("name", name);
        user.put("phone", phone);
        user.put("mail", mail);
        user.put("cart", cart);
        return user;
    }
}
