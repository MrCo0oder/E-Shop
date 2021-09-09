package com.example.e_shop;

import androidx.appcompat.app.AppCompatActivity;

import android.annotation.SuppressLint;
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
    TextView  titleTV,qtyTV,priceTV ,descriptionTV ,counterTV;
    ImageView imageView;
    Button backButton,plusBtn,minusBtn;
    Integer productId;
    String productName;
    String productCat;
    double productPrice;
    String productDescription;
    String imageUrl;
    ProgressBar progressBar;
    int c=1;

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
        plusBtn=findViewById(R.id.plusBTN);
        minusBtn=findViewById(R.id.minusBTN);
        counterTV=findViewById(R.id.counterTV);

        plusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                c++;
                priceTV.setText((productPrice*c)+" $");
                counterTV.setText(c+"");            }
        });
        minusBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (c>1){
                    c--;
                    priceTV.setText((productPrice*c)+" $");
                    counterTV.setText(c+"");                }
            }
        });
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
            counterTV.setText(c+"");
            titleTV.setText(productName);
           priceTV.setText((productPrice*c)+" $");
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
            productPrice=getIntent().getDoubleExtra("productPrice",0.0);
            productDescription=getIntent().getStringExtra("productDescription");
            imageUrl=getIntent().getStringExtra("imageUrl");



        }
        else
        {
            Toast.makeText(this,"no data",Toast.LENGTH_SHORT).show();
        }
    }
}