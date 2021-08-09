package com.example.e_shop;

import android.os.Bundle;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.e_shop.adapter.CategoryAdapter;
import com.example.e_shop.adapter.ProductsAdapter;
import com.example.e_shop.api.ApiInterface;
import com.example.e_shop.model.Products;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class MainActivity extends AppCompatActivity {
   CategoryAdapter CategoryAdapter;
    RecyclerView productCategoryRV;
    ProgressBar progressBar;
    private ProductsAdapter productsAdapter;
    List<Products>productsList=new ArrayList<>();
    List<String>productsCategoriesList=new ArrayList<>();
    private RecyclerView productsRV;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        progressBar=findViewById(R.id.progressBar3);

        Retrofit retrofit=new Retrofit.Builder().baseUrl("https://fakestoreapi.com/").addConverterFactory(GsonConverterFactory.create()).build();
        ApiInterface apiInterface=retrofit.create(ApiInterface.class);
        final Call<List<Products>> productsCall=apiInterface.getAllProducts();
        productsCall.enqueue(new Callback<List<Products>>() {
            @Override
            public void onResponse(Call<List<Products>> call, Response<List<Products>> response) {
                if (progressBar != null) {
                    progressBar.setVisibility(View.GONE);
                }
                productsList=response.body();
                setProductsRecycler(productsList);

            }

            @Override
            public void onFailure(Call<List<Products>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getLocalizedMessage(),Toast.LENGTH_SHORT).show();
            }
        });
       final Call<List<String>> productsCategoriesCall=apiInterface.getAllProductsCategories();
        productsCategoriesCall.enqueue(new Callback<List<String>>() {
            @Override
            public void onResponse(Call<List<String>> call, Response<List<String>> response) {
                productsCategoriesList=response.body();

                setProductCategoryAdapterRecycler(productsCategoriesList);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void setProductCategoryAdapterRecycler(List<String> productCategoryList){

        productCategoryRV = findViewById(R.id.cat_rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCategoryRV.setLayoutManager(layoutManager);
        CategoryAdapter = new CategoryAdapter(this, productCategoryList);
        productCategoryRV.setAdapter(CategoryAdapter);

    }
    private void setProductsRecycler(List<Products> productsList){

        productsRV = findViewById(R.id.product_rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productsRV.setLayoutManager(layoutManager);
        productsAdapter = new ProductsAdapter(this, productsList);
        productsRV.setAdapter(productsAdapter);

    }
}