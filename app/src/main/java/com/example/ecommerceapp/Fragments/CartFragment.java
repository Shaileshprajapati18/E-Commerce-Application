package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.os.Bundle;

import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.ecommerceapp.Adapter.DatabaseHelper;
import com.example.ecommerceapp.Adapter.cartAdapter;
import com.example.ecommerceapp.Models.cartItem;
import com.example.ecommerceapp.Activities.Order_Summary_Activity;
import com.example.ecommerceapp.R;

import java.util.ArrayList;
import java.util.List;

public class CartFragment extends Fragment implements cartAdapter.CartTotalPriceUpdateListener {

    RecyclerView recyclerView;
    cartAdapter cartAdapter;
    List<cartItem> cartItemList = new ArrayList<>();
    DatabaseHelper databaseHelper;
    TextView totalPrice, placeOrder;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_cart, container, false);

        recyclerView = view.findViewById(R.id.cart_list);
        totalPrice = view.findViewById(R.id.total_price);
        placeOrder = view.findViewById(R.id.placeOrder);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        cartAdapter = new cartAdapter(getContext(), (ArrayList<cartItem>) cartItemList, this);
        recyclerView.setAdapter(cartAdapter);

        databaseHelper = new DatabaseHelper(getContext());
        productData();

        placeOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String total = totalPrice.getText().toString();
                Intent intent = new Intent(getActivity(), Order_Summary_Activity.class);
                intent.putExtra("total", total);
                startActivity(intent);
            }
        });

        return view;
    }

    private void productData() {
        Cursor cursor = databaseHelper.viewData();
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

                double price = parsePrice(priceString);
                total += price;

                cartItem item = new cartItem(imageBitmap, name, size, rating, "(" + ratingCount + ")", priceString, id);
                cartItemList.add(item);
            } while (cursor.moveToNext());
            cursor.close();
            cartAdapter.notifyDataSetChanged();
        }
        totalPrice.setText(String.format("%.2f", total));
    }

    private double parsePrice(String priceString) {
        try {
            return Double.parseDouble(priceString.replaceAll("[^\\d.]", ""));
        } catch (NumberFormatException e) {
            return 0.0;
        }
    }
    @Override
    public void onTotalPriceUpdated() {
        double total = 0.0;
        // Recalculate the total price by summing the price of each item in the cart
        for (cartItem item : cartItemList) {
            double price = parsePrice(item.getPrice());
            total += price;
        }
        // Update the total price TextView
        totalPrice.setText(String.format("%.2f", total));
    }
}
