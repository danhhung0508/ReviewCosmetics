package com.example.reviewcosmetics;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.google.android.material.navigation.NavigationView;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

public class HomeActivity extends AppCompatActivity implements
        NavigationView.OnNavigationItemSelectedListener, ItemClickListener {
    public static final String PHONE = "phone";
    public static int PRODUCT_TYPE_ID = -1;
    public static final String NAME = "name";
    public static final String IS_SIGN_IN = "isSignIn";
    public static final String AVATAR = "avatar";
    public static final String CHECK_SIGN_IN = "SignInState";
    public static final String PASSWORD = "pass";
    DrawerLayout drawerLayout;
    Toolbar toolbar;
    ActionBarDrawerToggle actionBarDrawerToggle;
    NavigationView navigationView;
    ImageView ivAvatar;
    TextView tvName;
    User user;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        getInfoUser();
        mapping();
        setUpNavDrawer();
        setFragmentHome();
        navigationView.setNavigationItemSelectedListener(this);
    }

    @Override
    public boolean onNavigationItemSelected(@NonNull MenuItem item) {
        drawerLayout.closeDrawer(GravityCompat.START);
        switch (item.getItemId()) {
            case R.id.menu_item_home:
                setFragmentHome();
                break;
            case R.id.menu_item_products:
                setFragmentBrand();
                break;
            case R.id.menu_item_top:
                Intent it = new Intent(HomeActivity.this, TopProductActivity.class);
                startActivity(it);
                break;
            case R.id.menu_item_logout:
                logOut();
                break;
        }
        return true;
    }

    @Override
    public void itemClick(int position) {
        PRODUCT_TYPE_ID = position;
        FragmentTransaction transaction = getSupportFragmentManager().beginTransaction();
        transaction.replace(R.id.frameLayoutHome, new ProductTypeFragment());
        transaction.addToBackStack(null);
        transaction.commit();
    }

    @Override
    public void itemClick(Product product, String key) {

    }

    private void logOut() {
        SharedPreferences preferences = getSharedPreferences(CHECK_SIGN_IN, MODE_PRIVATE);
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(IS_SIGN_IN, false);
        editor.remove(NAME);
        editor.remove(AVATAR);
        editor.remove(PASSWORD);
        editor.remove(PHONE);
        editor.apply();
        Intent itSignOut = new Intent(HomeActivity.this, MainActivity.class);
        startActivity(itSignOut);
        finish();
    }

    private void setFragmentBrand() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, new BrandsFragment());
        fragmentTransaction.addToBackStack(null);
        fragmentTransaction.commit();
    }

    private void setFragmentHome() {
        FragmentManager manager = getSupportFragmentManager();
        FragmentTransaction fragmentTransaction = manager.beginTransaction();
        fragmentTransaction.replace(R.id.frameLayoutHome, new HomeFragment());
        fragmentTransaction.commit();
    }

    private void setUpNavDrawer() {
        toolbar.setTitle("Perfect skin for every one");
        setSupportActionBar(toolbar);
        actionBarDrawerToggle = new ActionBarDrawerToggle(this, drawerLayout, toolbar,
                R.string.openNav, R.string.closeNav);
        drawerLayout.addDrawerListener(actionBarDrawerToggle);
        actionBarDrawerToggle.setDrawerIndicatorEnabled(true);
        actionBarDrawerToggle.syncState();
        setInfoUser();
    }

    private void setInfoUser() {
        tvName.setText(user.getName());
        if (user.getAvatar().equals("")) {
            ivAvatar.setImageResource(R.drawable.avatar_default);
        } else {
            Glide.with(this).load(user.getAvatar()).centerCrop().
                    error(R.drawable.ic_baseline_error_outline_24).into(ivAvatar);
        }
    }

    private void getInfoUser() {
        SharedPreferences sharedPreferences = getSharedPreferences(CHECK_SIGN_IN, MODE_PRIVATE);
        String name = sharedPreferences.getString(NAME, "");
        String pass = sharedPreferences.getString(PASSWORD, "");
        String avatar = sharedPreferences.getString(AVATAR, "");
        user = new User(name, pass, avatar);
    }

    private void mapping() {
        drawerLayout = findViewById(R.id.drawerLayout);
        toolbar = findViewById(R.id.toolBarHome);
        navigationView = findViewById(R.id.navView);
        ivAvatar = navigationView.getHeaderView(0).findViewById(R.id.ivAvatarHome);
        tvName = navigationView.getHeaderView(0).findViewById(R.id.tvNameHome);
    }


}