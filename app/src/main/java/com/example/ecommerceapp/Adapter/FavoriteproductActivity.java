package com.example.ecommerceapp.Adapter;

import static java.security.AccessController.getContext;

import android.content.Context;
import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.graphics.Insets;
import androidx.core.view.ViewCompat;
import androidx.core.view.WindowInsetsCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.cartItem;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class FavoriteproductActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    cartAdapter cartAdapter;
    List<cartItem> cartItemList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    Context context;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_favoriteproduct);

        recyclerView = findViewById(R.id.cart_list);

        recyclerView.setLayoutManager(new LinearLayoutManager(this));

        // Initialize the cartAdapter with the updated listener
        cartAdapter = new cartAdapter(this, (ArrayList<cartItem>) cartItemList);
        recyclerView.setAdapter(cartAdapter);

        databaseHelper = new DatabaseHelper(this);
        productData();
    }
    private void productData() {
        Cursor cursor = databaseHelper.viewFavoriteData();
        double total = 0.0;
        if (cursor.moveToFirst()) {
            do {
                byte[] imageBytes = cursor.getBlob(cursor.getColumnIndex("image"));
                Bitmap imageBitmap = databaseHelper.getBitmapFromBytes(imageBytes);

                String name = cursor.getString(cursor.getColumnIndex("name"));
                String size = cursor.getString(cursor.getColumnIndex("size"));
                String rating = cursor.getString(cursor.getColumnIndex("rating"));
                String ratingCount = cursor.getString(cursor.getColumnIndex("ratingCount"));
                String priceString = cursor.getString(cursor.getColumnIndex("price"));
                int id = cursor.getInt(cursor.getColumnIndex("id"));


                cartItem item = new cartItem(imageBitmap, name, size, rating, "(" + ratingCount + ")", priceString, id);
                cartItemList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            cartAdapter.notifyDataSetChanged();
        }
    }
}