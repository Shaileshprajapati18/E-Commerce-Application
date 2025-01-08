package com.example.ecommerceapp.Adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.ecommerceapp.Models.GridItem;
import com.example.ecommerceapp.R;

import java.util.List;

public class GridViewAdapter extends BaseAdapter {
    private Context context;
    private List<GridItem> productList;

    public GridViewAdapter(Context context, List<GridItem> productList) {
        this.context = context;
        this.productList = productList;
    }

    @Override
    public int getCount() {
        return (productList != null) ? productList.size() : 0;
    }

    @Override
    public Object getItem(int position) {
        return productList.get(position);
    }

    @Override
    public long getItemId(int position) {
        return position;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        if (convertView == null) {
            convertView = LayoutInflater.from(context).inflate(R.layout.productlist, parent, false);
        }

        ImageView productImage = convertView.findViewById(R.id.product_image);
        TextView productName = convertView.findViewById(R.id.product_name);
        TextView productPrice = convertView.findViewById(R.id.product_price);
        TextView productDescription = convertView.findViewById(R.id.product_description);
        TextView productCategory = convertView.findViewById(R.id.product_category);
        TextView rate = convertView.findViewById(R.id.rate);
        TextView count = convertView.findViewById(R.id.count);

        GridItem product = productList.get(position);

        productName.setText(product.getTitle());
        productDescription.setText(product.getDescription());
        productCategory.setText(product.getCategory());

        String rupee_price=product.getPrice();
        double dollarAmount = Double.parseDouble(rupee_price);
        double rupeesAmount = dollarAmount * 85;
        productPrice.setText(String.format("%.2f", rupeesAmount));

        rate.setText(String.valueOf(product.getRating().getRate()));
        count.setText(String.valueOf(product.getRating().getCount()));

        Glide.with(context)
                .load(product.getImage())
                .placeholder(R.drawable.img_1)
                .into(productImage);

        return convertView;
    }
}
