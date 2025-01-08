package com.example.ecommerceapp.Services;

import com.example.ecommerceapp.Models.GridItem;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.PATCH;
import retrofit2.http.POST;

import java.util.List;

public interface ApiService {

    @POST("products")
    Call<GridItem> createProduct(@Body GridItem product);

    @PATCH("products/{id}")
    Call<GridItem> updateProduct(int id, GridItem product);

    @GET("products")
    Call<List<GridItem>> getProducts();
}

