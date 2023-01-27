package com.example.e_shop.ui;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.example.e_shop.R;
import com.example.e_shop.model.CartItem;
import com.example.e_shop.model.Product;
import com.github.chrisbanes.photoview.PhotoView;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.FirebaseFirestore;
import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

import java.util.Objects;


public class ProductDetailsActivity extends AppCompatActivity {
    TextView titleTV, qtyTV, priceTV, descriptionTV, counterTV;
    PhotoView imageView;
    Button backButton, plusBtn, minusBtn, cart;
    Product product;
    ProgressBar progressBar;
    int c = 1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        init();
        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                Double num = product.getPrice() * c;
                String p = String.format("%.2f $\n", num);
                priceTV.setText(p);
                counterTV.setText(String.valueOf(c));
            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c > 1) {
                    c--;
                    Double num = product.getPrice() * c;
                    String p = String.format("%.2f $\n", num);

                    priceTV.setText(p);
                    counterTV.setText(String.valueOf(c));
                }
            }
        });
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });
        getData();
        setData();
        cart.setOnClickListener(new View.OnClickListener() {
            @Override

            public void onClick(View v) {
                FirebaseFirestore db = FirebaseFirestore.getInstance();
                db.collection("users")
                        .document(Objects.requireNonNull(FirebaseAuth.getInstance().getUid()))
                        .collection("cart").document(String.valueOf(System.currentTimeMillis()))
                        .set(new CartItem(product.getId(), c, product.getPrice() * c, product.getTitle(), product.getPrice(), product.getImage()))
                        .addOnCompleteListener(task -> {
                            if (task.isSuccessful()) {
                                Toast.makeText(getBaseContext(), "added", Toast.LENGTH_SHORT).show();
                            } else {
                                Toast.makeText(getBaseContext(), task.getException().toString(), Toast.LENGTH_SHORT).show();
                            }
                        });

            }
        });
    }

    private void init() {
        imageView = findViewById(R.id.imageView8);
        titleTV = findViewById(R.id.title_details_tv);
        priceTV = findViewById(R.id.price_details_tv);
        descriptionTV = findViewById(R.id.desctv);
        progressBar = findViewById(R.id.progressBar);
        backButton = findViewById(R.id.back);
        plusBtn = findViewById(R.id.plusBTN);
        minusBtn = findViewById(R.id.minusBTN);
        counterTV = findViewById(R.id.counterTV);
        cart = findViewById(R.id.addtocartbtn);
    }

    void setData() {
        counterTV.setText(String.valueOf(c));
        titleTV.setText(product.getTitle());
        String s = (product.getPrice() * c) + " $";
        priceTV.setText(s);
        descriptionTV.setText(product.getDescription());
        Picasso.get().load(product.getImage()).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
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
}