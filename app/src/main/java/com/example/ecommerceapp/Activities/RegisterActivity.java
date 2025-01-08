package com.example.ecommerceapp.Activities;

import android.content.Intent;
import android.os.Bundle;
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

public class RegisterActivity extends AppCompatActivity {

    TextView signin;
    Button loginbutton;
    EditText username,email,password;
    FirebaseAuth firebaseauth;
    ProgressBar progressBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        EdgeToEdge.enable(this);
        setContentView(R.layout.activity_register);

        signin=findViewById(R.id.signin);
        username=findViewById(R.id.username);
        email=findViewById(R.id.email);
        password=findViewById(R.id.password);
        loginbutton=findViewById(R.id.loginButton);
        firebaseauth=FirebaseAuth.getInstance();
        progressBar=findViewById(R.id.progressBar);

        loginbutton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String user=username.getText().toString().trim();
                String mail=email.getText().toString().trim();
                String pass=password.getText().toString().trim();

                if (TextUtils.isEmpty(user) || TextUtils.isEmpty(mail) || TextUtils.isEmpty(pass)){
                    username.setError("Please Enter Username");
                    email.setError("Please Enter Email");
                    password.setError("Please Enter Password");
                }
                else {
                    progressBar.setVisibility(View.VISIBLE);
                    firebaseauth.createUserWithEmailAndPassword(mail,pass)
                            .addOnCompleteListener(task -> {

                                if (task.isSuccessful()){
                                    Toast.makeText(RegisterActivity.this, "Sign Up Successful", Toast.LENGTH_SHORT).show();
                                    Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                                    startActivity(intent);
                                    finish();
                                }
                                else {
                                    progressBar.setVisibility(View.GONE);
                                    Toast.makeText(RegisterActivity.this, "Sign Up Failed: ", Toast.LENGTH_LONG).show();
                                }
                            });
                }
            }
        });
        signin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(RegisterActivity.this,LoginActivity.class);
                startActivity(intent);
                finish();
            }
        });
    }
}