package com.example.ecommerceapp.Activities;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.target.CustomTarget;
import com.bumptech.glide.request.transition.Transition;
import com.example.ecommerceapp.Adapter.DatabaseHelper;
import com.example.ecommerceapp.Fragments.CartFragment;
import com.example.ecommerceapp.R;

import java.io.ByteArrayOutputStream;

public class product_show_Activity extends AppCompatActivity {

    private ImageView productImage,cart, favorite;
    private TextView productName, productPrice, productDescription, productCategory, productRating, productRatingCount;
    private RatingBar productRatingBar;
    private TextView addToCart, buyNow;
    private Spinner quantitySpinner;
    private DatabaseHelper databaseHelper;

    private Bitmap productBitmap; // Store the product image as a Bitmap

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_product_show);

        cart = findViewById(R.id.MoveToCart);
        productImage = findViewById(R.id.product_image);
        productName = findViewById(R.id.product_name);
        productPrice = findViewById(R.id.product_price);
        productDescription = findViewById(R.id.product_description);
        productCategory = findViewById(R.id.product_category);
        productRating = findViewById(R.id.rate);
        productRatingCount = findViewById(R.id.count);
        productRatingBar = findViewById(R.id.ratingBar);
        addToCart = findViewById(R.id.add_to_cart);
        buyNow = findViewById(R.id.buy_now);
        quantitySpinner = findViewById(R.id.quantity_spinner);
        favorite=findViewById(R.id.favorite);

        databaseHelper = new DatabaseHelper(this);

        CartFragment cartFragment = new CartFragment();

        cart.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                getSupportFragmentManager()
                        .beginTransaction()
                        .replace(R.id.fragment_container, cartFragment)
                        .addToBackStack(null)
                        .commit();
            }
        });
        String name = getIntent().getStringExtra("product_name");
        String price = getIntent().getStringExtra("product_price");
        String description = getIntent().getStringExtra("product_description");
        String category = getIntent().getStringExtra("product_category");
        String imageUrl = getIntent().getStringExtra("product_image");
        String rating = getIntent().getStringExtra("product_rating");
        String ratingCount = getIntent().getStringExtra("product_rating_count");

        productName.setText(name != null ? name : "No Name");
        productPrice.setText(price != null ? price : "$0.00");
        productDescription.setText(description != null ? description : "No Description");
        productCategory.setText(category != null ? category : "No Category");
        productRating.setText(rating != null ? rating : "N/A");
        productRatingCount.setText("(" + (ratingCount != null ? ratingCount : "0") + ")");
        productRatingBar.setRating(rating != null ? Float.parseFloat(rating) : 0f);

        Glide.with(this)
                .asBitmap()
                .load(imageUrl)
                .placeholder(R.drawable.img_1)
                .into(new CustomTarget<Bitmap>() {
                    @Override
                    public void onResourceReady(@NonNull Bitmap resource, Transition<? super Bitmap> transition) {
                        productImage.setImageBitmap(resource);
                        productBitmap = resource;
                    }

                    @Override
                    public void onLoadCleared(Drawable placeholder) {
                    }
                });

        favorite.setOnClickListener(v ->{
            if (productBitmap != null) {
                String size = quantitySpinner.getSelectedItem().toString();
                byte[] imageBytes = getBytesFromBitmap(productBitmap);
                boolean isInserted = databaseHelper.insertFavoriteProduct(imageBytes, name, size, rating, ratingCount, price);
                Log.d("ImageBytes", "Size: " + imageBytes.length);
                favorite.setImageResource(R.drawable.baseline_favorite_24);
                Toast.makeText(this, "Product added to favorites", Toast.LENGTH_SHORT).show();
            } else {
                Log.e("ImageBytes", "Product bitmap is null");
            }
        });
        addToCart.setOnClickListener(v -> {
            if (productBitmap != null) {
                String size = quantitySpinner.getSelectedItem().toString();
                byte[] imageBytes = getBytesFromBitmap(productBitmap);
                boolean isInserted = databaseHelper.insertProduct(imageBytes, name, size, rating, ratingCount, price);
                Log.d("ImageBytes", "Size: " + imageBytes.length);
            } else {
                Log.e("ImageBytes", "Product bitmap is null");
            }
        });
    }
    private byte[] getBytesFromBitmap(Bitmap bitmap) {
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        return stream.toByteArray();
    }
}
