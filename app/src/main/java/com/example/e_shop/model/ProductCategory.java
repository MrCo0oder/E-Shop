package com.example.e_shop.model;

public class ProductCategory {


    private String categoryName;
    private String image;

    public ProductCategory(String categoryName, String image) {

        this.categoryName = categoryName;
        this.image = image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getImage() {
        return image;
    }


    public String getCategoryName() {
        return categoryName;
    }

    public void setCategoryName(String categoryName) {
        this.categoryName = categoryName;
    }


}
