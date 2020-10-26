package com.example.reviewcosmetics;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.Manifest;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.google.android.material.textfield.TextInputEditText;
import com.google.android.material.textfield.TextInputLayout;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class SignInActivity extends AppCompatActivity {
    Button btnLogin;
    TextInputEditText edtPhoneLogin, edtPasswordLogin;
    TextInputLayout layoutPhoneLogin, layoutPassLogIn;
    DatabaseReference databaseReference;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_in);
        mapping();
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                validateAccount();
            }
        });
    }

    private void mapping() {
        databaseReference = FirebaseDatabase.getInstance().getReference("Users");
        btnLogin = findViewById(R.id.btnLogin);
        edtPhoneLogin = findViewById(R.id.etPhoneLogin);
        edtPasswordLogin = findViewById(R.id.etPasswordLogin);
        layoutPhoneLogin = findViewById(R.id.etLayoutPhoneLogin);
        layoutPassLogIn = findViewById(R.id.etLayoutPhoneLogin);
    }

    private void validateAccount() {
        String phone = edtPhoneLogin.getText().toString().trim();
        final String pass = edtPasswordLogin.getText().toString().trim();
        if(phone.isEmpty() || pass.isEmpty()) {
            Toast.makeText(SignInActivity.this, "Not be empty phone or password !",
                    Toast.LENGTH_SHORT).show();
            return;
        }
        checkInDatabase(phone, pass);
    }

    private void checkInDatabase(final String phone, final String pass) {
        databaseReference.child(phone).addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                User user = snapshot.getValue(User.class);
                if(user == null) {
                    Toast.makeText(SignInActivity.this, "This phone number not exist !",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                if (!user.getPassword().equals(pass)) {
                    Toast.makeText(SignInActivity.this, "Wrong password !",
                            Toast.LENGTH_SHORT).show();
                    return;
                }
                Toast.makeText(SignInActivity.this, "Sign In Successfully",
                        Toast.LENGTH_SHORT).show();
                saveSignInState(phone, user);
                Intent it = new Intent(SignInActivity.this, HomeActivity.class);
                startActivity(it);
                finish();
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {
                Log.d("MH", error.toString());
            }
        });
    }

    private void saveSignInState(String phone, User user) {
        SharedPreferences preferences = getSharedPreferences(HomeActivity.CHECK_SIGN_IN, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(HomeActivity.NAME, user.getName());
        editor.putString(HomeActivity.AVATAR, user.getAvatar());
        editor.putString(HomeActivity.PASSWORD, user.getPassword());
        editor.putString(HomeActivity.PHONE, phone);
        editor.putBoolean(HomeActivity.IS_SIGN_IN, true);
        editor.apply();
    }

}