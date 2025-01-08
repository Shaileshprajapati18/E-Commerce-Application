package com.example.ecommerceapp.Activities;

import android.os.Bundle;
import android.view.MenuItem;

import androidx.activity.EdgeToEdge;
import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentTransaction;

import com.example.ecommerceapp.Fragments.AccountFragment;
import com.example.ecommerceapp.Fragments.CartFragment;
import com.example.ecommerceapp.Fragments.CategoriesFragment;
import com.example.ecommerceapp.Fragments.ExploreFragment;
import com.example.ecommerceapp.Fragments.HomeFragment;
import com.example.ecommerceapp.R;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {

    BottomNavigationView bottomNavigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_main);

        bottomNavigationView=findViewById(R.id.bottom_navigation);

        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container, new HomeFragment())
                    .commit();
        }

        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {

                Fragment selectFragment=null;

                if(item.getItemId()==R.id.home) {
                    selectFragment = new HomeFragment();
                }else if(item.getItemId()==R.id.explore) {
                    selectFragment = new ExploreFragment();
                }else if(item.getItemId()==R.id.categories) {
                    selectFragment = new CategoriesFragment();
                }else if(item.getItemId()==R.id.account) {
                    selectFragment = new AccountFragment();
                }else if(item.getItemId()==R.id.cart) {
                    selectFragment = new CartFragment();
                }
                else{
                    return false;
                }
                getSupportFragmentManager().beginTransaction().replace(R.id.fragment_container,selectFragment).commit();
                return true;
            }
        });
    }
}