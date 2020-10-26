package com.example.reviewcosmetics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentPagerAdapter;

public class BrandPagerAdapter extends FragmentPagerAdapter {
    public static final String BRAND_TYPE = "brand_type";

    public BrandPagerAdapter(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        BrandChildFragment fragment = new BrandChildFragment();
        Bundle bundle = new Bundle();
        if(position == 0) {
            bundle.putString(BRAND_TYPE, "Europe");
        } else {
            bundle.putString(BRAND_TYPE, "Asian");
        }
        fragment.setArguments(bundle);
        return fragment;
    }

    @Override
    public int getCount() {
        return 2;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = "";
        if(position == 0) {
            title = "Europe";
        } else {
            title = "Asia";
        }
        return title;
    }
}
