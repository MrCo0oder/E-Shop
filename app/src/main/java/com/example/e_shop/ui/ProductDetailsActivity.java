package com.example.e_shop.ui;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;

import com.example.e_shop.R;
import com.example.e_shop.databinding.ActivityProductDetailsBinding;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProductDetailsActivity extends AppCompatActivity {
    ActivityProductDetailsBinding binding;
    Product product;
    int c = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_product_details);
        init();
        if (savedInstanceState != null) {
            c = savedInstanceState.getInt("counter");
        }
    }

    private void init() {
        binding.plusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                increment();
            }
        });
        binding.minusBTN.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                decrement();
            }
        });
        binding.back.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });
        getData();
        setData();
        binding.addtocartbtn.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users").document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid())).collection("cart").document(String.valueOf(System.currentTimeMillis())).set(new CartItem(product.getId(), c, product.getPrice() * c, product.getTitle(), product.getPrice(), product.getImage())).addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        Toast.makeText(getBaseContext(), "added", Toast.LENGTH_SHORT).show();
                    } else {
                        Toast.makeText(getBaseContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                    }
                });

            }
        });
    }

    void setData() {
        binding.counterTV.setText(String.valueOf(c));
        binding.titleDetailsTv.setText(product.getTitle());
        String s = (product.getPrice() * c) + " $";
        binding.priceDetailsTv.setText(s);
        binding.desctv.setText(product.getDescription());
        Picasso.get().load(product.getImage()).into(binding.imageView8, new Callback() {
            @Override
            public void onSuccess() {
                if (binding.progressBar != null) {
                    binding.progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(), e.getMessage(), Toast.LENGTH_SHORT).show();
            }
        });

    }

    void getData() {
        if (getIntent().hasExtra("PRODUCT")) {
            product = (Product) getIntent().getSerializableExtra("PRODUCT");
        } else {
            Toast.makeText(this, "no data", Toast.LENGTH_SHORT).show();
        }
    }

    void increment() {

        c++;
        Double num = product.getPrice() * c;
        String p = String.format("%.2f $\n", num);
        binding.priceDetailsTv.setText(p);
        binding.counterTV.setText(String.valueOf(c));
    }

    void decrement() {
        if (c > 1) {
            c--;
            Double num = product.getPrice() * c;
            String p = String.format("%.2f $\n", num);

            binding.priceDetailsTv.setText(p);
            binding.counterTV.setText(String.valueOf(c));
        }
    }

    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("counter", c);
    }
}