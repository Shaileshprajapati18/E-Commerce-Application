package com.example.ecommerceapp.Activities;

import android.content.Intent;
import android.os.Bundle;
import android.text.Html;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;
import androidx.viewpager.widget.ViewPager;

import com.example.ecommerceapp.Adapter.SliderAdapter;
import com.example.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class OnBoardingActivity extends AppCompatActivity {

    ViewPager viewPager;
    LinearLayout dots;
    SliderAdapter sliderAdapter;
    TextView[] adddots;
    Button getstarted;
    Animation animation;
    FirebaseAuth firebaseAuth;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_on_boarding);
        viewPager = findViewById(R.id.slider);
        dots = findViewById(R.id.dots);
        getstarted = findViewById(R.id.get_started_btn);
        firebaseAuth = FirebaseAuth.getInstance();

        if (firebaseAuth.getCurrentUser() != null) {
            startActivity(new Intent(OnBoardingActivity.this, MainActivity.class));
            finish();
        }

        addDots(0);

        viewPager.addOnPageChangeListener(changeListener);

        sliderAdapter = new SliderAdapter(this);
        viewPager.setAdapter(sliderAdapter);

        getstarted.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(OnBoardingActivity.this, LoginActivity.class));
                finish();
            }
        });

    }
    private void addDots(int position) {

        adddots = new TextView[3];
        dots.removeAllViews();
        for (int i = 0; i < adddots.length; i++) {
            adddots[i] = new TextView(this);
            adddots[i].setText(Html.fromHtml("&#8226"));
            adddots[i].setTextSize(35);
            dots.addView(adddots[i]);

        }
        if (adddots.length > 0) {
            adddots[position].setTextColor(getResources().getColor(R.color.purple));
        }
    }

    ViewPager.OnPageChangeListener changeListener = new ViewPager.OnPageChangeListener() {
        @Override
        public void onPageScrolled(int position, float positionOffset, int positionOffsetPixels) {

        }

        @Override
        public void onPageSelected(int position) {

            addDots(position);
            if (position == 0) {
                getstarted.setVisibility(View.INVISIBLE);
            } else if (position == 1) {
                getstarted.setVisibility(View.INVISIBLE);
            } else {
                animation= AnimationUtils.loadAnimation(OnBoardingActivity.this,R.anim.slide_animation);
                getstarted.setAnimation(animation);
                getstarted.setVisibility(View.VISIBLE);
            }
        }
        @Override
        public void onPageScrollStateChanged(int state) {

        }
    };
}