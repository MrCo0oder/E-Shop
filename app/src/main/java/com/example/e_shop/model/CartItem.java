package com.example.e_shop.model;


public class CartItem {
    Integer id;
    int quantity;
    double totalPrice;
    String title;
    double price;
    String image;


    public CartItem(Integer id, int quantity, double totalPrice, String title, double price, String image) {
        this.id = id;
        this.quantity = quantity;
        this.totalPrice = totalPrice;
        this.title = title;
        this.price = price;
        this.image = image;
    }

    public CartItem() {
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public int getQuantity() {
        return quantity;
    }


    public double getTotalPrice() {
        return totalPrice;
    }


    public void setQuantity(int quantity) {
        this.quantity = quantity;
    }

    public void setTotalPrice(double totalPrice) {
        this.totalPrice = totalPrice;
    }
}
