package com.example.e_shop.ui;

import static android.app.Activity.RESULT_CANCELED;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_shop.R;
import com.github.dhaval2404.imagepicker.ImagePicker;
import com.google.android.gms.tasks.OnCanceledListener;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
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
    ProgressBar progressBar;
    CardView cardView;
    Button fab;
    StorageReference storageReference;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_account, container, false);
        Toast.makeText(getActivity(), "Swipe up for Options. 🔼", Toast.LENGTH_LONG).show();
        progressBar = view.findViewById(R.id.progressBar5);
        progressBar.setVisibility(View.VISIBLE);
        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        fab = view.findViewById(R.id.floatingActionButton);
        profilePic = view.findViewById(R.id.profile_pic);
        cardView = view.findViewById(R.id.cardView);
        welcomeTV = view.findViewById(R.id.welcomeTV);
        editIB = view.findViewById(R.id.editimageView);
        logoutIB = view.findViewById(R.id.logoutimageView);
        emailTV = view.findViewById(R.id.emailTV);
        nameTV = view.findViewById(R.id.fullnameTV);
        phoneTV = view.findViewById(R.id.phoneTV);
        addressTV = view.findViewById(R.id.addressTV);
        fab.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                ImagePicker.Companion.with(AccountFragment.this).galleryOnly()
                        .cropSquare()
                        .compress(720)
                        .maxResultSize(1080, 1080)
                        .start();
            }
        });

        Picasso.get().load(user.getPhotoUrl()).placeholder(R.drawable.profilepicph).into(profilePic, new Callback() {
            @Override
            public void onSuccess() {

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {

                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }
        });
        docRef = mFirebaseFirestore.collection("users").document(mAuth.getUid());
        docRef.get().addOnCompleteListener(new OnCompleteListener<DocumentSnapshot>() {
            @Override
            public void onComplete(@NonNull Task<DocumentSnapshot> task) {
                if (task.isSuccessful()) {
                    DocumentSnapshot document = task.getResult();
                    if (document.exists()) {
                        nameTV.setText(task.getResult()
                                .get("name").toString());
                        phoneTV.setText(task.getResult()
                                .get("phone").toString());
                        emailTV.setText(task.getResult()
                                .get("mail").toString());
                        addressTV.setText(task.getResult()
                                .get("address").toString());
                        welcomeTV.setText("Welcome, "
                                + task.getResult().get("name").toString().split(" ")[0]);
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Toast.makeText(getActivity(),
                            Objects.requireNonNull(task.getException()).getLocalizedMessage(),
                            Toast.LENGTH_SHORT).show();
                }
            }
        });


        editIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new SettingsFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN)
                        .commit();

            }
        });
        logoutIB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                mAuth.signOut();
                Toast.makeText(getActivity(), "Bye 👋", Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getActivity(), LoginActivity.class));
                requireActivity().finish();

            }
        });
        return view;
    }

    void uploadProfilePic(final Uri uri) {
//        SimpleDateFormat formatter=new SimpleDateFormat("yyyy_MM_dd_hh_mm_ss", Locale.ENGLISH);
//        Date n=new Date();
//        String fileName=formatter.format(n);
        storageReference = FirebaseStorage.getInstance().getReference(mAuth.getUid() + "/pic");
        storageReference.putFile(uri).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                UserProfileChangeRequest profileUpdates;
                profileUpdates = new UserProfileChangeRequest.Builder().setPhotoUri(uri).build();
                mAuth.getCurrentUser().updateProfile(profileUpdates);
            }

        });
    }

    @SuppressWarnings("deprecation")
    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode != RESULT_CANCELED) {
            Uri uri = null;
            if (data != null) {
                uri = data.getData();
            }
            profilePic.setImageURI(uri);
            uploadProfilePic(uri);
        }
    }


}
