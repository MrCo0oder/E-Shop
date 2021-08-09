package com.example.e_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.squareup.picasso.Callback;
import com.squareup.picasso.Picasso;

public class ProductDetailsActivity extends AppCompatActivity {
    TextView  titleTV,qtyTV,priceTV,descriptionTV;
    ImageView imageView;
    Button backButton;
    Integer productId;
    String productName, productCat, productPrice,productDescription;
    String imageUrl;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_details);
        imageView=findViewById(R.id.imageView8);
        titleTV=findViewById(R.id.title_details_tv);
        priceTV=findViewById(R.id.price_details_tv);
        descriptionTV=findViewById(R.id.desctv);
        progressBar=findViewById(R.id.progressBar);
        backButton=findViewById(R.id.back);
        backButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(getApplicationContext(), MainActivity.class);

                startActivity(intent);
            }
        });
        getData();
        setData();
    }
    void setData(){
            titleTV.setText(productName);
           priceTV.setText(productPrice+" $");
           descriptionTV.setText(productDescription);
        Picasso.get().load(imageUrl).into(imageView, new Callback() {
            @Override
            public void onSuccess() {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
            }

            @Override
            public void onError(Exception e) {
                Toast.makeText(getApplicationContext(),e.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });

    }
    void getData(){
        if(getIntent().hasExtra("productId")
                &&getIntent().hasExtra("productName")
                &&getIntent().hasExtra("productCat")
                &&getIntent().hasExtra("productPrice"+"")
                &&getIntent().hasExtra("productDescription")
                &&getIntent().hasExtra("imageUrl"))
        {
            productId=getIntent().getIntExtra("productId",1);
            productName=getIntent().getStringExtra("productName");
            productCat =getIntent().getStringExtra("productCat");
            productPrice=getIntent().getStringExtra("productPrice");
            productDescription=getIntent().getStringExtra("productDescription");
            imageUrl=getIntent().getStringExtra("imageUrl");



        }
        else
        {
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
    }
}