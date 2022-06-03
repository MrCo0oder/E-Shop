package com.example.e_shop;
import android.app.ProgressDialog;
import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.View.OnClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Products;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
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
    AutoCompleteTextView editTextFilledExposedDropdown;
    UserProfileChangeRequest profileUpdates;
    ProgressDialog dialog;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);
        dialog = new ProgressDialog(SignUpActivity.this);
        dialog.setMessage("SignUp...");
        dialog.setCanceledOnTouchOutside(false);

        passwordET = findViewById(R.id.signupPasswordTextField);
        emailET = findViewById(R.id.signupEmailTextField);
        nameET = findViewById(R.id.signupnameTextField);
        phoneET = findViewById(R.id.signupPhoneTextField);
        String[] type = new String[]{"Male", "Female"};
        ArrayAdapter<String> adapter = new ArrayAdapter<>(this, R.layout.dropdown_menu_popup_item, type);
        editTextFilledExposedDropdown = findViewById(R.id.morfTF);
        editTextFilledExposedDropdown.setAdapter(adapter);
        db = FirebaseFirestore.getInstance();
        final FirebaseAuth instance = FirebaseAuth.getInstance();
        mAuth = instance;


        signupBTN = findViewById(R.id.signupBtn);
        signupBTN.setOnClickListener(new OnClickListener() {
            public void onClick(View v) {
                final String mail = Objects.requireNonNull(emailET.getEditText()).getText().toString().trim();
                String pass = Objects.requireNonNull(passwordET.getEditText()).getText().toString().trim();
                final String name = Objects.requireNonNull(nameET.getEditText()).getText().toString().trim();
                final String phone = Objects.requireNonNull(phoneET.getEditText()).getText().toString().trim();
                final String gender = editTextFilledExposedDropdown.getText().toString();
                String str = "";
                if (gender.equals("Male")) {
                    profileUpdates = new UserProfileChangeRequest.Builder().
                            setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/e-shop-5de8d.appspot.com/o/imge.jpg?alt=media&token=e8226197-d7b8-4e99-98c8-d5ae9b4ac92f")).setDisplayName(name).build();
                } else {
                    profileUpdates = new UserProfileChangeRequest.Builder().
                            setPhotoUri(Uri.parse("https://firebasestorage.googleapis.com/v0/b/e-shop-5de8d.appspot.com/o/girl.jpg?alt=media&token=d3d8bae5-b050-482a-bc55-d90e8150d562")).setDisplayName(name).build();
                }


                if (mail.equals(str) || pass.equals(str) || name.equals(str) || phone.equals(str)) {
                    Toast.makeText(SignUpActivity.this, "failed.", Toast.LENGTH_SHORT).show();
                } else {
                    dialog.show();

                    mAuth.createUserWithEmailAndPassword(mail, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
                        public void onComplete(Task<AuthResult> task) {
                            final String str = "TAG";
                            if (task.isSuccessful()) {

                                dialog.dismiss();
                                Log.d(str, "createUserWithEmail:success");
                                currentUser = instance.getCurrentUser();
                                db.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                                        .set(putUserData(name, phone, mail, gender)).addOnSuccessListener(new OnSuccessListener<Void>() {
                                    @Override
                                    public void onSuccess(Void aVoid) {

                                    }
                                }).addOnFailureListener(new OnFailureListener() {
                                    public void onFailure(Exception e) {
                                        Log.w("TAG", "Error adding document", e);
                                    }
                                });
                                currentUser.updateProfile(profileUpdates)
                                        .addOnCompleteListener(new OnCompleteListener<Void>() {
                                            @Override
                                            public void onComplete(@NonNull Task<Void> task) {
                                                if (task.isSuccessful()) {
                                                    Log.d(str, "User profile updated.");
                                                }
                                            }
                                        });
                                startActivity(new Intent(SignUpActivity.this.getApplicationContext(), MainActivity.class));
                                finish();
                                return;
                            }
                            dialog.dismiss();
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
        finish();
    }


    public Map<String, Object> putUserData(String name, String phone, String mail, String gender) {
        Map<String, Object> user = new HashMap();
        user.put("name", name);
        user.put("phone", phone);
        user.put("mail", mail);
        user.put("address", " Not defined yet");
        user.put("gender", gender);
        return user;
    }
}
