package com.example.reviewcosmetics;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentStatePagerAdapter;

public class PagerTopProduct extends FragmentStatePagerAdapter {
    public PagerTopProduct(@NonNull FragmentManager fm, int behavior) {
        super(fm, behavior);
    }

    @NonNull
    @Override
    public Fragment getItem(int position) {
        TopProductFragment fragment = new TopProductFragment();
        Bundle args = new Bundle();
        switch (position) {
            case 0:
                args.putString(ProductTypeFragment.PRODUCT_TYPE, ProductTypeFragment.TYPE_CLEANSER);
                break;
            case 1:
                args.putString(ProductTypeFragment.PRODUCT_TYPE, ProductTypeFragment.TYPE_TONER);
                break;
            case 2:
                args.putString(ProductTypeFragment.PRODUCT_TYPE, ProductTypeFragment.TYPE_SERUM);
                break;
            case 3:
                args.putString(ProductTypeFragment.PRODUCT_TYPE, ProductTypeFragment.TYPE_EXFOLIATOR);
                break;
            case 4:
                args.putString(ProductTypeFragment.PRODUCT_TYPE, ProductTypeFragment.TYPE_MOISTURISER);
                break;
            case 5:
                args.putString(ProductTypeFragment.PRODUCT_TYPE, ProductTypeFragment.TYPE_SUNSCREEN);
                break;
        }
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public int getCount() {
        return 6;
    }

    @Nullable
    @Override
    public CharSequence getPageTitle(int position) {
        CharSequence title = "";
        switch (position) {
            case 0:
                title = ProductTypeFragment.TYPE_CLEANSER;
                break;
            case 1:
                title = ProductTypeFragment.TYPE_TONER;
                break;
            case 2:
                title = ProductTypeFragment.TYPE_SERUM;
                break;
            case 3:
                title = ProductTypeFragment.TYPE_EXFOLIATOR;
                break;
            case 4:
                title = ProductTypeFragment.TYPE_MOISTURISER;
                break;
            case 5:
                title = ProductTypeFragment.TYPE_SUNSCREEN;
                break;
        }
        return title;
    }
}
