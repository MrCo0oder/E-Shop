package com.example.e_shop.api;

import com.example.e_shop.model.Products;
import java.util.List;
import retrofit2.Call;
import retrofit2.http.GET;

public interface ApiInterface {
    @GET("products")
    Call<List<Products>> getAllProducts();

    @GET("products/categories")
    Call<List<String>> getAllProductsCategories();

    @GET("products/category/electronics")
    Call<List<Products>> getElectronicsCategory();

    @GET("products/category/jewelery")
    Call<List<Products>> getJeweleryCategory();

    @GET("products/category/men's clothing")
    Call<List<Products>> getMenClothingCategory();

    @GET("products/category/women's clothing")
    Call<List<Products>> getWomenClothingCategory();

}
