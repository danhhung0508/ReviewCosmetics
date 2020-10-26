package com.example.reviewcosmetics;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class MainActivity extends AppCompatActivity {
    Button btnSignIn, btnSignUp;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        checkSignInState();
        btnSignIn = findViewById(R.id.btnSignIn);
        btnSignUp = findViewById(R.id.btnSignUp);
        btnSignIn.setPressed(false);
        btnSignUp.setPressed(false);
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignUpActivity.class));
            }
        });

        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(MainActivity.this, SignInActivity.class));
            }
        });
    }

    private void checkSignInState() {
        SharedPreferences sharedPreferences = getSharedPreferences(HomeActivity.CHECK_SIGN_IN, MODE_PRIVATE);
        boolean isSignIn = sharedPreferences.getBoolean(HomeActivity.IS_SIGN_IN, false);
        if(isSignIn) {
            Intent itCheck = new Intent(MainActivity.this, HomeActivity.class);
            startActivity(itCheck);
            finish();
        }
    }
}