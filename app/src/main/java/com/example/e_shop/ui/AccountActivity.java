package com.example.e_shop.ui;


import android.content.Context;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.e_shop.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class AccountActivity extends AppCompatActivity {
    Context c;
    private final BottomNavigationView.OnNavigationItemSelectedListener navigationItemSelectedListener =
            new BottomNavigationView.OnNavigationItemSelectedListener() {
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
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer, selectedFragment).setTransition(FragmentTransaction.TRANSIT_FRAGMENT_OPEN).commit();
                    } catch (Exception e) {
                        Log.d("EEE", e.getLocalizedMessage());
                    }

                    return true;
                }
            };

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        c = AccountActivity.this;
        setContentView(R.layout.activity_account);
        BottomNavigationView bottomNavigationView = findViewById(R.id.bottomNavigationView);
        bottomNavigationView.setOnNavigationItemSelectedListener(navigationItemSelectedListener);
        if (savedInstanceState == null) {

            if (getIntent().hasExtra("FLAG")) {
                int flag = getIntent().getIntExtra("FLAG", 0);


                switch (flag) {
                    case 1:
                        bottomNavigationView.setSelectedItemId(R.id.account_bnav);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                new AccountFragment()).commit();
                        break;
                    case 2:
                        bottomNavigationView.setSelectedItemId(R.id.cart_bnav);
                        getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                                new CartFragment()).commit();
                        break;

                }

            }
        } else {

            bottomNavigationView.setSelectedItemId(R.id.account_bnav);
            getSupportFragmentManager().beginTransaction().replace(R.id.fragmentContainer,
                    new AccountFragment()).commit();

        }
    }
}