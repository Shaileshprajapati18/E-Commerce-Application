package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.PopupMenu;
import android.widget.RatingBar;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.ecommerceapp.Models.cartItem;
import com.example.ecommerceapp.R;

import java.util.ArrayList;

public class cartAdapter extends RecyclerView.Adapter<cartAdapter.ViewHolder> {

    private Context context;
    private ArrayList<cartItem> list;
    private CartTotalPriceUpdateListener priceUpdateListener;

    public cartAdapter(Context context, ArrayList<cartItem> list, CartTotalPriceUpdateListener priceUpdateListener) {
        this.context = context;
        this.list = list;
        this.priceUpdateListener = priceUpdateListener;
    }

    public cartAdapter(FavoriteproductActivity context, ArrayList<cartItem> cartItemList) {
        this.context = context;
        this.list = cartItemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.add_to_cart_list, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        cartItem item = list.get(position);

        holder.name.setText(item.getName());
        holder.size.setText(item.getSize());
        holder.rating.setText(item.getRating());
        holder.ratingCount.setText(item.getRatingCount());
        holder.price.setText(item.getPrice());
        holder.productImage.setImageBitmap(item.getImageBitmap());
        holder.ratingBar.setRating(Float.parseFloat(item.getRating()));

        double initialPrice = Double.parseDouble(item.getPrice());
        holder.price.setText(String.format("%.2f", initialPrice));

        holder.quantitySpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parent, View view, int pos, long id) {
                int quantity = Integer.parseInt(parent.getItemAtPosition(pos).toString());
                double updatedPrice = initialPrice * quantity;

                item.setPrice(String.format("%.2f", updatedPrice));

                holder.price.setText(String.format("%.2f", updatedPrice));

                if (priceUpdateListener != null) {
                    priceUpdateListener.onTotalPriceUpdated();
                }
            }
            @Override
            public void onNothingSelected(AdapterView<?> parent) {

            }
        });

        String quantity = holder.quantitySpinner.getSelectedItem().toString();
        double price = Double.parseDouble(item.getPrice());
        double totalPrice = price * Integer.parseInt(quantity);
        holder.price.setText(String.format("%.2f", totalPrice));

        holder.remove.setOnClickListener(v -> {
            DatabaseHelper databaseHelper = new DatabaseHelper(context);
            databaseHelper.deleteProduct(item.getId()); // Default delete action (e.g., cart)
            list.remove(position);
            notifyDataSetChanged();
            if (priceUpdateListener != null) {
                priceUpdateListener.onTotalPriceUpdated();
            }
        });
}
    @Override
    public int getItemCount() {
        return list.size();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        TextView name, size, rating, ratingCount, price;
        ImageView productImage;
        LinearLayout remove;
        RatingBar ratingBar;
        Spinner quantitySpinner;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            name = itemView.findViewById(R.id.title);
            size = itemView.findViewById(R.id.size);
            rating = itemView.findViewById(R.id.rate);
            ratingCount = itemView.findViewById(R.id.count);
            price = itemView.findViewById(R.id.price);
            productImage = itemView.findViewById(R.id.product_image);
            remove = itemView.findViewById(R.id.remove);
            ratingBar = itemView.findViewById(R.id.ratingBar);
            quantitySpinner = itemView.findViewById(R.id.quantity_spinner);
        }
    }

    public interface CartTotalPriceUpdateListener {
        void onTotalPriceUpdated();
    }
}
