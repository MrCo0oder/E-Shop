package com.example.e_shop.repository;

import com.example.e_shop.model.Product;
import com.example.e_shop.network.ApiInterface;

import java.util.ArrayList;


import javax.inject.Inject;

import io.reactivex.rxjava3.core.Observable;

public class Repository {
    private final ApiInterface apiInterfaceService;

    @Inject
    public Repository(ApiInterface apiInterfaceService) {
        this.apiInterfaceService = apiInterfaceService;
    }

    public Observable<ArrayList<Product>> getAllProducts() {
        return apiInterfaceService.getAllProducts();
    }
    public Observable<ArrayList<String>> getCategories() {
        return apiInterfaceService.getCategories();
    }
    public Observable<ArrayList<Product>> getElectronicsCategory() {
        return apiInterfaceService.getElectronicsCategory();
    }
    public Observable<ArrayList<Product>> getJeweleryCategory() {
        return apiInterfaceService.getJeweleryCategory();
    }
    public Observable<ArrayList<Product>> getMenClothingCategory() {
        return apiInterfaceService.getMenClothingCategory();
    }
    public Observable<ArrayList<Product>> getWomenClothingCategory() {
        return apiInterfaceService.getWomenClothingCategory();
    }


}
