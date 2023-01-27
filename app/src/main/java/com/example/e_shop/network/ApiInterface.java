package com.example.e_shop.network;

import com.example.e_shop.model.Product;

import java.util.ArrayList;

import io.reactivex.rxjava3.core.Observable;

import retrofit2.http.GET;

public interface ApiInterface {
    @GET("products")
    Observable<ArrayList<Product>> getAllProducts();
    @GET("products/categories")
    Observable<ArrayList<String>> getCategories();

    @GET("products/category/electronics")
    Observable<ArrayList<Product>> getElectronicsCategory();

    @GET("products/category/jewelery")
    Observable<ArrayList<Product>> getJeweleryCategory();

    @GET("products/category/men's clothing")
    Observable<ArrayList<Product>> getMenClothingCategory();

    @GET("products/category/women's clothing")
    Observable<ArrayList<Product>> getWomenClothingCategory();

}
