package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.GridView;
import android.widget.ProgressBar;
import android.widget.Toast;

import androidx.fragment.app.Fragment;

import com.example.ecommerceapp.R;
import com.example.ecommerceapp.Services.ApiService;
import com.example.ecommerceapp.Models.GridItem;
import com.example.ecommerceapp.Adapter.GridViewAdapter;
import com.example.ecommerceapp.Services.RetrofitClient;
import com.example.ecommerceapp.Activities.product_show_Activity;

import java.util.ArrayList;
import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class ExploreFragment extends Fragment {

    GridView gridView;
    GridViewAdapter adapterGrid;
    private ApiService apiService;
    private List<GridItem> productList = new ArrayList<>();
    ProgressBar progressBar;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_explore, container, false);

        gridView = view.findViewById(R.id.gridlayout);
        progressBar = view.findViewById(R.id.progress_bar);

        adapterGrid = new GridViewAdapter(getContext(), productList);
        gridView.setAdapter(adapterGrid);

        gridView.setOnItemClickListener((parent, view1, position, id) -> {
            String price = productList.get(position).getPrice();
            double dollarAmount = Double.parseDouble(price);
            double rupeesAmount = dollarAmount * 85;

            Intent intent = new Intent(getActivity(), product_show_Activity.class);
            intent.putExtra("product_name", productList.get(position).getTitle());
            intent.putExtra("product_price", String.format("%.2f", rupeesAmount));
            intent.putExtra("product_description", productList.get(position).getDescription());
            intent.putExtra("product_category", productList.get(position).getCategory());
            intent.putExtra("product_image", productList.get(position).getImage());
            intent.putExtra("product_rating", productList.get(position).getRating().getRate());
            intent.putExtra("product_rating_count", productList.get(position).getRating().getCount());

            startActivity(intent);
        });

        apiService = RetrofitClient.getClient().create(ApiService.class);
        fetchProducts();

        return view;
    }

    private void fetchProducts() {

        progressBar.setVisibility(View.VISIBLE);

        apiService.getProducts().enqueue(new Callback<List<GridItem>>() {
            @Override
            public void onResponse(Call<List<GridItem>> call, Response<List<GridItem>> response) {

                progressBar.setVisibility(View.GONE);

                if (response.isSuccessful() && response.body() != null) {
                    productList.clear();
                    productList.addAll(response.body());
                    adapterGrid.notifyDataSetChanged();
                } else {
                    if (isAdded()) {
                        Toast.makeText(requireContext(), "Failed to load products", Toast.LENGTH_SHORT).show();
                    }
                }
            }

            @Override
            public void onFailure(Call<List<GridItem>> call, Throwable t) {

                progressBar.setVisibility(View.GONE);

                if (isAdded()) {
                    Toast.makeText(requireContext(), "Check Internet Connection", Toast.LENGTH_SHORT).show();
                } else {
                    Log.e("ExploreFragment", "Error fetching products: " + t.getMessage());
                }
            }
        });
    }
}
