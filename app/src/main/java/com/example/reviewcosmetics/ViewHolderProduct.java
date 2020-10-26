package com.example.reviewcosmetics;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class ViewHolderProduct extends RecyclerView.ViewHolder {
    ImageView ivProduct;
    TextView tvProductName, tvProductBrand, tvFavourite;
    View view;
    public ViewHolderProduct(@NonNull View itemView) {
        super(itemView);
        view = itemView;
        ivProduct = itemView.findViewById(R.id.ivProduct);
        tvProductName = itemView.findViewById(R.id.tvProductName);
        tvProductBrand = itemView.findViewById(R.id.tvProductBrand);
        tvFavourite = itemView.findViewById(R.id.tvFavouriteItem);
    }
}
