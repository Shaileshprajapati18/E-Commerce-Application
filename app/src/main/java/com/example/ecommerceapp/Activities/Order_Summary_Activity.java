package com.example.ecommerceapp.Activities;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.razorpay.Checkout;
import com.razorpay.PaymentResultListener;

import org.json.JSONObject;

public class Order_Summary_Activity extends AppCompatActivity implements PaymentResultListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_order_summary);

        String total = getIntent().getStringExtra("total");

        Checkout.preload(getApplicationContext());

        makePyment(total);
    }

    private void makePyment(String number) {
        Checkout checkout = new Checkout();
        checkout.setImage(R.drawable.img_1);
        checkout.setKeyID("rzp_test_1a2b3c4d5e6f7g");
        double finalAmount=Float.parseFloat(number)*100;

        try {
            JSONObject object = new JSONObject();
            object.put("name", "E-Commerce");
            object.put("description", "Reference No. #123456");
            object.put("image", "https://s3.amazonaws.com/rzp-mobile/images/rzp.png");
            object.put("theme.color", "#3399cc");
            object.put("currency", "INR");
            object.put("amount", finalAmount+"");
            object.put("prefill.contact", "7041593700");
            object.put("prefill.email", "shaileshprajapati182005@gmail.com");

            checkout.open(this, object);
            Log.d("TAG", "makePyment: "+object);

        }catch (Exception e){
            Log.e("TAG", "Error in starting Razorpay Checkout", e);
        }
    }

    @Override
    public void onPaymentSuccess(String s) {
        Toast.makeText(this, "Payment Successful", Toast.LENGTH_SHORT).show();
        finish();
        Log.d("TAG", "onPaymentSuccess: "+s);
    }

    @Override
    public void onPaymentError(int i, String s) {
        Log.d("TAG", "onPaymentError: "+s);
        Toast.makeText(this, "Payment Failed", Toast.LENGTH_SHORT).show();
        finish();

    }
}