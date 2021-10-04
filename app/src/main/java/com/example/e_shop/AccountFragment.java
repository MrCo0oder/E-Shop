package com.example.e_shop;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;

public class AccountFragment extends Fragment {
    ImageButton editIB, logoutIB;
    ImageView profilePic;
    TextView welcomeTV, emailTV, nameTV, phoneTV, addressTV;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore mFirebaseFirestore;
    DocumentReference docRef;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Toast.makeText(getActivity(), "Swipe up for Options. 🔼", Toast.LENGTH_LONG).show();
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        profilePic = view.findViewById(R.id.profile_pic);
        welcomeTV = view.findViewById(R.id.welcomeTV);
        editIB = view.findViewById(R.id.editimageView);
        logoutIB = view.findViewById(R.id.logoutimageView);
        emailTV = view.findViewById(R.id.emailTV);
        nameTV = view.findViewById(R.id.fullnameTV);
        phoneTV = view.findViewById(R.id.phoneTV);
        addressTV = view.findViewById(R.id.addressTV);
        Picasso.get().load(user.getPhotoUrl()).into(profilePic, new Callback() {
            @Override
            public void onSuccess() {

            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getActivity(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });
        docRef = mFirebaseFirestore.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nameTV.setText(task.getResult().get("name").toString());
                        phoneTV.setText(task.getResult().get("phone").toString());
                        emailTV.setText(task.getResult().get("mail").toString());
                        addressTV.setText(task.getResult().get("address").toString());
                        welcomeTV.setText("Welcome, " + task.getResult().get("name").toString().split(" ")[0]);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Toast.makeText(getActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });


        editIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(getActivity(), "Edit", Toast.LENGTH_SHORT).show();
            }
        });
        logoutIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getActivity(), "Bye 👋", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                Objects.requireNonNull(getActivity()).finish();

            }
        });
        return view;
    }
}
