package com.example.e_shop;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import android.annotation.SuppressLint;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_account);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (savedInstanceState == null) {
            bottomNavigationView.setSelectedItemId(R.id.account_bnav);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new AccountFragment()).commit();
        }
    }

    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
                @SuppressLint("NonConstantResourceId")
                @Override
                public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                    try {
                        Fragment selectedFragment = null;
                        switch (item.getItemId()) {
                            case R.id.account_bnav:
                                selectedFragment = new AccountFragment();
                                break;
                            case R.id.cart_bnav:
                                selectedFragment = new CartFragment();
                                break;
                            case R.id.orders_bnav:
                                selectedFragment = new OrdersFragment();
                                break;
                        }
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).commit();
                    } catch (Exception e) {
                        Log.d("EEE", e.getLocalizedMessage());
                    }

                    return true;
                }
            };
}