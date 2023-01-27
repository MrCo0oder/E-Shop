package com.example.e_shop.viewmodels;

import android.annotation.SuppressLint;
import android.util.Log;

import androidx.hilt.lifecycle.ViewModelInject;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.e_shop.model.Product;
import com.example.e_shop.model.ProductCategory;
import com.example.e_shop.repository.Repository;

import java.util.ArrayList;
import java.util.List;

import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers;
import io.reactivex.rxjava3.functions.Function;
import io.reactivex.rxjava3.schedulers.Schedulers;


public class ProductsViewModel extends ViewModel {
    private final Repository repository;
    private final MutableLiveData<ArrayList<Product>> productsList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Product>> menProductsList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Product>> womenProductsList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Product>> jeweleryList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<Product>> electronicsList = new MutableLiveData<>();
    private final MutableLiveData<ArrayList<ProductCategory>> categoriesList = new MutableLiveData<>();

    @ViewModelInject
    public ProductsViewModel(Repository repository) {
        this.repository = repository;
    }

    public MutableLiveData<ArrayList<Product>> getProductsList() {
        return productsList;
    }


    public MutableLiveData<ArrayList<Product>> getMenProductsList() {
        return menProductsList;
    }

    public MutableLiveData<ArrayList<Product>> getWomenProductsList() {
        return womenProductsList;
    }

    public MutableLiveData<ArrayList<Product>> getJeweleryList() {
        return jeweleryList;
    }

    public MutableLiveData<ArrayList<Product>> getElectronicsList() {
        return electronicsList;
    }

    public MutableLiveData<ArrayList<ProductCategory>> getCategoriesList() {
        return categoriesList;
    }

    @SuppressLint("CheckResult")
    public void getProducts() {
        repository.getAllProducts().subscribeOn(Schedulers.io()).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> productsList.setValue(result), error -> Log.e("ViewModel", error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getCategories() {
        repository.getCategories().subscribeOn(Schedulers.io()).map(new Function<ArrayList<String>, List<ProductCategory>>() {
            @Override
            public ArrayList<ProductCategory> apply(ArrayList<String> strings) throws Throwable {
                strings.add(0, "All Products");
                ArrayList<ProductCategory> newList = new ArrayList<>();
                List<String> images = new ArrayList<>();
                images.add("https://firebasestorage.googleapis.com/v0/b/e-shop-5de8d.appspot.com/o/explore_icon.png?alt=media&token=8df78b2c-25ef-4147-998c-417f1d693b9e");
                images.add("https://pngimg.com/uploads/computer_pc/computer_pc_PNG17486.png");
                images.add("https://pngimg.com/uploads/jewelry/jewelry_PNG6792.png");
                images.add("https://pngimg.com/uploads/jacket/jacket_PNG8043.png");
                images.add("https://pngimg.com/uploads/dress/dress_PNG147.png");
                for (int i = 0; i < strings.size(); i++) {
                    newList.add(i, new ProductCategory(strings.get(i), images.get(i)));
                }
                return newList;
            }
        }).observeOn(AndroidSchedulers.mainThread()).subscribe(result -> categoriesList.setValue((ArrayList<ProductCategory>) result), error -> Log.e("ViewModel:Cats->", error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getElectronics() {
        repository.getElectronicsCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> electronicsList.setValue(result), error -> Log.e("ViewModel", error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getJeweleries() {
        repository.getJeweleryCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> jeweleryList.setValue(result), error -> Log.e("ViewModel", error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getMenClothing() {
        repository.getMenClothingCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> menProductsList.setValue(result), error -> Log.e("ViewModel", error.getMessage()));
    }

    @SuppressLint("CheckResult")
    public void getWomenClothing() {
        repository.getWomenClothingCategory()
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(result -> womenProductsList.setValue(result), error -> Log.e("ViewModel", error.getMessage()));
    }
}
