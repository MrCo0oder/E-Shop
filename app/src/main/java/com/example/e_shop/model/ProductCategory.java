package com.example.e_shop.model;

public class ProductCategory {

    String  categoryName;


    public String getProductName() {
        return categoryName;
    }

    public void setProductName(String categoryName) {
        this.categoryName = categoryName;
    }

    public ProductCategory( String categoryName) {

        this.categoryName = categoryName;
    }
}
