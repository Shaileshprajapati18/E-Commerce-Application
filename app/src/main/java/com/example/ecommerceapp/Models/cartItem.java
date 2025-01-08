package com.example.ecommerceapp.Models;

import android.graphics.Bitmap;

public class cartItem {
    private Bitmap imageBitmap;
    private String name, size, rating, ratingCount, price;
    private int id;

    public cartItem(Bitmap imageBitmap, String name, String size, String rating, String ratingCount, String price, int id) {
        this.imageBitmap = imageBitmap;
        this.name = name;
        this.size = size;
        this.rating = rating;
        this.ratingCount = ratingCount;
        this.price = price;
        this.id = id;
    }

    public Bitmap getImageBitmap() {
        return imageBitmap;
    }

    public String getName() {
        return name;
    }

    public String getSize() {
        return size;
    }

    public String getRating() {
        return rating;
    }

    public String getRatingCount() {
        return ratingCount;
    }

    public String getPrice() {
        return price;
    }
    public void setPrice(String price) {
        this.price = price;
    }

    public int getId() {
        return id;
    }
}
