package com.example.e_shop.api;

import com.example.e_shop.model.ProductCategory;
import com.example.e_shop.model.Products;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("products")
    public Call<List<Products>> getAllProducts();
    @GET("products/categories")
    public Call<List<String>> getAllProductsCategories();

}
