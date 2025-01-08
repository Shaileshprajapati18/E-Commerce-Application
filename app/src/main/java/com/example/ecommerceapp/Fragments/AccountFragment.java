package com.example.ecommerceapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.cardview.widget.CardView;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.example.ecommerceapp.Activities.LoginActivity;
import com.example.ecommerceapp.Adapter.FavoriteproductActivity;
import com.example.ecommerceapp.R;
import com.google.firebase.Firebase;
import com.google.firebase.auth.FirebaseAuth;

public class AccountFragment extends Fragment {

    LinearLayout logOut;
    FirebaseAuth firebaseAuth;
    CardView wishList;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        View view=inflater.inflate(R.layout.fragment_account, container, false);

        logOut=view.findViewById(R.id.btnLogout);
        firebaseAuth=FirebaseAuth.getInstance();
        wishList=view.findViewById(R.id.wishList);

        wishList.setOnClickListener(v -> {
            Intent intent=new Intent(getActivity(), FavoriteproductActivity.class);
            startActivity(intent);
        });
        logOut.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                firebaseAuth.signOut();
                Intent intent=new Intent(getActivity(), LoginActivity.class);
                startActivity(intent);
                getActivity().finish();
            }
        });
        return view;

    }
}