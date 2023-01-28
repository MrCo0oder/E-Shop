package com.example.e_shop.ui;

import android.Manifest;
import android.content.pm.PackageManager;
import android.location.Address;
import android.location.Geocoder;
import android.location.Location;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.core.app.ActivityCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_shop.R;
import com.google.android.gms.location.FusedLocationProviderClient;
import com.google.android.gms.location.LocationServices;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.io.IOException;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Objects;

public class SettingsFragment extends Fragment {
    Button closeBTN, saveBTN;
    Button imageButton;
    EditText fullnameET, addressET, phoneET;
    FirebaseAuth mAuth;
    FirebaseUser user;
    FirebaseFirestore mFirebaseFirestore;
    DocumentReference docRef;
    FusedLocationProviderClient fusedLocationProviderClient;
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View view = inflater.inflate(R.layout.fragment_settings, container, false);
        fusedLocationProviderClient = LocationServices.getFusedLocationProviderClient(requireActivity());

        mAuth = FirebaseAuth.getInstance();
        user = FirebaseAuth.getInstance().getCurrentUser();
        mFirebaseFirestore = FirebaseFirestore.getInstance();
        fullnameET = view.findViewById(R.id.fullnameET);
        addressET = view.findViewById(R.id.addressET);
        phoneET = view.findViewById(R.id.phoneET);
        closeBTN = view.findViewById(R.id.closeBTN);
        saveBTN = view.findViewById(R.id.saveBTN);
        imageButton = view.findViewById(R.id.currentLocationBTN);
        imageButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (ActivityCompat.checkSelfPermission(getContext(), Manifest.permission.ACCESS_FINE_LOCATION) == PackageManager.PERMISSION_GRANTED) {
                    getLocation();

                } else {
                    ActivityCompat.requestPermissions(requireActivity(), new String[]{Manifest.permission.ACCESS_FINE_LOCATION}, 44);

                }
            }
        });
        closeBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                assert getFragmentManager() != null;
                getFragmentManager().beginTransaction()
                        .replace(R.id.fragmentContainer, new AccountFragment())
                        .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                        .commit();

            }
        });
        saveBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (fullnameET.equals("") || addressET.equals("") || phoneET.equals("")) {
                    Toast.makeText(getActivity(), "failed.", Toast.LENGTH_SHORT).show();
                } else {
                    mFirebaseFirestore.collection("users")
                            .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                            .update(putUserData(fullnameET.getText().toString(), phoneET.getText().toString(), addressET.getText().toString()))
                            .addOnSuccessListener(new OnSuccessListener<Void>() {
                                @Override
                                public void onSuccess(Void aVoid) {

                                    getFragmentManager().beginTransaction()
                                            .replace(R.id.fragmentContainer, new AccountFragment())
                                            .setTransition(FragmentTransaction.TRANSIT_FRAGMENT_CLOSE)
                                            .commit();
                                }
                            }).addOnFailureListener(new OnFailureListener() {
                        public void onFailure(Exception e) {
                            Log.w("TAG", "Error adding document", e);
                            Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_LONG).show();

                        }
                    });
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
                        fullnameET.setText(task.getResult().get("name").toString());
                        phoneET.setText(task.getResult().get("phone").toString());
                        addressET.setText(task.getResult().get("address").toString());
                    } else {
                        Log.d("TAG", "No such document");
                    }
                } else {
                    Toast.makeText(requireActivity(), task.getException().getLocalizedMessage(), Toast.LENGTH_SHORT).show();
                }
            }
        });
        return view;
    }

    private void getLocation() {
        if (ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_FINE_LOCATION) != PackageManager.PERMISSION_GRANTED && ActivityCompat.checkSelfPermission(requireContext(), Manifest.permission.ACCESS_COARSE_LOCATION) != PackageManager.PERMISSION_GRANTED) {


        }
        fusedLocationProviderClient.getLastLocation().addOnCompleteListener(new OnCompleteListener<Location>() {
            @Override
            public void onComplete(@NonNull Task<Location> task) {
                Location location = task.getResult();
                if (location != null) {
                    Geocoder geocoder = new Geocoder(getContext(), Locale.getDefault());
                    try {
                        List<Address> addresses = geocoder.getFromLocation(location.getLatitude(), location.getLongitude(), 1);
                        addressET.setText(addresses.get(0).getAddressLine(0));

                    } catch (IOException e) {
                        Toast.makeText(getActivity(), e.getLocalizedMessage(), Toast.LENGTH_SHORT).show();

                    }
                }
            }
        });


    }

    private Map<String, Object> putUserData(String name, String phone, String address) {
        Map<String, Object> user = new HashMap();
        user.put("name", name);
        user.put("phone", phone);
        user.put("address", address);

        return user;
    }
}
