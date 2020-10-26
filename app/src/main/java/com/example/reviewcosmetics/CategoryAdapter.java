package com.example.reviewcosmetics;

import android.content.ClipData;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.request.RequestOptions;

import java.util.List;

public class CategoryAdapter extends RecyclerView.Adapter<ViewHolderCategory> {
    List<Category> categoryList;
    int layout;
    ViewHolderCategory holder;
    ItemClickListener itemClickListener;
    Context context;

    public CategoryAdapter(Context context, List<Category> categoryList, int layout, ItemClickListener itemClickListener) {
        this.categoryList = categoryList;
        this.layout = layout;
        this.itemClickListener = itemClickListener;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewHolderCategory onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(layout, parent, false);
        holder = new ViewHolderCategory(view, itemClickListener);
        return holder;
    }


    @Override
    public void onBindViewHolder(@NonNull ViewHolderCategory holder, int position) {
        Category category = categoryList.get(position);
        Glide.with(context).load(category.getImage()).apply(new RequestOptions().override(410, 200))
                .into(holder.ivCategory);
        holder.tvCategory.setText(category.getName());
    }

    @Override
    public int getItemCount() {
        return categoryList.size();
    }
}
