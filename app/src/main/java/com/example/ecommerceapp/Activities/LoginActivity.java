package com.example.ecommerceapp.Activities;

import android.content.Intent;
import  android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import androidx.activity.EdgeToEdge;
import androidx.appcompat.app.AppCompatActivity;

import com.example.ecommerceapp.R;
import com.google.firebase.auth.FirebaseAuth;

public class LoginActivity extends AppCompatActivity {

    TextView signup;
    Button loginbutton;
    EditText email,password;
    FirebaseAuth firebaseauth;
    ProgressBar progressBar;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_login);
        signup=findViewById(R.id.signup);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginbutton=findViewById(R.id.loginButton);
        firebaseauth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        if(firebaseauth.getCurrentUser()!=null){
            Intent intent=new Intent(LoginActivity.this, MainActivity.class);
            startActivity(intent);
            finish();
        }

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String mail=email.getText().toString().trim();
                String pass=password.getText().toString().trim();
                
                        if(TextUtils.isEmpty(mail)||TextUtils.isEmpty(pass)){
                            email.setError("Please Enter Email");
                            password.setError("Please Enter Password");

                        }else {
                            progressBar.setVisibility(View.VISIBLE);

                            firebaseauth.signInWithEmailAndPassword(mail,pass)
                                .addOnCompleteListener(task -> {

                                    if (task.isSuccessful()){
                                        Intent intent=new Intent(LoginActivity.this, MainActivity.class);
                                        startActivity(intent);
                                        finish();
                                    }
                                    else {
                                        progressBar.setVisibility(View.GONE);
                                        Toast.makeText(LoginActivity.this, "Sign Up Failed" ,Toast.LENGTH_LONG).show();
                                    }
                            });
                        }
            }
        });
        signup.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(LoginActivity.this,RegisterActivity.class);
                startActivity(intent);
                finish();
            }
        });

    }
}