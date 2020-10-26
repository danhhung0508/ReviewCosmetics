package com.example.reviewcosmetics;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Filter;
import android.widget.Filterable;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class ProductAdapter extends RecyclerView.Adapter<ViewHolderProduct> {
    List<Product> productList;
    int layout;
    ItemClickListener itemClickListener;
    Context context;

    public ProductAdapter(Context context, List<Product> productList, int layout, ItemClickListener itemClickListener) {
        this.itemClickListener = itemClickListener;
        this.productList = productList;
        this.layout = layout;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderProduct onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        ViewHolderProduct holder = new ViewHolderProduct(view);
        return holder;
    }

    @Override
    public void onBindViewHolder(@NonNull final ViewHolderProduct holder, final int position) {
        Product product = productList.get(position);
        Glide.with(context).load(product.getImageProduct()).into(holder.ivProduct);
        holder.tvProductBrand.setText(product.getBrandName());
        holder.tvProductName.setText(product.getProductName());
        holder.tvFavourite.setText(String.valueOf(product.getLike()));
        holder.view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                itemClickListener.itemClick(holder.getAdapterPosition());
            }
        });
    }

    @Override
    public int getItemCount() {
        return productList.size();
    }
    }
