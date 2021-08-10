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
import com.example.e_shop.model.ProductCategory;
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
    List<ProductCategory> productsCategoriesList=new ArrayList<>();
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
                System.out.println(response.body().get(0)+" "+response.body().get(2));
                productsCategoriesList.add(new ProductCategory(response.body().get(0),"https://freepngimg.com/thumb/computer/32997-1-gaming-computer-file.png"));
                productsCategoriesList.add(new ProductCategory(response.body().get(1),"https://freepngimg.com/thumb/ring/34315-3-heart-ring-photos.png"));
                productsCategoriesList.add(new ProductCategory(response.body().get(2),"https://freepngimg.com/thumb/dress%20shirt/2-dress-shirt-png-image.png"));
                productsCategoriesList.add(new ProductCategory(response.body().get(3),"https://freepngimg.com/thumb/dress%20shirt/13-dress-shirt-png-image.png"));
                System.out.println(productsCategoriesList.get(0).getCategoryName());
                setProductCategoryAdapterRecycler((ArrayList<ProductCategory>) productsCategoriesList);
            }

            @Override
            public void onFailure(Call<List<String>> call, Throwable t) {
                Toast.makeText(getApplicationContext(),t.getMessage(),Toast.LENGTH_SHORT).show();
            }
        });



    }
    private void setProductCategoryAdapterRecycler(ArrayList<ProductCategory> productCategoryList){

        productCategoryRV = findViewById(R.id.cat_rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.HORIZONTAL, false);
        productCategoryRV.setHasFixedSize(true);
        productCategoryRV.setLayoutManager(layoutManager);
        CategoryAdapter = new CategoryAdapter(this, productCategoryList);
        productCategoryRV.setAdapter(CategoryAdapter);

    }
    private void setProductsRecycler(List<Products> productsList){

        productsRV = findViewById(R.id.product_rv);
        RecyclerView.LayoutManager layoutManager= new LinearLayoutManager(this, RecyclerView.VERTICAL, false);
        productsRV.setLayoutManager(layoutManager);
        productsAdapter = new ProductsAdapter(this, productsList);
        productsRV.setAdapter(productsAdapter);

    }
}